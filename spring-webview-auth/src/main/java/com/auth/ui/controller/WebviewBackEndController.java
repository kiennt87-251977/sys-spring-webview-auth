package com.auth.ui.controller;

import com.auth.core.domain.dto.ResponseLoginDTO;
import com.auth.core.service.TokenUtils;
import com.auth.core.service.WebviewBackEndService;
import com.auth.core.utils.DateUtilsV8;
import com.auth.core.utils.GsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@RequestMapping("/webview-back-end")
@RequiredArgsConstructor
@RestController
public class WebviewBackEndController {

    private final WebviewBackEndService service;

    //    *****************************************************************************************
//    GET
    @GetMapping("/get-phone-by")
    @CrossOrigin
    public ResponseEntity<Object> getPhoneBy(@RequestHeader HttpHeaders headers) {
        Object response = service.callApiGetPhoneBy();
        return ResponseEntity.status(HttpStatus.OK).body(
                response
        );
    }


    @GetMapping("/pingWebCore")
    @CrossOrigin
    public ResponseEntity<Object> pingWebCore(@RequestHeader HttpHeaders headers) {
        Object response = service.pingWebCore();
        return ResponseEntity.status(HttpStatus.OK).body(
                response
        );
    }


    @GetMapping("/get-data-acc-table")
    @CrossOrigin
    public ResponseEntity<String> getDataAccTable(@RequestHeader HttpHeaders headers, @RequestParam(value = "numbRecord") String numbRecord
            , @RequestParam(value = "nameRecord") String nameRecord) {

        String response = service.getDataAccTable(numbRecord, nameRecord);

        return ResponseEntity.status(HttpStatus.OK).body(
                response
        );
    }

//    @GetMapping("/getTransChart/{fromDate}/{toDate}")
//    @CrossOrigin
//    public ResponseEntity<String> getTransChart(@PathVariable String fromDate
//            , @PathVariable String toDate
//    ) {
//
//        String response = service.getTransChart(fromDate, toDate);
//        return ResponseEntity.status(HttpStatus.OK).body(
//                response
//        );
//
//    }


    @GetMapping("/getTpsChart/{fromDate}/{toDate}")
    @CrossOrigin
    public ResponseEntity<String> getTpsChart(@PathVariable String fromDate
            , @PathVariable String toDate
    ) {

        String response = service.getTpsChart(fromDate, toDate, 100);
        return ResponseEntity.status(HttpStatus.OK).body(
                response
        );

    }

    @GetMapping("/getStatusChart/{fromDate}/{toDate}")
    @CrossOrigin
    public ResponseEntity<String> getStatusChart(@PathVariable String fromDate
            , @PathVariable String toDate
    ) {

        String response = service.getStatusChart(fromDate, toDate);
        return ResponseEntity.status(HttpStatus.OK).body(
                response
        );

    }

    //    *****************************************************************************************
//    POST
    @PostMapping("/call-api-execute")
    @CrossOrigin
    public ResponseEntity<String> callApiExecute(@RequestHeader HttpHeaders headers, @RequestBody String sqlString
    ) {
        boolean auth = false;
        String response;
        if (sqlString != null && sqlString.toLowerCase().contains("alter")) {
            response = "{\"status\":\"Error can not execute alter command, Access denied\"}";
        } else {
            if (sqlString != null && (
                    sqlString.toLowerCase().contains("update")
                            || sqlString.toLowerCase().contains("insert")
                            || sqlString.toLowerCase().contains("delete")
            )) {
                Map<String, Object> responseValudate = validateAuth(headers);
                auth = (boolean) responseValudate.get(keyAuth);
            } else {
                auth = true;
            }

            if (auth) {
                response = service.callApiExecute(sqlString);
            } else {
                response = "{\"status\":\"Access denied\"}";
            }
        }


        return ResponseEntity.status(HttpStatus.OK).body(
                response
        );
    }

