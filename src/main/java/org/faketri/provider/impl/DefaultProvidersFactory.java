package org.faketri.provider.impl;

import org.faketri.provider.Provider;
import org.faketri.provider.ProvidersFactory;
import org.faketri.proxy.GlobalProxyHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class DefaultProvidersFactory implements ProvidersFactory {

    private final List<Provider> providers;

    public DefaultProvidersFactory() {
        providers = new ArrayList<>();
    }

    public DefaultProvidersFactory(List<Provider> providers) {
        this.providers = providers;
    }

    @Override
    public void register(Provider provider) {
        providers.add(GlobalProxyHandler.newProxy(provider, Provider.class));
    }

    @Override
    public Provider getProvider(String systemProvider) {
        return providers
                .stream()
                .max(Comparator.comparingInt(a -> a.canCheck(systemProvider)))
                .orElseThrow();
    }
}
