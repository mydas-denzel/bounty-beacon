package com.bountybeacon.provider.hackerone;

import lombok.Data;
import java.util.List;

@Data
public class HackerOneResponse {
    private List<HackerOneProgram> data;

    @Data
    public static class HackerOneProgram {
        private String id;
        private Attributes attributes;
    }

    @Data
    public static class Attributes {
        private String name;
        private String handle;
        private String description;
        private String url;
        private String profile_picture;
        private boolean offers_bounties;
        private String created_at;
        private String updated_at;
    }
}
