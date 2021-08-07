package com.project.singleusersessionspring.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name="email_login_code")
public class EmailLoginCodeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="email_login_code_id")
    Integer emailLoginCodeId;

    @NonNull
    @Column(name="email", unique = true)
    String email;

    @NonNull
    @Column(name="new_login_code")
    int newLoginCode;

    @NonNull
    @Column(name="login_code_send_date")
    Date loginCodeSendDate;

    public Integer getEmailLoginCodeId() {
        return emailLoginCodeId;
    }

    public void setEmailLoginCodeId(Integer emailLoginCodeId) {
        this.emailLoginCodeId = emailLoginCodeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Date getLoginCodeSendDate() {
        return loginCodeSendDate;
    }

    public void setLoginCodeSendDate(Date loginCodeSendDate) {
        this.loginCodeSendDate = loginCodeSendDate;
    }

    public int getNewLoginCode() {
        return newLoginCode;
    }

    public void setNewLoginCode(int newLoginCode) {
        this.newLoginCode = newLoginCode;
    }

    
}
