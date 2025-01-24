package com.auth.core.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class ResponseApiDTO {
    private String responseCode;
    private String message;
    private Date dateCreated;
    private Object data;

    public ResponseApiDTO() {
        dateCreated = new Date();
    }
}
