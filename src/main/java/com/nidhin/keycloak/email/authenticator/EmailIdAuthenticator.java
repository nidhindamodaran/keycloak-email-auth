package com.nidhin.keycloak.email.authenticator;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import com.nidhin.keycloak.email.authenticator.BaseDirectGrantAuthenticator;
import org.keycloak.events.Errors;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import javax.ws.rs.core.Response;
import java.util.List;

public class EmailIdAuthenticator extends BaseDirectGrantAuthenticator {

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        user.addRequiredAction("EMAIL_ID_GRANT_CONFIG");
    }

    protected UserModel findUser(AuthenticationFlowContext context) {
        List<UserModel> users = context.getSession().users().searchForUserByUserAttribute(
                "email", context.getHttpRequest().getDecodedFormParameters().getFirst("email"), context.getRealm());
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        UserModel user = findUser(context);
        if (user == null) {
            context.getEvent().error(Errors.INVALID_USER_CREDENTIALS);
            Response challenge = errorResponse(Response.Status.UNAUTHORIZED.getStatusCode(), "invalid_grant", "Invalid user credentials");
            context.failure(AuthenticationFlowError.INVALID_USER, challenge);
            return;
        }

        context.setUser(user);
        context.success();
    }
}
