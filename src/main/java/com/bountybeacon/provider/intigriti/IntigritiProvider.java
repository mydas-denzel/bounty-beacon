package com.bountybeacon.provider.intigriti;

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
public class IntigritiProvider implements Provider {
    private final IntigritiClient client;
    private final IntigritiMapper mapper;

    @Override
    public ProviderType getType() {
        return ProviderType.INTIGRITI;
    }

    @Override
    public List<Program> fetchPrograms() {
        log.info("Fetching programs from Intigriti");
        try {
            IntigritiResponse response = client.fetchPrograms().block();
            return mapper.toPrograms(response);
        } catch (Exception e) {
            log.error("Error fetching programs from Intigriti", e);
            return List.of();
        }
    }
}