    @PostMapping(value = "/upload-file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadFile(@RequestParam("filename") MultipartFile file
    ) {
        log.info("Session uploadFile open");

        String responseservice = service.saveFile(file);

        String response = "Upload file finish, path file : " + responseservice;
        return ResponseEntity.status(HttpStatus.OK).body(
                response
        );
    }


    //    *****************************************************************************************
//    POST

    private String dataUser = "admin";
    private String dataPass = "admin_123a@";
    private String dataNom = "pie";
    private String dataPrenom = "chocola";

    @PostMapping("/call-login")
    @CrossOrigin
    public ResponseEntity<String> callApiLogin(@RequestHeader HttpHeaders headers, @RequestBody String bodyInput
    ) {
        log.info("Login : " + bodyInput);
        boolean auth = false;
        String user = null;
        String pass = null;
        Map<String, Object> responseValudate = validateAuth(headers);
        auth = (boolean) responseValudate.get(keyAuth);
        user = String.valueOf(responseValudate.get(keyUser));
        pass = String.valueOf(responseValudate.get(keyPass));
        ResponseLoginDTO responseLogin = null;
        if (auth) {
            String token = TokenUtils.generateToken(user, pass, bodyInput);

            responseLogin = new ResponseLoginDTO();
            responseLogin.setNom(dataNom);
            responseLogin.setPrenom(dataPrenom);
            responseLogin.setToken(token);
            responseLogin.setStatus("Success");
            responseLogin.setExpireTime(DateUtilsV8.convertDatePlusByField(new Date(), Calendar.MINUTE, 5));

        } else {
            responseLogin = new ResponseLoginDTO();
            responseLogin.setStatus("Access denied");
        }
        String response = GsonUtils.convertObjectToStringJson(responseLogin);
        return ResponseEntity.status(HttpStatus.OK).body(
                response
        );
    }

    @PostMapping("/call-logout")
    @CrossOrigin
    public ResponseEntity<String> callApiLogout(@RequestHeader HttpHeaders headers, @RequestBody String bodyInput
    ) {
        log.info("Logout : " + bodyInput);

        boolean auth = false;
        String user = null;
        String pass = null;

        Map<String, Object> responseValudate = validateAuth(headers);
        auth = (boolean) responseValudate.get(keyAuth);
        user = String.valueOf(responseValudate.get(keyUser));
        pass = String.valueOf(responseValudate.get(keyPass));

        ResponseLoginDTO responseLogin = null;
        if (auth) {
            TokenUtils.removeToken(user);
            responseLogin = new ResponseLoginDTO();
            responseLogin.setStatus("Success");

        } else {
            responseLogin = new ResponseLoginDTO();
            responseLogin.setStatus("Access denied");
        }

        String response = GsonUtils.convertObjectToStringJson(responseLogin);

        return ResponseEntity.status(HttpStatus.OK).body(
                response
        );
    }


    public static String AUTHORIZATION_HEADER = "Authorization";
    private static String keyAuth = "auth";
    private static String keyUser = "user";
    private static String keyPass = "pass";

    private Map<String, Object> validateAuth(HttpHeaders headers) {
        Map<String, Object> response = new HashMap<>();

        boolean auth = false;
        String user = null;
        String pass = null;

        List<String> list = headers.get(AUTHORIZATION_HEADER.toLowerCase());
        if (list != null && list.size() == 1) {
            String authorization = list.get(0);
            if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
                String base64Credentials = authorization.substring("Basic".length()).trim();

                byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
                String credentials = new String(credDecoded, StandardCharsets.UTF_8);
                String[] values = credentials.split(":", 2);

                user = values[0];
                pass = values[1];
                auth = dataUser.equals(user)
                        && dataPass.equals(pass);
            }
        }

        response.put(keyAuth, auth);
        response.put(keyUser, user);
        response.put(keyPass, pass);

        return response;
    }


}
