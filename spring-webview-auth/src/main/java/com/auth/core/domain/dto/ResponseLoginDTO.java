package com.auth.core.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseLoginDTO {
    private String token;
    private String nom;
    private String prenom;
    private String status;
    private Date expireTime;

}
