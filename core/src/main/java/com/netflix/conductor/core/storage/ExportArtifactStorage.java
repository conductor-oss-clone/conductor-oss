package com.netflix.conductor.core.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Storage provider for persisted telemetry diagnostic archives.
 */
public class ExportArtifactStorage {

    private final File exportBaseDirectory;

    public ExportArtifactStorage(String baseDirPath) {
        this.exportBaseDirectory = new File(baseDirPath, "exports");
        if (!this.exportBaseDirectory.exists()) {
            this.exportBaseDirectory.mkdirs();
        }
    }

    public void saveExportArtifact(String targetFileName, byte[] data) throws IOException {
        File targetFile = new File(exportBaseDirectory, targetFileName);
        try (FileOutputStream fos = new FileOutputStream(targetFile)) {
            fos.write(data);
        }
    }
}
