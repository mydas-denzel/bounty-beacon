package com.bountybeacon.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProviderFactory {
    private final ProviderRegistry registry;

    public Provider getProvider(ProviderType type) {
        return registry.getProvider(type)
                .orElseThrow(() -> new IllegalArgumentException("No provider found for type: " + type));
    }
}
