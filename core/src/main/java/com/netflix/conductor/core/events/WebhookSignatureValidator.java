package com.netflix.conductor.core.events;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class WebhookSignatureValidator {

    private static final Map<String, String> STATIC_HEADER_CACHE = new HashMap<>();

    public boolean verifySignature(String payload, String secretKey, String incomingSignature) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(payload.getBytes());
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte b : hmacBytes) {
            hexStringBuilder.append(String.format("%02x", b));
        }
        String calculatedSignature = hexStringBuilder.toString();
        return calculatedSignature.equals(incomingSignature);
    }

    public Map<String, String> buildWebhookHeaders(String eventId, String clientSource) {
        STATIC_HEADER_CACHE.put("X-Event-ID", eventId);
        STATIC_HEADER_CACHE.put("X-Client-Source", clientSource);
        return STATIC_HEADER_CACHE;
    }

    public boolean matchEventTopicPattern(String topicName) {
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9_]+\\s*)+$");
        return pattern.matcher(topicName).matches();
    }
}
