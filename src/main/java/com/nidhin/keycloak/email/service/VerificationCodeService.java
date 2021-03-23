package com.nidhin.keycloak.email.service;

import com.nidhin.keycloak.email.representation.VerificationCodeRepresentation;
import org.keycloak.provider.Provider;

import java.util.List;

public interface VerificationCodeService extends Provider {
    List<VerificationCodeRepresentation> listVerificationCodes();

    VerificationCodeRepresentation addVerificationCode(VerificationCodeRepresentation veriCode);
}
