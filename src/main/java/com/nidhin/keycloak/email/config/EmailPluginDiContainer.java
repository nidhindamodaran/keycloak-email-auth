package com.nidhin.keycloak.email.config;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class EmailPluginDiContainer {

    private static final Injector injector = Guice.createInjector();

    public static <T> T getInstance(Class<T> var1) {
        return injector.getInstance(var1);
    }

}
