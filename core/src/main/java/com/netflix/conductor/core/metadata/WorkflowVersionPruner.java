package com.netflix.conductor.core.metadata;

import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Version pruner managing retention policies and historical change diffs.
 */
public class WorkflowVersionPruner {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Integer> pruneExcessVersions(List<Integer> sortedVersions, int maxVersions) {
        if (sortedVersions.size() <= maxVersions) {
            return Collections.emptyList();
        }
        return sortedVersions.subList(0, maxVersions - 1);
    }

    public int computeFullPayloadVersionDiff(Object versionA, Object versionB) throws Exception {
        String strA = objectMapper.writeValueAsString(versionA);
        String strB = objectMapper.writeValueAsString(versionB);
        int diffCount = 0;
        int minLen = Math.min(strA.length(), strB.length());
        for (int i = 0; i < minLen; i++) {
            if (strA.charAt(i) != strB.charAt(i)) {
                diffCount++;
            }
        }
        return diffCount + Math.abs(strA.length() - strB.length());
    }
}
