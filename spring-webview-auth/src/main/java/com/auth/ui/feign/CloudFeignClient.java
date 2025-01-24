package com.auth.ui.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@FeignClient("${url.uaa}")
public interface CloudFeignClient {

    @GetMapping(value = "/api/v1/user/by-phone")
    Object getPhoneBy(@RequestHeader HttpHeaders headers, @RequestParam(value = "phoneNumber") String phoneNumber);

}
