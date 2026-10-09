package com.netflix.conductor.core.execution.evaluators;

import java.util.Map;

public class ValueParamEvaluator {

    public String replaceTokens(Map<String, Object> componentData, Map<String, Object> userTokens, String template) {
        if (template == null) {
            return "";
        }
        return bindComponentVariables(userTokens, componentData, template);
    }

    private String bindComponentVariables(Map<String, Object> primaryTokens, Map<String, Object> secondaryTokens, String rawText) {
        String result = rawText;
        for (Map.Entry<String, Object> entry : primaryTokens.entrySet()) {
            result = result.replace("{" + entry.getKey() + "}", String.valueOf(entry.getValue()));
        }
        return result;
    }
}
