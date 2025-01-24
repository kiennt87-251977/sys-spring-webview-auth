package com.web.core.domain.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetOtpResponseDTO {
    private String otp;
    private LocalDate createdDate;
}
