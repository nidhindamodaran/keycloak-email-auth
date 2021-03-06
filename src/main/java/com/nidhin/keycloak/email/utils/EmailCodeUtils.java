package com.nidhin.keycloak.email.utils;

import com.nidhin.keycloak.email.Constants;
import com.nidhin.keycloak.email.service.EmailContext;
import org.jboss.resteasy.spi.HttpRequest;

import javax.inject.Singleton;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Random;

@Singleton
public class EmailCodeUtils {

    public boolean validateEmailCode(EmailContext context) {
        String storedCode = context.getAuthenticationSession().getAuthNote("email_code");
        return validateEmailCode(context.getHttpRequest(), storedCode);
    }

    public boolean emailStoreIsEmpty(EmailContext context) {
        return context.getAuthenticationSession().getAuthNote("email_code") == null;
    }

    public void storeEmailCode(EmailContext context, String code) {
        context.getAuthenticationSession().setAuthNote("email_code", code);
    }

    public String generateCode() {
        return String.format("%06d", new Random().nextInt(1000000));
    }

    private boolean validateEmailCode(HttpRequest httpRequest, String storedCode) {
        MultivaluedMap<String, String> formData = httpRequest.getDecodedFormParameters();
        String emailCode = formData.getFirst(Constants.ANSW_EMAIL_CODE);
        return storedCode.equals(emailCode);
    }

}
