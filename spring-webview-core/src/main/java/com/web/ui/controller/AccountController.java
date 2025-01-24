package com.web.ui.controller;

import com.web.core.configuration.ConfigInfoService;
import com.web.core.configuration.ConfigServiceCommon;
import com.web.core.domain.dto.ResponseApiDTO;
import com.web.core.domain.dto.RequestDataChartDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/" + ConfigServiceCommon.ContextApiService.context +
        "/" + ConfigInfoService.END_POINT_NAME +
        "/" + AccountController.SERVICE_NAME +
        "/" + AccountController.VERSION
)
public interface AccountController extends RequestBaseController {
    String SERVICE_NAME = "account";
    String VERSION = "v1";

    //***********************************************************************************
    @GetMapping("/get-info-by")
    ResponseEntity<ResponseApiDTO> getInfoBy(@RequestParam String accountId);

    //***********************************************************************************
    @GetMapping("/get-trans-chart")
    ResponseEntity<ResponseApiDTO> getTransChart(@RequestParam String fromDate, @RequestParam String toDate);

    //***********************************************************************************
    @GetMapping("/get-tps-chart")
    ResponseEntity<ResponseApiDTO> getTpsChart(@RequestParam String fromDate, @RequestParam String toDate);

    //***********************************************************************************
    @GetMapping("/get-status-chart")
    ResponseEntity<ResponseApiDTO> getStatusChart(@RequestParam String fromDate, @RequestParam String toDate);

    //***********************************************************************************
    @PostMapping("/get-data-table")
    ResponseEntity<ResponseApiDTO> getDataTable(@RequestBody RequestDataChartDTO requestDataChart);
}
