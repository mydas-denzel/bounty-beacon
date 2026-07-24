package com.bountybeacon.provider.bugcrowd;

import lombok.Data;

@Data
public class BugcrowdProgram {
    private String name;
    private String url;
    private boolean allows_disclosure;
    private boolean managed_by_bugcrowd;
    private String safe_harbor;
}