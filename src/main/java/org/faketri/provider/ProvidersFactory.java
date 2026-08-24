package org.faketri.provider;


public interface ProvidersFactory {

    void register(Provider provider);
    Provider getProvider(String systemProvider);
}
