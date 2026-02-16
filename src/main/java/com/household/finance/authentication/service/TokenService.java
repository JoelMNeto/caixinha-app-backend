package com.household.finance.authentication.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.household.finance.user.entity.User;
import com.household.finance.common.utils.RSAUtil;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    public static final String TOKEN_ISSUER = "Caixinha App";
    public static final String OFFSET_ID = "-03:00";

    public String generateToken(User user) {
        try {
            Algorithm algorithm = getAlgorithmToGenerateToken();

            return  JWT.create()
                    .withIssuer(TOKEN_ISSUER)
                    .withSubject(user.getUsername())
                    .withExpiresAt(getExpiration(30))
                    .sign(algorithm);
        } catch (Exception exception){
            throw new RuntimeException("Error generating JWT access token.");
        }
    }

    public String generateRefreshToken(User user) {
        try {
            Algorithm algorithm = getAlgorithmToGenerateToken();

            return  JWT.create()
                    .withIssuer(TOKEN_ISSUER)
                    .withSubject(user.getId().toString())
                    .withExpiresAt(getExpiration(120))
                    .sign(algorithm);
        } catch (Exception exception){
            throw new RuntimeException("Error generating JWT access token.");
        }
    }

    public String verifyToken(String token) {
        DecodedJWT decodedJWT;

        try {
            Algorithm algorithm = getAlgorithmToVerifyToken();

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(TOKEN_ISSUER)
                    .build();

            decodedJWT = verifier.verify(token);

            return decodedJWT.getSubject();
        } catch (Exception exception){
            throw new RuntimeException("Error generating JWT access token.");
        }
    }

    private Algorithm getAlgorithmToVerifyToken() throws Exception {
        RSAPublicKey publicKey = RSAUtil.getPublicKey();

        return Algorithm.RSA256(publicKey, null);
    }

    private Algorithm getAlgorithmToGenerateToken() throws Exception {
        RSAPrivateKey privateKey = RSAUtil.getPrivateKey();
        RSAPublicKey publicKey = RSAUtil.getPublicKey();

        return Algorithm.RSA256(publicKey, privateKey);
    }

    public Instant getExpiration(Integer minutes) {
        return LocalDateTime.now().plusMinutes(minutes).toInstant(ZoneOffset.of(OFFSET_ID));
    }
}
