package com.web.core.domain.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetSuperAgentsResponseDTO {
    private Long accountId;
    private String name;
    private Long staffCount;
}
