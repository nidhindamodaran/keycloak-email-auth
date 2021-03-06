package com.nidhin.keycloak.email;

public class Constants {

    // User attributes
    public static final String ATTR_EMAIL = "email";


    // Answer field names
    public static final String ANSW_EMAIL_CODE = "email_code";
    public static final String ANSW_EMAIL = "email";
    public static final String ANSW_RESEND_EMAIL_CODE = "resend_email_code";
    public static final String ANSW_CANCEL_RESEND_EMAIL_CODE = "resend_email_cancel";

    // Froms
    public static final String FROM_RESEND_EMAIL = "resend-email.ftl";
    public static final String FORM_EMAIL_VALIDATION = "email-code.ftl";

    // Messages
    public static final String MESSAGE_EMAIL_NO_VALID = "email.no.valid";
    public static final String MESSAGE_EMAIL_CODE_NO_VALID = "email_code.no.valid";
    public static final String MESSAGE_EMAIL_NOT_EQUAL = "email.not.equal";
    public static final String MESSAGE_EMAIL_TEXT = "email_text_format";
    public static final String MESSAGE_EMAIL_CODE_PROMPT = "email_code.prompt";
    public static final String MESSAGE_EMAIL_NO_PROMPT = "email.prompt";

    private Constants() {
    }
}
