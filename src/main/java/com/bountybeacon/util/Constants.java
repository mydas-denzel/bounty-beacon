package com.bountybeacon.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String API_V1_PREFIX = "/api/v1";
    public static final String PROGRAMS_ENDPOINT = API_V1_PREFIX + "/programs";
    
    public static final String HACKERONE_URL = "https://api.hackerone.com/v1/hackers";
    // In src/main/java/com/bountybeacon/util/Constants.java
    public static final String BUGCROWD_URL = "https://raw.githubusercontent.com/arkadiyt/bounty-targets-data/main/data/bugcrowd_data.json";
    public static final String INTIGRITI_URL = "https://api.intigriti.com/external/researcher/v1";
}
