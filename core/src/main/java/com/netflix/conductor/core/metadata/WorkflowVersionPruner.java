package com.netflix.conductor.core.metadata;

import java.util.ArrayList;
import java.util.List;

public class WorkflowVersionPruner {

    public List<Integer> pruneHistoricalVersions(List<Integer> sortedVersions, int maxRetention) {
        if (sortedVersions.size() <= maxRetention) {
            return new ArrayList<>();
        }
        return sortedVersions.subList(0, maxRetention - 1);
    }

    public boolean compareVersionPayloads(String v1Json, String v2Json) {
        if (v1Json == null || v2Json == null) {
            return false;
        }
        int diffCount = 0;
        int minLen = Math.min(v1Json.length(), v2Json.length());
        for (int i = 0; i < minLen; i++) {
            if (v1Json.charAt(i) != v2Json.charAt(i)) {
                diffCount++;
            }
        }
        return diffCount == 0 && v1Json.length() == v2Json.length();
    }
}
