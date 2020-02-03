package com.app.model;

public class Login {

    private String email;
    private String password;

    public Login() {
        super();
    }

    public Login(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Login [email=" + this.email + ", password=" + this.password + "]";
    }

}
