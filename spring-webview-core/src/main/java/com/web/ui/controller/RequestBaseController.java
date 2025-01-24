package com.web.ui.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


public interface RequestBaseController {

    @GetMapping("/pingAvailable")
    ResponseEntity<?> pingAvailable();

}
