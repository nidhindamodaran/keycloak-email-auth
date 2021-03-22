package com.nidhin.keycloak.email.otp;

import com.nidhin.keycloak.email.producer.LogsEmailProducer;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserModel;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.utils.MediaType;
import org.jboss.resteasy.spi.HttpRequest;
import com.nidhin.keycloak.email.utils.UserAttributeUtils;
import org.keycloak.models.RealmModel;
import com.nidhin.keycloak.email.utils.emailUtils;
import com.nidhin.keycloak.email.utils.EmailCodeUtils;
import org.keycloak.theme.Theme;
import com.nidhin.keycloak.email.Constants;
import com.nidhin.keycloak.email.service.EmailServiceException;
import com.nidhin.keycloak.email.representation.VerificationCodeRepresentation;
import com.nidhin.keycloak.email.service.VerificationCodeService;
import org.keycloak.common.ClientConnection;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.resources.admin.permissions.AdminPermissionEvaluator;
import org.keycloak.services.resources.admin.permissions.AdminPermissions;

import org.jboss.logging.Logger;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.text.FieldPosition;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.text.MessageFormat;
import java.util.Locale;

public class GenerateOtpResource {
    private final KeycloakSession session;
    private static final Logger logger = Logger.getLogger(GenerateOtpResource.class);

    @Context
    private HttpHeaders httpHeaders;

    @Context
    private ClientConnection clientConnection;

    private AppAuthManager authManager;
    protected AdminPermissionEvaluator auth;

    public GenerateOtpResource(KeycloakSession session) {
        this.httpHeaders = session.getContext().getRequestHeaders();
        this.clientConnection = session.getContext().getConnection();
        this.authManager = new AppAuthManager();
        this.session = session;
    }


    public void close() {

    }

    public Object getResource() {
        return this;
    }

//    @GET
//    @Path("users")
//    @NoCache
//    @Produces({MediaType.APPLICATION_JSON})
//    @Encoded
//    public List<UserDetails> getUsers() {
//        List<UserModel> userModel = session.users().getUsers(session.getContext().getRealm());
//        return userModel.stream().map(e -> toUserDetail(e)).collect(Collectors.toList());
//    }

//    private UserDetails toUserDetail(UserModel um) {
//        return new UserDetails(um.getUsername(), um.getFirstName(), um.getLastName());
//
//    }

    @POST
    @Path("generate")
    @NoCache
    @Produces({MediaType.APPLICATION_JSON})
    public Response generateOtp(@Context HttpRequest request, final MultivaluedMap<String, String> formData) {
        logger.info("------------------" + formData.getFirst("userName"));
        RealmModel model = session.getContext().getRealm();
        UserModel user = session.users().getUserByUsername(formData.getFirst("userName"), model);
        UserAttributeUtils userAttributeUtils = new UserAttributeUtils();
        String email = userAttributeUtils.getEmail(user);
        EmailCodeUtils emailCodeUtils = new EmailCodeUtils();
        String code = emailCodeUtils.generateCode();

        VerificationCodeRepresentation rep = new VerificationCodeRepresentation();
        rep.setUserId(user.getId());
        rep.setKind(formData.getFirst("kind"));
        rep.setCode(code);


//        VerificationCodeService codeService = new VerificationCodeService(session);

        VerificationCodeRepresentation vc = session.getProvider(VerificationCodeService.class).addVerificationCode(rep);
//        codeService.addVerificationCode(rep);

        logger.info("------------------" + email);
        logger.info("------------------" + code);
        Locale locale = session.getContext().resolveLocale(user);
        String emailMessageFormat;
        try {
            emailMessageFormat = session.theme()
                    .getTheme(Theme.Type.LOGIN)
                    .getMessages(locale)
                    .getProperty(Constants.MESSAGE_EMAIL_TEXT);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        String message = getEmailMessageBody(locale, emailMessageFormat, code);
        LogsEmailProducer emailProducer = new LogsEmailProducer();
        try {
            emailProducer.produce(message, email);
        } catch (Exception ex) {
            throw new EmailServiceException("failed to produce email message for username=" + user.getUsername(), ex);
        }

        return Response.noContent().build();
    }


    private String getEmailMessageBody(Locale locale, String emailMessageFormat, String code) {
        Object[] args = new Object[1];
        args[0] = code;
        return new MessageFormat(emailMessageFormat, locale).format(args, new StringBuffer(), new FieldPosition(0)).toString();
    }


}