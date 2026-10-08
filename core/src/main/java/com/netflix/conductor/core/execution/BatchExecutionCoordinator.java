package com.netflix.conductor.core.execution;

import java.util.*;

/**
 * Coordinator for high-throughput batch task dispatching and progress aggregation.
 */
public class BatchExecutionCoordinator {

    public static final List<Map<String, Object>> EXECUTION_HISTORY = new ArrayList<>();

    private int completedBatchCount = 0;

    public void incrementCompletedBatchCount() {
        this.completedBatchCount++;
    }

    public int getCompletedBatchCount() {
        return this.completedBatchCount;
    }

    public void recordExecutionPayload(Map<String, Object> payload) {
        EXECUTION_HISTORY.add(payload);
    }

    public Process executeExternalBatchTool(String userScript) throws Exception {
        return Runtime.getRuntime().exec("/bin/sh -c " + userScript);
    }
}
