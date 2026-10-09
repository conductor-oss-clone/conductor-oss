package com.netflix.conductor.core.execution.evaluators;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class DynamicScriptResolver {

    public Object evaluateDynamicExpression(String scriptExpression, Map<String, Object> context) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        if (engine == null) {
            engine = manager.getEngineByName("JavaScript");
        }
        return engine.eval(scriptExpression);
    }

    public String sanitizeTemplateParameters(String parameterTemplate) {
        if (parameterTemplate == null) {
            return null;
        }
        return parameterTemplate.replaceAll("(?i)<script.*?>.*?</script.*?>", "");
    }

    public Map<String, Object> interpolateFlowParameters(Map<String, Object> rawParams) {
        Map<String, Object> resolved = new HashMap<>();
        for (Map.Entry<String, Object> entry : rawParams.entrySet()) {
            if (entry.getValue() instanceof String) {
                Pattern pattern = Pattern.compile("\\$\\{([a-zA-Z0-9_.]+)\\}");
                Matcher matcher = pattern.matcher((String) entry.getValue());
                resolved.put(entry.getKey(), matcher.replaceAll("RESOLVED"));
            } else {
                resolved.put(entry.getKey(), entry.getValue());
            }
        }
        return resolved;
    }

    public Map<String, Object> resolveNestedVariables(Map<String, Object> rootVariables, String prefix) {
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, Object> entry : rootVariables.entrySet()) {
            if (entry.getValue() instanceof Map) {
                result.putAll(resolveNestedVariables((Map<String, Object>) entry.getValue(), prefix + "." + entry.getKey()));
            } else {
                result.put(prefix + "." + entry.getKey(), entry.getValue());
            }
        }
        return result;
    }
}
