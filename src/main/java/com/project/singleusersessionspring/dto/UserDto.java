package com.project.singleusersessionspring.dto;

import java.util.Date;

public class UserDto {
    
    Integer userId;
    String email;
    int loginCode;
    Date lastLoginDate;
    Boolean sessionActive;
    String message;
    Boolean emailSent;

    


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Boolean getSessionActive() {
        return sessionActive;
    }

    public void setSessionActive(Boolean sessionActive) {
        this.sessionActive = sessionActive;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getEmailSent() {
        return emailSent;
    }

    public void setEmailSent(Boolean emailSent) {
        this.emailSent = emailSent;
    }

    public int getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(int loginCode) {
        this.loginCode = loginCode;
    }

    
}
