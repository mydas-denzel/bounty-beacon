package com.bountybeacon.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProviderRegistry {
    private final Map<ProviderType, Provider> providers;

    public ProviderRegistry(List<Provider> providerList) {
        this.providers = providerList.stream()
                .collect(Collectors.toMap(Provider::getType, Function.identity()));
    }

    public Optional<Provider> getProvider(ProviderType type) {
        return Optional.ofNullable(providers.get(type));
    }

    public List<Provider> getAllProviders() {
        return List.copyOf(providers.values());
    }
}
