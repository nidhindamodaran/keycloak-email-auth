package com.nidhin.keycloak.email.otp;

import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

public class GenerateOtpResourceFactory implements RealmResourceProviderFactory {
    public static final String ID = "otp";

    public RealmResourceProvider create(KeycloakSession session) {
        return new GenerateOtpResource(session);
    }

    public void init(Scope config) {
    }

    public void postInit(KeycloakSessionFactory factory) {
    }

    public void close() {
    }

    public String getId() {
        return ID;
    }

}