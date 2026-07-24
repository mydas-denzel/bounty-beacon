package com.bountybeacon.notification.discord;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DiscordWebhookClient {
    private final WebClient webClient;

    public Mono<Void> sendWebhook(String webhookUrl, DiscordWebhookRequest request) {
        return webClient.post()
                .uri(webhookUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
