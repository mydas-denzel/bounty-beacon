package com.bountybeacon.provider.hackerone;

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
public class HackerOneProvider implements Provider {
    private final HackerOneClient client;
    private final HackerOneMapper mapper;

    @Override
    public ProviderType getType() {
        return ProviderType.HACKERONE;
    }

    @Override
    public List<Program> fetchPrograms() {
        log.info("Fetching programs from HackerOne");
        try {
            HackerOneResponse response = client.fetchPrograms().block();
            return mapper.toPrograms(response);
        } catch (Exception e) {
            log.error("Error fetching programs from HackerOne", e);
            return List.of();
        }
    }
}
