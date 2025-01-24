package com.web.ui.controller.impl;

import com.web.ui.controller.RequestBaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class RequestBaseControllerImpl implements RequestBaseController {

    public ResponseEntity<?> pingAvailable() {
        Map<String, String> responseObject = new HashMap<>();
        responseObject.put("ResponseCode", "00000");
        responseObject.put("ResponseDesc", "Success");
        responseObject.put("Data", "ping service success");
        responseObject.put("DateTime", String.valueOf(new Date()));
        return ResponseEntity.status(HttpStatus.OK).body(
                responseObject
        );
    }

}
