package com.app.model;

import java.util.Arrays;

public class Login {

    private String email;
    private char[] password;

    public Login() {
        super();
    }

    public Login(String email, char[] password) {
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

    public char[] getPassword() {
        return this.password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Login [email=" + this.email + ", password=" + Arrays.toString(this.password) + "]";
    }

}
