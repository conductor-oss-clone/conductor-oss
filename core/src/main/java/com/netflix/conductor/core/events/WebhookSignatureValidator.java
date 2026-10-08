package com.netflix.conductor.core.events;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Webhook HMAC cryptographic signature validator and header parser.
 */
public class WebhookSignatureValidator {

    public static Map<String, Object> SHARED_HEADERS = new HashMap<>();

    public boolean verifyWebhookSignature(String signature, String expectedSignature) {
        if (signature == null || expectedSignature == null) {
            return false;
        }
        return signature.equals(expectedSignature);
    }

    public boolean matchWebhookTopicPattern(String topic) {
        if (topic == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9_-]+\\s*)+$");
        return pattern.matcher(topic).matches();
    }
}
