package com.bountybeacon.provider.hackerone;

import com.bountybeacon.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HackerOneClient {
    private final WebClient webClient;

    @Value("${provider.hackerone.username:}")
    private String username;

    @Value("${provider.hackerone.api-key:}")
    private String apiKey;

    public Mono<HackerOneResponse> fetchPrograms() {
        return webClient.get()
                .uri(Constants.HACKERONE_URL + "/programs")
                .headers(headers -> {
                    if (!username.isEmpty() && !apiKey.isEmpty()) {
                        headers.setBasicAuth(username, apiKey);
                    }
                })
                .retrieve()
                .bodyToMono(HackerOneResponse.class);
    }
}
