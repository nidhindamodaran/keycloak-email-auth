package com.nidhin.keycloak.email.authenticator;

import com.google.inject.Inject;
import com.nidhin.keycloak.email.service.AuthenticationFlowContextEmailAdapter;
import com.nidhin.keycloak.email.service.EmailService;
import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import javax.inject.Singleton;

@Singleton
public class EmailAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(EmailAuthenticator.class);

    private final EmailService emailService;

    @Inject
    public EmailAuthenticator(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        logger.debug("authenticate called ...");
        emailService.emailChallenge(new AuthenticationFlowContextEmailAdapter(context));
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        logger.debug("action called ...");
        emailService.verifyEmailChallenge(new AuthenticationFlowContextEmailAdapter(context));
    }

    @Override
    public boolean requiresUser() {
        logger.debug("requiresUser called ...");
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        logger.debug("configuredFor called ...");
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        logger.debug("setRequiredActions called ...");
    }

    @Override
    public void close() {
        logger.debug("close called ...");
    }
}
