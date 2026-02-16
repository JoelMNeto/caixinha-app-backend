package com.household.finance.common.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    public static RSAPrivateKey getPrivateKey() throws Exception {

        InputStream is = new ClassPathResource("keys/private.pem").getInputStream();
        String key = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        key = key.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(key);

        KeyFactory factory = KeyFactory.getInstance("RSA");

        return (RSAPrivateKey) factory.generatePrivate(
                new PKCS8EncodedKeySpec(decoded)
        );
    }

    public static RSAPublicKey getPublicKey() throws Exception {

        InputStream is = new ClassPathResource("keys/public.pem").getInputStream();
        String key = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        key = key.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(key);

        KeyFactory factory = KeyFactory.getInstance("RSA");

        return (RSAPublicKey) factory.generatePublic(
                new X509EncodedKeySpec(decoded)
        );
    }
}
