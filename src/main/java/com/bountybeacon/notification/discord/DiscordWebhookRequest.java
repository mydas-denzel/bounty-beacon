package com.bountybeacon.notification.discord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscordWebhookRequest {
    private String content;
    private List<DiscordEmbed> embeds;
}
