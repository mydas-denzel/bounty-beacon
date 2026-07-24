package com.bountybeacon.provider.intigriti;

import com.bountybeacon.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class IntigritiClient {
    private final WebClient webClient;

    @Value("${provider.intigriti.api-key:}")
    private String apiKey;

    public Mono<IntigritiResponse> fetchPrograms() {
        return webClient.get()
                .uri(Constants.INTIGRITI_URL + "/programs")
                .headers(headers -> {
                    if (!apiKey.isEmpty()) {
                        headers.setBearerAuth(apiKey);
                    }
                })
                .retrieve()
                .bodyToMono(IntigritiResponse.class);
    }
}
