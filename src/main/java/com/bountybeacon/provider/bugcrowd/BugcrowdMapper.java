package com.bountybeacon.provider.bugcrowd;

import com.bountybeacon.program.entity.Program;
import com.bountybeacon.provider.ProviderType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BugcrowdMapper {

    public List<Program> toPrograms(BugcrowdProgram[] programs) {
        if (programs == null) return List.of();

        return Arrays.stream(programs)
                .map(this::toProgram)
                .collect(Collectors.toList());
    }

    private Program toProgram(BugcrowdProgram bp) {
        // Extract the handle from the URL (e.g., https://bugcrowd.com/tesla -> tesla)
        String handle = "";
        if (bp.getUrl() != null && bp.getUrl().contains("/")) {
            handle = bp.getUrl().substring(bp.getUrl().lastIndexOf('/') + 1);
        }

        return Program.builder()
                .name(bp.getName())
                .handle(handle)
                .provider(ProviderType.BUGCROWD.name())
                .description("Bugcrowd Program: " + bp.getName()) // Not provided by aggregator
                .url(bp.getUrl())
                .logoUrl(null) // Not provided by aggregator
                .bounty(true) // Defaulting to true
                .build();
    }
}