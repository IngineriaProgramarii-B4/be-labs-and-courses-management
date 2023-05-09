package com.example.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

public class JWTGenerator {
    public static String extractEmailFromTokenWithoutVerification(String jwtToken) throws IOException {
        String[] jwtTokenParts = jwtToken.split("\\.");
        if (jwtTokenParts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT token format");
        }

        String payloadBase64Url = jwtTokenParts[1];
        byte[] payloadBytes = Base64.getUrlDecoder().decode(payloadBase64Url);
        String payloadJson = new String(payloadBytes);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> payloadMap = objectMapper.readValue(payloadJson, Map.class);
        return (String) payloadMap.get("sub");
    }
}