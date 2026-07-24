package com.bountybeacon.program.mapper;

import com.bountybeacon.program.dto.ProgramDto;
import com.bountybeacon.program.entity.Program;
import org.springframework.stereotype.Component;

@Component
public class ProgramMapper {
    public ProgramDto toDto(Program program) {
        if (program == null) return null;
        return ProgramDto.builder()
                .id(program.getId())
                .name(program.getName())
                .handle(program.getHandle())
                .provider(program.getProvider())
                .description(program.getDescription())
                .url(program.getUrl())
                .logoUrl(program.getLogoUrl())
                .bounty(program.isBounty())
                .createdAt(program.getCreatedAt())
                .updatedAt(program.getUpdatedAt())
                .build();
    }
}
