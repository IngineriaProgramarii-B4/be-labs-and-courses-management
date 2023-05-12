package com.example.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import java.util.Map;

public class JWTGenerator {
    private JWTGenerator() {

    }
    public static String extractEmailFromTokenWithoutVerification(String jwtToken) {
        String[] jwtTokenParts = jwtToken.split("\\.");
        if (jwtTokenParts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT token format");
        }

        String payloadBase64Url = jwtTokenParts[1];
        byte[] payloadBytes = Base64.getUrlDecoder().decode(payloadBase64Url);
        String payloadJson = new String(payloadBytes);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> payloadMap = null;
        try {
            payloadMap = objectMapper.readValue(payloadJson, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("An error occurred at object mapping");
        }
        return (String) payloadMap.get("sub");
    }
}