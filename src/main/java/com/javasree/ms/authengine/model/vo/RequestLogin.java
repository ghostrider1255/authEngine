package com.javasree.ms.authengine.model.vo;

public class RequestLogin {
    private String email;
    private String password;

    public RequestLogin(){}
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
