package com.bountybeacon.notification.discord;

import com.bountybeacon.notification.NotificationService;
import com.bountybeacon.notification.NotificationType;
import com.bountybeacon.program.entity.Program;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscordNotificationService implements NotificationService {
    private final DiscordWebhookClient discordClient;

    @Value("${notification.discord.webhook-url:}")
    private String webhookUrl;

    @Override
    public void sendNotification(Program program, NotificationType type) {
        if (webhookUrl == null || webhookUrl.isEmpty()) {
            log.warn("Discord webhook URL not configured, skipping notification");
            return;
        }

        String title = type == NotificationType.NEW_PROGRAM ? "🚀 New Program Found!" : "📝 Program Updated";
        int color = type == NotificationType.NEW_PROGRAM ? 0x00FF00 : 0xFFFF00;

        DiscordEmbed embed = DiscordEmbed.builder()
                .title(title)
                .description(program.getDescription() != null && program.getDescription().length() > 250 
                        ? program.getDescription().substring(0, 250) + "..." 
                        : program.getDescription())
                .url(program.getUrl())
                .color(color)
                .thumbnail(new DiscordEmbed.Thumbnail(program.getLogoUrl()))
                .fields(List.of(
                        new DiscordEmbed.Field("Program", program.getName(), true),
                        new DiscordEmbed.Field("Provider", program.getProvider(), true),
                        new DiscordEmbed.Field("Bounty", program.isBounty() ? "✅ Yes" : "❌ No", true)
                ))
                .build();

        DiscordWebhookRequest request = DiscordWebhookRequest.builder()
                .embeds(List.of(embed))
                .build();

        discordClient.sendWebhook(webhookUrl, request)
                .doOnError(e -> log.error("Failed to send Discord notification", e))
                .subscribe();
    }
}
