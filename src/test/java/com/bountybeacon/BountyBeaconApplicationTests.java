package com.bountybeacon;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.reactive.function.client.WebClient;
import com.bountybeacon.notification.discord.DiscordWebhookClient;

@SpringBootTest
@ActiveProfiles("test")
class BountyBeaconApplicationTests {

    @MockBean
    private WebClient.Builder webClientBuilder;

    @MockBean
    private WebClient webClient;

    @MockBean
    private DiscordWebhookClient discordWebhookClient;

    @Test
    void contextLoads() {
    }

}
