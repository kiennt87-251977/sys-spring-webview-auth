package com.web.core.service.impl;

import com.web.core.configuration.utils.RandomUtils;
import com.web.core.domain.dto.*;
import com.web.core.domain.constant.ErrorCode;
import com.web.core.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl extends ServiceBaseImpl implements AccountService {

    @Value("${service.test.mode}")
    protected Boolean testMode;

//    private final AccountAdapter accountAdapter;

    public AccountApiResponseDTO getInfoBy(String accountId) {
//        AccountBO account = accountAdapter.findByAccountId(Long.valueOf(accountId));

        AccountApiResponseDTO responseObject = new AccountApiResponseDTO();
        responseObject.setCode(ErrorCode.TYPE.ACCOUNT_DB.getCode());
        responseObject.setDesc(ErrorCode.TYPE.ACCOUNT_DB.getMessage());

//        responseObject.setData(account);

        return responseObject;
    }

    @Override
    public List<ResponseDataChartDTO> getDataTable(RequestDataChartDTO requestDataChart) {
        List<ResponseDataChartDTO> response = new ArrayList<>();
        int i = 0;
        while (i < requestDataChart.getNumbRecord()) {
            ResponseDataChartDTO object = new ResponseDataChartDTO();
            object.setIdAcc(i);
            object.setNameAcc(requestDataChart.getNameRecord() + "_" + i);

            object.setUuid(UUID.randomUUID().toString());
            if (RandomUtils.randInt(0, 2) % 3 == 0) {
                object.setStatus(false);
            } else {
                object.setStatus(true);
            }

            response.add(object);
            i++;
        }


        return response;
    }

    @Override
    public List<ResponseTransChartDTO> getTransChart(String fromDate, String toDate) {

        List<ResponseTransChartDTO> response = new ArrayList<>();
        int i = 0;
        while (i < 1000) {
            ResponseTransChartDTO object = new ResponseTransChartDTO();
            object.setId(i);
//            object.setNameAcc(requestDataChart.getNameRecord() + "_" + i);

            object.setUuid(UUID.randomUUID().toString());
            if (i % 3 == 0) {
                object.setStatus(false);
            } else {
                object.setStatus(true);
            }
            response.add(object);
            i++;
        }


        return response;
    }


    private static Integer currentTps = 0;

    @Override
    public Integer getTpsChart(String fromDate, String toDate) {
        currentTps += RandomUtils.randInt(-10, 10);
        if (currentTps < 0) {
            currentTps = -currentTps;
        }
        return currentTps;
    }


    @Override
    public List<StatusChartDTO> getStatusChart(String fromDate, String toDate) {
        List<StatusChartDTO> resposne = new ArrayList<>();

        StatusChartDTO statusSuccess = new StatusChartDTO();
        statusSuccess.setName("Success");
        statusSuccess.setValue(RandomUtils.randInt(50, 100));
        resposne.add(statusSuccess);

        StatusChartDTO statusFail = new StatusChartDTO();
        statusFail.setName("Fail");
        statusFail.setValue(100 - statusSuccess.getValue());
        resposne.add(statusFail);

        StatusChartDTO statusProcessing = new StatusChartDTO();
        statusProcessing.setName("Processing");
        statusProcessing.setValue(RandomUtils.randInt(0, 30));
        resposne.add(statusProcessing);

        StatusChartDTO statusError = new StatusChartDTO();
        statusError.setName("Error");
        statusError.setValue(RandomUtils.randInt(0, 20));
        resposne.add(statusError);

        StatusChartDTO statusIncomming = new StatusChartDTO();
        statusIncomming.setName("Incomming");
        statusIncomming.setValue(RandomUtils.randInt(0, 20));
        resposne.add(statusIncomming);

        return resposne;
    }

}
