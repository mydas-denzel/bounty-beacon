package com.bountybeacon.provider.bugcrowd;

import com.bountybeacon.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BugcrowdClient {
    private final WebClient webClient;

    @Value("${provider.bugcrowd.api-key:}")
    private String apiKey;

    public Mono<BugcrowdResponse> fetchPrograms() {
        return webClient.get()
                .uri(Constants.BUGCROWD_URL + "/programs")
                .headers(headers -> {
                    if (!apiKey.isEmpty()) {
                        headers.setBearerAuth(apiKey);
                        headers.set("Accept", "application/vnd.bugcrowd.v4+json");
                    }
                })
                .retrieve()
                .bodyToMono(BugcrowdResponse.class);
    }
}
