package com.nidhin.keycloak.email.service;

import org.keycloak.Config;
import com.nidhin.keycloak.email.service.VerificationCodeService;
import com.nidhin.keycloak.email.service.VerificationCodeServiceProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class VerificationCodeServiceProviderFactoryImpl implements VerificationCodeServiceProviderFactory {

    @Override
    public VerificationCodeService create(KeycloakSession session) {
        return new VerificationCodeServiceImpl(session);
    }

    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "VerificationCodeServiceProviderFactoryImpl";
    }
}
