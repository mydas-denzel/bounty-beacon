package com.bountybeacon.notification.discord;

import com.bountybeacon.notification.NotificationType;
import com.bountybeacon.program.entity.Program;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscordNotificationServiceTest {

    @Mock
    private DiscordWebhookClient discordClient;

    private DiscordNotificationService service;

    private static final String WEBHOOK_URL = "https://discord.com/api/webhooks/test";

    @BeforeEach
    void setUp() {
        service = new DiscordNotificationService(discordClient);
        ReflectionTestUtils.setField(service, "webhookUrl", WEBHOOK_URL);
    }

    @Test
    void sendNotification_ShouldSendNewProgramNotification() {
        // Given
        Program program = Program.builder()
                .name("Test Program")
                .provider("HACKERONE")
                .description("Test Description")
                .url("https://example.com")
                .bounty(true)
                .build();
        
        when(discordClient.sendWebhook(eq(WEBHOOK_URL), any())).thenReturn(Mono.empty());

        // When
        service.sendNotification(program, NotificationType.NEW_PROGRAM);

        // Then
        ArgumentCaptor<DiscordWebhookRequest> requestCaptor = ArgumentCaptor.forClass(DiscordWebhookRequest.class);
        verify(discordClient).sendWebhook(eq(WEBHOOK_URL), requestCaptor.capture());
        
        DiscordWebhookRequest request = requestCaptor.getValue();
        assertThat(request.getEmbeds()).hasSize(1);
        DiscordEmbed embed = request.getEmbeds().get(0);
        assertThat(embed.getTitle()).contains("New Program Found");
        assertThat(embed.getColor()).isEqualTo(0x00FF00);
    }

    @Test
    void sendNotification_ShouldSendUpdatedProgramNotification() {
        // Given
        Program program = Program.builder()
                .name("Test Program")
                .provider("HACKERONE")
                .build();
        
        when(discordClient.sendWebhook(eq(WEBHOOK_URL), any())).thenReturn(Mono.empty());

        // When
        service.sendNotification(program, NotificationType.PROGRAM_UPDATED);

        // Then
        ArgumentCaptor<DiscordWebhookRequest> requestCaptor = ArgumentCaptor.forClass(DiscordWebhookRequest.class);
        verify(discordClient).sendWebhook(eq(WEBHOOK_URL), requestCaptor.capture());
        
        DiscordWebhookRequest request = requestCaptor.getValue();
        assertThat(request.getEmbeds()).hasSize(1);
        DiscordEmbed embed = request.getEmbeds().get(0);
        assertThat(embed.getTitle()).contains("Program Updated");
        assertThat(embed.getColor()).isEqualTo(0xFFFF00);
    }

    @Test
    void sendNotification_ShouldNotSend_WhenWebhookUrlNotConfigured() {
        // Given
        ReflectionTestUtils.setField(service, "webhookUrl", "");
        Program program = Program.builder().name("Test").build();

        // When
        service.sendNotification(program, NotificationType.NEW_PROGRAM);

        // Then
        verify(discordClient, never()).sendWebhook(anyString(), any());
    }
}
