package com.auth.core.domain.dto;

import lombok.Data;

@Data
public class ResponseDataChartDTO {
    private Integer idAcc;
    private String nameAcc;
    private String uuid;
    private Boolean status;
}
