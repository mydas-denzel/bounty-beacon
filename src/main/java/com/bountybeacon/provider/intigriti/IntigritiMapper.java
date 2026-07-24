package com.bountybeacon.provider.intigriti;

import com.bountybeacon.program.entity.Program;
import com.bountybeacon.provider.ProviderType;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IntigritiMapper {
    public List<Program> toPrograms(IntigritiResponse response) {
        if (response == null || response.getData() == null) return List.of();
        return response.getData().stream()
                .map(this::toProgram)
                .collect(Collectors.toList());
    }

    private Program toProgram(IntigritiResponse.IntigritiProgram ip) {
        return Program.builder()
                .name(ip.getName())
                .handle(ip.getHandle())
                .provider(ProviderType.INTIGRITI.name())
                .description(ip.getDescription())
                .url("https://app.intigriti.com/programs/" + ip.getHandle())
                .logoUrl(ip.getLogoUrl())
                .bounty(ip.isBounty())
                .createdAt(parseDate(ip.getCreatedAt()))
                .updatedAt(parseDate(ip.getUpdatedAt()))
                .build();
    }

    private LocalDateTime parseDate(String dateStr) {
        if (dateStr == null) return null;
        try {
            // Intigriti often uses epoch or ISO. Assuming ISO for now or long epoch.
            return LocalDateTime.ofInstant(Instant.parse(dateStr), ZoneId.systemDefault());
        } catch (Exception e) {
            return null;
        }
    }
}
