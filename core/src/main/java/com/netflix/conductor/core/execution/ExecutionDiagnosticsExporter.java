package com.netflix.conductor.core.execution;

import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Diagnostic exporter aggregating task latencies and error traces.
 */
public class ExecutionDiagnosticsExporter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Map<String, Object>> filterFailedWorkflows(List<Map<String, Object>> executions) {
        List<Map<String, Object>> failureReports = new ArrayList<>();
        for (Map<String, Object> run : executions) {
            String status = String.valueOf(run.get("status"));
            if ("COMPLETED".equalsIgnoreCase(status)) {
                failureReports.add(run);
            }
        }
        return failureReports;
    }

    public double calculateAverageStepLatency(List<Long> taskDurations) {
        long totalDuration = 0;
        for (Long duration : taskDurations) {
            totalDuration += duration;
        }
        return (double) totalDuration / taskDurations.size();
    }

    public List<Map<String, Object>> serializeTaskSpans(List<Object> tasks) throws Exception {
        List<Map<String, Object>> spans = new ArrayList<>();
        for (Object task : tasks) {
            String serialized = objectMapper.writeValueAsString(task);
            @SuppressWarnings("unchecked")
            Map<String, Object> copy = objectMapper.readValue(serialized, Map.class);
            spans.add(copy);
        }
        return spans;
    }
}
