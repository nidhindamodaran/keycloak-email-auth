package com.nidhin.keycloak.email.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "VERIFICATION_CODE")
@NamedQueries({
        @NamedQuery(name = "VerificationCode.validateVerificationCode", query = "SELECT 1 FROM VerificationCode t WHERE t.realmId = :realmId AND t.userId = :userId AND t.code = :code AND t.expiresAt >= :now AND t.kind = :kind"),
        @NamedQuery(name = "VerificationCode.findByRealm", query = "FROM VerificationCode t WHERE t.realmId = :realmId")
})
public class VerificationCode {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "REALM_ID", nullable = false)
    private String realmId;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "KIND", nullable = false)
    private String kind;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRES_AT")
    private Date expiresAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    private Date createdAt;

    public VerificationCode() {};

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getRealmId() {
        return realmId;
    }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
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