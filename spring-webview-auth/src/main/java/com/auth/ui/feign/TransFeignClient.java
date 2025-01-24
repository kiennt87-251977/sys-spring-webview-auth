package com.auth.ui.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "core-service", value = "", url = "${url.api.path}")
public interface TransFeignClient {

    @GetMapping(value = "/api/v1/public/trans-log/getTransHisBy/{pathVariable}")
    String getTransHisBy(@PathVariable("pathVariable") String walletId);

    @GetMapping(value = "/api/v1/public/trans-log/getTransChart")
    String getTransChart(@RequestParam(value = "fromDate") String fromDate, @RequestParam(value = "toDate") String toDate);

    @PostMapping(value = "/api/v1/public/accounting-log/postQuerySql")
    String callApiExecute02(@RequestHeader HttpHeaders headers
            , @RequestBody String sqlString);

    @GetMapping(value = "/api/v1/user/by-phone")
    Object getPhoneBy(@RequestHeader HttpHeaders headers, @RequestParam(value = "phoneNumber") String phoneNumber);

}
