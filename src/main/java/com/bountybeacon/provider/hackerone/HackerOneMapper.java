package com.bountybeacon.provider.hackerone;

import com.bountybeacon.program.entity.Program;
import com.bountybeacon.provider.ProviderType;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HackerOneMapper {
    public List<Program> toPrograms(HackerOneResponse response) {
        if (response == null || response.getData() == null) return List.of();
        return response.getData().stream()
                .map(this::toProgram)
                .collect(Collectors.toList());
    }

    private Program toProgram(HackerOneResponse.HackerOneProgram h1p) {
        HackerOneResponse.Attributes attr = h1p.getAttributes();
        return Program.builder()
                .name(attr.getName())
                .handle(attr.getHandle())
                .provider(ProviderType.HACKERONE.name())
                .description(attr.getDescription())
                .url(attr.getUrl())
                .logoUrl(attr.getProfile_picture())
                .bounty(attr.isOffers_bounties())
                .createdAt(attr.getCreated_at() != null ? OffsetDateTime.parse(attr.getCreated_at()).toLocalDateTime() : null)
                .updatedAt(attr.getUpdated_at() != null ? OffsetDateTime.parse(attr.getUpdated_at()).toLocalDateTime() : null)
                .build();
    }
}
