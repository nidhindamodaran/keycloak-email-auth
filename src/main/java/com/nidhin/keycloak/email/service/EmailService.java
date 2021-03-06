package com.nidhin.keycloak.email.service;

import com.google.inject.Inject;
import com.nidhin.keycloak.email.Constants;
import com.nidhin.keycloak.email.producer.LogsEmailProducer;
import com.nidhin.keycloak.email.producer.EmailProducer;
import com.nidhin.keycloak.email.utils.emailUtils;
import com.nidhin.keycloak.email.utils.EmailCodeUtils;
import com.nidhin.keycloak.email.utils.UserAttributeUtils;
import org.jboss.logging.Logger;
import org.keycloak.models.UserModel;
import org.keycloak.theme.Theme;

import javax.inject.Singleton;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.text.FieldPosition;
import java.text.MessageFormat;
import java.util.Locale;

@Singleton
public class EmailService {

    public static final String EMAIL_FORM_STATE = "email_verification_form_state";
    public static final String EMAIL_CODE_INPUT = "email_code_input";
    public static final String EMAIL_INPUT = "email_input";
    public static final String CUSTOM_STYLES_ATTRIBUTE = "customStyles";
    public static final String INTL_TEL_INPUT_MIN_CSS = "intlTelInput.min.css";

    private static final Logger logger = Logger.getLogger(EmailService.class);

    private final EmailProducer emailProducer;
    private final EmailCodeUtils emailCodeUtils;
    private final emailUtils emailUtils;
    private final UserAttributeUtils userAttributeUtils;

    @Inject
    public EmailService(
            LogsEmailProducer logsEmailProducer,
            EmailCodeUtils emailCodeUtils,
            emailUtils emailUtils,
            UserAttributeUtils userAttributeUtils
    ) {
        this.emailProducer = logsEmailProducer;
        this.emailCodeUtils = emailCodeUtils;
        this.emailUtils = emailUtils;
        this.userAttributeUtils = userAttributeUtils;
    }

    public void emailChallenge(EmailContext context) {

        if (isResendEmailReload(context)) {
            showResendForm(context);
            return;
        }

        UserModel user = context.getUser();
        String email = userAttributeUtils.getEmail(user);
        Locale locale = context.getSession().getContext().resolveLocale(context.getUser());

        String emailMessageFormat;
        try {
            emailMessageFormat = context.getSession().theme()
                    .getTheme(Theme.Type.LOGIN)
                    .getMessages(locale)
                    .getProperty(Constants.MESSAGE_EMAIL_TEXT);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        if (emailMessageFormat == null) {
            throw new EmailServiceException("failed to find email message format");
        }

        if (emailCodeUtils.emailStoreIsEmpty(context)) {
            String code = emailCodeUtils.generateCode();
            String message = getEmailMessageBody(locale, emailMessageFormat, code);
            try {
                emailProducer.produce(message, email);
            } catch (Exception ex) {
                throw new EmailServiceException("failed to produce email message for username=" + user.getUsername(), ex);
            }
            emailCodeUtils.storeEmailCode(context, code);
        }

        Response challenge = context.form()
                .setInfo(Constants.MESSAGE_EMAIL_CODE_PROMPT)
                .createForm(Constants.FORM_EMAIL_VALIDATION);
        context.challenge(challenge);

        context.getAuthenticationSession().setAuthNote(EMAIL_FORM_STATE, EMAIL_CODE_INPUT);
    }

    public void verifyEmailChallenge(EmailContext context) {
        if (EMAIL_CODE_INPUT.equals(context.getAuthenticationSession().getAuthNote(EMAIL_FORM_STATE))) {
            MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
            if (formData.containsKey(Constants.ANSW_RESEND_EMAIL_CODE)) {
                showResendForm(context);
                return;
            }

            boolean validated = emailCodeUtils.validateEmailCode(context);
            if (!validated) {
                Response challenge = context.form()
                        .setError(Constants.MESSAGE_EMAIL_CODE_NO_VALID)
                        .createForm(Constants.FORM_EMAIL_VALIDATION);
                context.challenge(challenge);
                return;
            }

            context.success();
        } else {
            MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
            if (formData.containsKey(Constants.ANSW_CANCEL_RESEND_EMAIL_CODE)) {
                Response challenge = context.form()
                        .setInfo(Constants.MESSAGE_EMAIL_CODE_PROMPT)
                        .createForm(Constants.FORM_EMAIL_VALIDATION);
                context.challenge(challenge);
                context.getAuthenticationSession().setAuthNote(EMAIL_FORM_STATE, EMAIL_CODE_INPUT);
                return;
            }

            logger.info(formData.getFirst(Constants.ANSW_EMAIL));

            String providedEmail = formData.getFirst(Constants.ANSW_EMAIL);
            String existingEmail = userAttributeUtils.getEmail(context.getUser());

            logger.info("provide = " + providedEmail + "existing=" + existingEmail);

            String errorMessageKey = emailUtils.validateEmail(existingEmail, providedEmail);
            if (errorMessageKey == null) {
                logger.info(String.format("resend email request for %s was successful", existingEmail));
                emailCodeUtils.storeEmailCode(context, null);
                context.getAuthenticationSession().setAuthNote(EMAIL_FORM_STATE, EMAIL_CODE_INPUT);
                emailChallenge(context);
            } else {
                logger.info(String.format("resend email request for %s was failed due to %s", existingEmail, errorMessageKey));
                Response challenge = context.form()
                        .setError(errorMessageKey)
//                        .setAttribute(CUSTOM_STYLES_ATTRIBUTE, INTL_TEL_INPUT_MIN_CSS)
                        .createForm(Constants.FROM_RESEND_EMAIL);
                context.challenge(challenge);
            }
        }
    }

    private void showResendForm(EmailContext context) {
        Response challenge = context.form()
                .setInfo(Constants.MESSAGE_EMAIL_NO_PROMPT)
//                .setAttribute(CUSTOM_STYLES_ATTRIBUTE, INTL_TEL_INPUT_MIN_CSS)
                .createForm(Constants.FROM_RESEND_EMAIL);
        context.challenge(challenge);

        context.getAuthenticationSession().setAuthNote(EMAIL_FORM_STATE, EMAIL_INPUT);
    }

    private boolean isResendEmailReload(EmailContext context) {
        return EMAIL_INPUT.equals(context.getAuthenticationSession().getAuthNote(EMAIL_FORM_STATE));
    }

    private String getEmailMessageBody(Locale locale, String emailMessageFormat, String code) {
        Object[] args = new Object[1];
        args[0] = code;
        return new MessageFormat(emailMessageFormat, locale).format(args, new StringBuffer(), new FieldPosition(0)).toString();
    }
}
