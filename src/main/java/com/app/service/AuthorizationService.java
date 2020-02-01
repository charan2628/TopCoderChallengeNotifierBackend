package com.app.service;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.app.model.Login;

/**
 * Service class to verify login.
 *
 * @author charan2628
 *
 */
@Service
public class AuthorizationService {

    private Environment environment;

    public AuthorizationService(@Autowired Environment environment) {
        this.environment = environment;
    }

    /**
     * Verifies login through admin username and
     * password provided through environment properties.
     *
     * <p>Expects following keys in environment
     * ADMIN_USERNAME, ADMIN_PASSWORD
     * Note: ADMIN_PASSWORD should be Base64(without padding) encoding
     * of SHA-256 of password.
     *
     * @param login
     * @return
     * @throws Exception
     */
    public boolean authenticate(Login login) throws Exception {
        String adminUsername = this.environment
                .getProperty("ADMIN_USERNAME");
        String adminPassword = this.environment
                .getProperty("ADMIN_PASSWORD");

        char[] loginPassword = login.getPassword();
        CharBuffer charBuffer = CharBuffer.wrap(loginPassword);
        byte[] pass = Charset.forName("UTF-8")
                .encode(charBuffer).array();

        pass = Base64.getUrlEncoder().encode(pass);

        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        sha256.update(pass);
        pass = sha256.digest();

        pass = Base64.getUrlEncoder().withoutPadding().encode(pass);

        if (login.getUsername().equals(adminUsername)
                && new String(pass).equals(adminPassword)) {
            return true;
        }
        return false;
    }
}
