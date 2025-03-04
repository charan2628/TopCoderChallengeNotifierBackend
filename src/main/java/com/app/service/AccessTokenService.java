package com.app.service;

import java.io.FileInputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.app.model.jwt.Claims;
import com.app.model.jwt.Header;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service class to create JWT access tokens.
 *
 * @author charan2628
 *
 */
@Service
public class AccessTokenService {

    private KeyPair keyPair;
    private String expMin;

    /**
     * Initializes keypair with the keystore file
     * provided through properties.
     *
     * <p> Expects following properties in environment
     * TOKEN_EXP_TIME, JKS_KEYSTORE_ALIAS, JKS_KEYSTORE_FILE, JKS_KEYSTORE_PASSWORD
     *
     * @param environment {@link org.springframework.core.env.Environment}
     * @throws Exception
     */
    public AccessTokenService(
            @Autowired Environment environment) throws Exception {
        this.expMin = environment
                .getProperty("TOKEN_EXP_TIME");
        String alias = environment
                .getProperty("JKS_KEYSTORE_ALIAS");
        FileInputStream ksFile = new FileInputStream(environment.
                getProperty("JKS_KEYSTORE_FILE"));
        char[] ksPassword = environment
                .getProperty("JKS_KEYSTORE_PASSWORD")
                .toCharArray();

        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(ksFile, ksPassword);
        ksFile.close();

        PrivateKey privateKey = (PrivateKey) keyStore
                .getKey(alias, ksPassword);
        PublicKey publicKey = keyStore
                .getCertificate(alias)
                .getPublicKey();
        this.keyPair = new KeyPair(publicKey, privateKey);
    }

    /**
     * Creates JWT token using the private key from
     * the JKS keystore file provided.
     *
     * <p>Uses SHA256withECDSA to sign the JWT
     *
     * Implemented as specified in RFC 7519, RFC 7518, RFC 7515
     *
     * @return JWT token
     * @throws Exception
     */
    public String createToken(Claims claims) throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime = dateTime.plusMinutes(Integer.parseInt(this.expMin));

        Header header = new Header("JWT", "ES256");

        ObjectMapper mapper = new ObjectMapper();

        String jwtHeader = mapper.writeValueAsString(header);
        jwtHeader = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(jwtHeader
                        .getBytes("UTF-8"));

        String jwtPayload = mapper.writeValueAsString(claims);
        jwtPayload = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(jwtPayload
                        .getBytes("UTF-8"));

        String jwtValue = String.format("%s.%s",
                jwtHeader, jwtPayload);

        String sign = new String(Base64.
                getUrlEncoder().
                withoutPadding().
                encode(sign(jwtValue)));

        return String.format("%s.%s.%s",
                jwtHeader, jwtPayload, sign);
    }
    
    public Claims getClaims(String token) throws Exception {
        String payload = token.split("\\.")[1];
        payload = new String(Base64.getUrlDecoder().decode(payload));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(payload, Claims.class);
    }

    /**
     * Verifies the access token by verifying the sign and
     * expiration time.
     *
     * @param token
     * @return is valid token or not
     * @throws Exception
     */
    public boolean verifyToken(String token) throws Exception {
        String[] data = token.split("\\.");
        if(data.length != 3) {
            return false;
        }

        Signature signature = Signature.getInstance("SHA256withECDSA");
        signature.initVerify(this.keyPair.getPublic());

        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        signature.update(
                sha256.digest(
                        String.format("%s.%s", data[0], data[1])
                        .getBytes()));

        byte[] sign = Base64
                .getUrlDecoder()
                .decode(data[2].getBytes());
        if (!signature.verify(sign)) {
            return false;
        }

        String jwtPayload  = new String(
                Base64.getUrlDecoder()
                .decode(data[1].getBytes("UTF-8")));
        ObjectMapper mapper = new ObjectMapper();
        Claims claims = mapper.readValue(jwtPayload, Claims.class);

        LocalDateTime now = LocalDateTime.now();
        ZoneOffset zoneOffset = ZoneId.systemDefault()
                .getRules()
                .getOffset(Instant.now());
        LocalDateTime exp = LocalDateTime.ofEpochSecond(
                claims.getExpirationTime(),
                0,
                zoneOffset);
        return exp.isAfter(now) ? true : false;
    }

    /**
     * Helper method to sign the JWT payload
     *
     * <p>Implemented as specified in RFC 7519, RFC 7518, RFC 7515
     *
     * @param payload JWT payload
     * @return JWT signature
     * @throws Exception
     */
    private byte[] sign(String payload) throws Exception {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        sha256.update(payload.getBytes("ASCII"));
        byte[] input = sha256.digest();

        Signature signature = Signature.getInstance("SHA256withECDSA");
        signature.initSign(this.keyPair.getPrivate());
        signature.update(input);
        return signature.sign();
    }
}
