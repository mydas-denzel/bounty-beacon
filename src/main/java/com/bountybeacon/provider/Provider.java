package com.bountybeacon.provider;

import com.bountybeacon.program.entity.Program;
import java.util.List;

public interface Provider {
    ProviderType getType();
    List<Program> fetchPrograms();
}
