package com.web.core.domain.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetSuperAgentStaffResponseDTO {
    private Long partyRoleId;
    private String name;
}
