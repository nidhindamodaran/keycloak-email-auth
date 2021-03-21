package com.nidhin.keycloak.email.representation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nidhin.keycloak.email.jpa.VerificationCode;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerificationCodeRepresentation {

    private String id;
    private String userId;
    private String code;
    private String kind;
    private Date expiresAt;
    private Date createdAt;

    public VerificationCodeRepresentation() {
    }

    public VerificationCodeRepresentation(VerificationCode veriCode) {
        id = veriCode.getId();
        userId = veriCode.getUserId();
        code = veriCode.getCode();
        kind = veriCode.getKind();
        expiresAt = veriCode.getExpiresAt();
        createdAt = veriCode.getCreatedAt();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
