package com.nidhin.keycloak.email.jpa;

import org.keycloak.Config;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

/**
 * @author <a href="mailto:nidhin.d.nambiar@gmail.com">Nidhin Damodaran</a>
 *
 * VerificationCodeJpaEntityProviderFactory.
 */

public class VerificationCodeJpaEntityProviderFactory implements JpaEntityProviderFactory {

    protected static final String ID = "verification-code-entity-provider";

    @Override
    public JpaEntityProvider create(KeycloakSession session) {
        return new VerificationCodeJpaEntityProvider();
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void init(Config.Scope config) {}

    @Override
    public void postInit(KeycloakSessionFactory factory) {}

    @Override
    public void close() {}
}
