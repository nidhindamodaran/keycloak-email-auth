package com.nidhin.keycloak.email.utils;

import com.nidhin.keycloak.email.Constants;
import org.keycloak.models.UserModel;

import javax.inject.Singleton;

@Singleton
public class UserAttributeUtils {

    public String getEmail(UserModel user) {
        String emailCreds = user.getFirstAttribute(Constants.ATTR_EMAIL);

        String email = null;

        if (emailCreds != null && !emailCreds.isEmpty()) {
            email = emailCreds;
        }

        return email;
    }

}
