package com.bountybeacon.provider.intigriti;

import lombok.Data;
import java.util.List;

@Data
public class IntigritiResponse {
    private List<IntigritiProgram> data;

    @Data
    public static class IntigritiProgram {
        private String handle;
        private String name;
        private String description;
        private String logoUrl;
        private boolean isBounty;
        private String createdAt;
        private String updatedAt;
    }
}
