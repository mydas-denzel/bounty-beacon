package com.bountybeacon.provider.bugcrowd;

import com.bountybeacon.program.entity.Program;
import com.bountybeacon.provider.Provider;
import com.bountybeacon.provider.ProviderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BugcrowdProvider implements Provider {
    private final BugcrowdClient client;
    private final BugcrowdMapper mapper;

    @Override
    public ProviderType getType() {
        return ProviderType.BUGCROWD;
    }

    @Override
    public List<Program> fetchPrograms() {
        log.info("Fetching programs from Bugcrowd");
        try {
            BugcrowdResponse response = client.fetchPrograms().block();
            return mapper.toPrograms(response);
        } catch (Exception e) {
            log.error("Error fetching programs from Bugcrowd", e);
            return List.of();
        }
    }
}
