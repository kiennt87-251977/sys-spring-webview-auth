package com.web.core.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AccountApiRequestDTO {
    private String messageSignature;
    private String mti;
    private String transType;
    private String processCode;
    private String requestId;
    private String transmissionDateTime;

}
