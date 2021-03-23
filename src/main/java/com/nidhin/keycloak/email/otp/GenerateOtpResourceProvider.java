package com.nidhin.keycloak.email.otp;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class GenerateOtpResourceProvider implements RealmResourceProvider {

    private final KeycloakSession session;

    public GenerateOtpResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return new GenerateOtpResource(session);
    }

    @Override
    public void close() {
    }

}
