package com.example.security.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class SecurityConstants {
    public static final long JWT_EXPIRATION = 70000;
    public static final SecretKey JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS512);
}
