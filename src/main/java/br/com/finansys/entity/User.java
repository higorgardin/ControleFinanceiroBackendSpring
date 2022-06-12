package br.com.finansys.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User Entity
 *
 * @author Higor Gardin
 * @version 1.0
 * @since 11/06/2022
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "cd_user", nullable = false)
    private String cdUser;

    @Column(name = "ds_name", nullable = false, length = 200)
    private String dsName;

    @Column(name = "ds_email", nullable = false, length = 120, unique = true)
    private String dsEmail;

    @Column(name = "ds_encryptedpassword", nullable = false)
    private String dsEncryptedPassword;

    @Column(name = "st_emailverification", nullable = false)
    private Boolean stEmailVerification = false;

    @Column(name = "ds_emailverificationtoken", nullable = false)
    private String emailVerificationToken;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCdUser() {
        return cdUser;
    }

    public void setCdUser(String cdUser) {
        this.cdUser = cdUser;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getDsEmail() {
        return dsEmail;
    }

    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    public String getDsEncryptedPassword() {
        return dsEncryptedPassword;
    }

    public void setDsEncryptedPassword(String dsEncryptedPassword) {
        this.dsEncryptedPassword = dsEncryptedPassword;
    }

    public Boolean getStEmailVerification() {
        return stEmailVerification;
    }

    public void setStEmailVerification(Boolean stEmailVerification) {
        this.stEmailVerification = stEmailVerification;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }
}
