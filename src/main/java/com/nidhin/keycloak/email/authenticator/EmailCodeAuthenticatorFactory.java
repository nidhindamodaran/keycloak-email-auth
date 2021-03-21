package com.nidhin.keycloak.email.authenticator;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.authentication.ConfigurableAuthenticatorFactory;
import com.nidhin.keycloak.email.authenticator.EmailCodeAuthenticator;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.ArrayList;
import java.util.List;

public class EmailCodeAuthenticatorFactory implements AuthenticatorFactory, ConfigurableAuthenticatorFactory {
    public static final String MAX_AGE = "verificationCode.max.age";
    public static final String KIND = "verificationCode.kind";

    public static final String PROVIDER_ID = "email-code-authenticator";

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
        return PROVIDER_ID;
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return new EmailCodeAuthenticator(session);
    }

    private static AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.DISABLED
    };
    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return true;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    private static final List<ProviderConfigProperty> configProperties = new ArrayList<ProviderConfigProperty>();

    static {
        ProviderConfigProperty maxAge;
        maxAge = new ProviderConfigProperty();
        maxAge.setName(MAX_AGE);
        maxAge.setLabel("Verification Code Max Age");
        maxAge.setType(ProviderConfigProperty.STRING_TYPE);
        maxAge.setHelpText("Max age in seconds of the verification codes.");
        configProperties.add(maxAge);

        ProviderConfigProperty kind = new ProviderConfigProperty();
        kind.setName(KIND);
        kind.setLabel("Verification Code Kind");
        kind.setType(ProviderConfigProperty.STRING_TYPE);
        kind.setHelpText("a string that identifies what the verification code is used for, if this is set, " +
                "a parameter of 'kind' is required to be equal with set value");
        configProperties.add(kind);
    }

    @Override
    public String getDisplayType() {
        return "Provide verification code";
    }

    @Override
    public String getHelpText() {
        return "Provide verification code";
    }

    @Override
    public String getReferenceCategory() {
        return "Verification Code Grant";
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }
}

