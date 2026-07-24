package com.bountybeacon.provider.bugcrowd;

import com.bountybeacon.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BugcrowdClient {
    private final WebClient webClient;
    private final ObjectMapper objectMapper; // Inject Jackson

    public Mono<BugcrowdProgram[]> fetchPrograms() {
        return webClient.get()
                .uri(Constants.BUGCROWD_URL)
                .retrieve()
                .bodyToMono(String.class) // 1. Fetch as a plain String
                .map(jsonString -> {
                    try {
                        // 2. Parse the string manually into your array
                        return objectMapper.readValue(jsonString, BugcrowdProgram[].class);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse Bugcrowd JSON", e);
                    }
                });
    }
}