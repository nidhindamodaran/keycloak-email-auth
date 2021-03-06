package com.nidhin.keycloak.email.utils;

import com.nidhin.keycloak.email.Constants;
import org.apache.commons.lang.StringUtils;

import javax.inject.Singleton;

@Singleton
public class emailUtils {

    /**
     * @return return error message code if error of null if fine
     */
    public String validateEmail(
            String existingEmail,
            String providedEmail) {
        if (StringUtils.isEmpty(providedEmail)) {
            return Constants.MESSAGE_EMAIL_NO_VALID;
        }

        String provided = providedEmail.trim();
        String existing = existingEmail.trim();

        if (!provided.equals(existing)) {
            return Constants.MESSAGE_EMAIL_NOT_EQUAL;
        }
        return null;
    }

}
