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
public class DiscordEmbed {
    private String title;
    private String description;
    private String url;
    private int color;
    private Thumbnail thumbnail;
    private List<Field> fields;

    @Data
    @AllArgsConstructor
    public static class Thumbnail {
        private String url;
    }

    @Data
    @AllArgsConstructor
    public static class Field {
        private String name;
        private String value;
        private boolean inline;
    }
}
