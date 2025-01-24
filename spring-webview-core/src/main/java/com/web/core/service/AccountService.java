package com.web.core.service;


import com.web.core.domain.dto.AccountApiResponseDTO;
import com.web.core.domain.dto.RequestDataChartDTO;
import com.web.core.domain.dto.ResponseDataChartDTO;
import com.web.core.domain.dto.ResponseTransChartDTO;
import com.web.core.domain.dto.StatusChartDTO;


import java.util.List;

public interface AccountService extends ServiceBase {
    AccountApiResponseDTO getInfoBy(String accountId);

    List<ResponseDataChartDTO> getDataTable(RequestDataChartDTO requestDataChart);

    List<ResponseTransChartDTO> getTransChart(String fromDate, String toDate);

    Integer getTpsChart(String fromDate, String toDate);

    List<StatusChartDTO> getStatusChart(String fromDate, String toDate);
}
