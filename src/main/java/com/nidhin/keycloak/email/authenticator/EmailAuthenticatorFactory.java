package com.nidhin.keycloak.email.authenticator;

import com.nidhin.keycloak.email.config.EmailPluginDiContainer;
import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.List;

public class EmailAuthenticatorFactory implements AuthenticatorFactory {

    public static final String PROVIDER_ID = "email-code-authenticator";

    private static final Logger logger = Logger.getLogger(EmailAuthenticatorFactory.class);

    private static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.DISABLED
    };

    private EmailAuthenticator emailAuthenticator;

    @Override
    public String getId() {
        logger.debug("getId called ... returning " + PROVIDER_ID);
        return PROVIDER_ID;
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        logger.debug("create called ...");
        return emailAuthenticator;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        logger.debug("getRequirementChoices called ...");
        return REQUIREMENT_CHOICES;
    }

    @Override
    public boolean isUserSetupAllowed() {
        logger.debug("isUserSetupAllowed called ...");
        return true;
    }

    @Override
    public boolean isConfigurable() {
        logger.debug("isConfigurable called ...");
        return false;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        logger.debug("getConfigProperties called ...");
        return null;
    }

    @Override
    public String getHelpText() {
        logger.debug("getHelpText called ...");
        return "A email code that a user has to provide.";
    }

    @Override
    public String getDisplayType() {
        logger.debug("getDisplayType called ...");
        return "Email Code";
    }

    @Override
    public String getReferenceCategory() {
        logger.debug("getReferenceCategory called ...");
        return "Email Code";
    }

    @Override
    public void init(Config.Scope config) {
        logger.debug("init called ...");
        emailAuthenticator = EmailPluginDiContainer.getInstance(EmailAuthenticator.class);
        logger.debug("init finished ...");
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        logger.debug("postInit called ...");
    }

    @Override
    public void close() {
        logger.debug("close called ...");
    }

}
