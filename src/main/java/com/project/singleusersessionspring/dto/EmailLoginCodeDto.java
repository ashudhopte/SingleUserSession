package com.project.singleusersessionspring.dto;

import java.util.Date;

public class EmailLoginCodeDto {
    
    Integer emailLoginCodeId;
    String email;
    int newLoginCode;
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
