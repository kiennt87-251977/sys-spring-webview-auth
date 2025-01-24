package com.auth.ui.feign;

import com.auth.core.domain.dto.RequestDataChartDTO;
import com.auth.core.domain.dto.ResponseApiDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.awt.color.ICC_Profile;

@FeignClient("${url.webview-core}")
public interface WebCoreFeignClient {

    @GetMapping(value = "/service/core-api-service/account/v1/pingAvailable")
    Object pingAvailable(@RequestHeader HttpHeaders headers);


    @PostMapping(value = "/service/core-api-service/account/v1/get-data-table")
    Object getDataAccTable(@RequestHeader HttpHeaders headers
            , @RequestBody RequestDataChartDTO requestDataChartDTO
    );

    @GetMapping(value = "/service/core-api-service/account/v1/get-trans-chart")
    String getTransChart(@RequestParam(value = "fromDate") String fromDate
            , @RequestParam(value = "toDate") String toDate);

    @GetMapping(value = "/service/core-api-service/account/v1/get-tps-chart")
    ResponseApiDTO getTpsChart(@RequestParam(name = "fromDate") String fromDate
            , @RequestParam(name = "toDate") String toDate
    );

    @GetMapping(value = "/service/core-api-service/account/v1/get-status-chart")
    ResponseApiDTO getStatusChart(@RequestParam(name = "fromDate") String fromDate
            , @RequestParam(name = "toDate") String toDate
    );
}
