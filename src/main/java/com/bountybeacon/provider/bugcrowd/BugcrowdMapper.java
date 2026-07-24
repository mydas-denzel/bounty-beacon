package com.bountybeacon.provider.bugcrowd;

import com.bountybeacon.program.entity.Program;
import com.bountybeacon.provider.ProviderType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BugcrowdMapper {
    public List<Program> toPrograms(BugcrowdResponse response) {
        if (response == null || response.getData() == null) return List.of();
        return response.getData().stream()
                .map(this::toProgram)
                .collect(Collectors.toList());
    }

    private Program toProgram(BugcrowdResponse.BugcrowdProgram bp) {
        BugcrowdResponse.Attributes attr = bp.getAttributes();
        return Program.builder()
                .name(attr.getName())
                .handle(attr.getCode())
                .provider(ProviderType.BUGCROWD.name())
                .description(attr.getDescription())
                .url("https://bugcrowd.com/" + attr.getCode())
                .logoUrl(attr.getLogo_url())
                .bounty(true) // Bugcrowd usually implies bounty if in this list, or we could refine
                .build();
    }
}
