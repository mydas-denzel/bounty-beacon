package com.bountybeacon.program.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDto {
    private Long id;
    private String name;
    private String handle;
    private String provider;
    private String description;
    private String url;
    private String logoUrl;
    private boolean bounty;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
