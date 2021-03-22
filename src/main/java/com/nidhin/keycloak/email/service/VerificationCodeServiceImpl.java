package com.nidhin.keycloak.email.service;

import org.keycloak.connections.jpa.JpaConnectionProvider;
import com.nidhin.keycloak.email.representation.VerificationCodeRepresentation;
import com.nidhin.keycloak.email.jpa.VerificationCode;
import com.nidhin.keycloak.email.service.VerificationCodeService;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.utils.KeycloakModelUtils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.*;

public class VerificationCodeServiceImpl implements VerificationCodeService{

    private final KeycloakSession session;

    public VerificationCodeServiceImpl(KeycloakSession session) {
        this.session = session;
        if (getRealm() == null) {
            throw new IllegalStateException("The service cannot accept a session without a realm in its context.");
        }
    }

    private EntityManager getEntityManager() {
        return session.getProvider(JpaConnectionProvider.class).getEntityManager();
    }

    protected RealmModel getRealm() {
        return session.getContext().getRealm();
    }

    public List<VerificationCodeRepresentation> listVerificationCodes() {
        List<VerificationCode> verificationCodesEntities = getEntityManager().createNamedQuery("VerificationCode.findByRealm", VerificationCode.class)
                .setParameter("realmId", getRealm().getId())
                .getResultList();
        List<VerificationCodeRepresentation> result = new LinkedList<>();
        for (VerificationCode entity : verificationCodesEntities) {
            result.add(new VerificationCodeRepresentation(entity));
        }
        return result;
    }

    public VerificationCodeRepresentation addVerificationCode(VerificationCodeRepresentation veriCode) {
        VerificationCode entity = new VerificationCode();
        String id = veriCode.getId() == null ? KeycloakModelUtils.generateId() : veriCode.getId();
        entity.setId(id);
        entity.setUserId(veriCode.getUserId());
        entity.setCode(veriCode.getCode());
        entity.setKind(Optional.ofNullable(veriCode.getKind()).orElse(""));
        entity.setRealmId(getRealm().getId());
        Instant now = Instant.now();
        entity.setCreatedAt(Date.from(now));
        entity.setExpiresAt(Date.from(now.plusSeconds(5 * 60)));
        getEntityManager().persist(entity);

        return new VerificationCodeRepresentation(entity);
    }

    public void close() {
    }
}
