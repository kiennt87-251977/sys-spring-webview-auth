package com.web.ui.controller.impl;

import com.web.core.domain.constant.ErrorCode;
import com.web.core.domain.dto.ResponseApiDTO;
import com.web.core.domain.dto.RequestDataChartDTO;
import com.web.core.service.AccountService;
import com.web.ui.controller.AccountController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountControllerImpl extends RequestBaseControllerImpl implements AccountController {

    private final AccountService service;

    //***********************************************************************************
    public ResponseEntity<ResponseApiDTO> getInfoBy(@RequestParam String accountId) {

        ResponseApiDTO responseApi = new ResponseApiDTO();

        var responseObject = service.getInfoBy(accountId);
        responseApi.setData(responseObject);

        responseApi.setResponseCode(ErrorCode.TYPE.SUCCESS.getCode());
        responseApi.setMessage(ErrorCode.TYPE.SUCCESS.getMessage());

        return ResponseEntity.status(HttpStatus.OK).body(
                responseApi
        );
    }

    @Override
    public ResponseEntity<ResponseApiDTO> getTransChart(String fromDate, String toDate) {

        ResponseApiDTO responseApi = new ResponseApiDTO();

        var responseObject = service.getTransChart(fromDate, toDate);
        responseApi.setData(responseObject);

        responseApi.setResponseCode(ErrorCode.TYPE.SUCCESS.getCode());
        responseApi.setMessage(ErrorCode.TYPE.SUCCESS.getMessage());

        return ResponseEntity.status(HttpStatus.OK).body(
                responseApi
        );
    }

    @Override
    public ResponseEntity<ResponseApiDTO> getTpsChart(String fromDate, String toDate) {
        ResponseApiDTO responseApi = new ResponseApiDTO();

        var responseObject = service.getTpsChart(fromDate, toDate);
        responseApi.setData(responseObject);

        responseApi.setResponseCode(ErrorCode.TYPE.SUCCESS.getCode());
        responseApi.setMessage(ErrorCode.TYPE.SUCCESS.getMessage());

        return ResponseEntity.status(HttpStatus.OK).body(
                responseApi
        );
    }

    @Override
    public ResponseEntity<ResponseApiDTO> getStatusChart(String fromDate, String toDate) {
        ResponseApiDTO responseApi = new ResponseApiDTO();

        var responseObject = service.getStatusChart(fromDate, toDate);
        responseApi.setData(responseObject);

        responseApi.setResponseCode(ErrorCode.TYPE.SUCCESS.getCode());
        responseApi.setMessage(ErrorCode.TYPE.SUCCESS.getMessage());

        return ResponseEntity.status(HttpStatus.OK).body(
                responseApi
        );
    }

    //***********************************************************************************
    @Override
    public ResponseEntity<ResponseApiDTO> getDataTable(RequestDataChartDTO requestDataChart) {

        ResponseApiDTO responseApi = new ResponseApiDTO();

        var responseObject = service.getDataTable(requestDataChart);
        responseApi.setData(responseObject);

        responseApi.setResponseCode(ErrorCode.TYPE.SUCCESS.getCode());
        responseApi.setMessage(ErrorCode.TYPE.SUCCESS.getMessage());

        return ResponseEntity.status(HttpStatus.OK).body(
                responseApi
        );
    }

}
