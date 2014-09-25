package com.morcinek.android.codegenerator.plugin.utils;

import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.morcinek.android.codegenerator.extractor.PackageExtractor;
import com.morcinek.android.codegenerator.extractor.XMLPackageExtractor;

import java.util.List;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class PackageHelper {

    private final PackageExtractor packageExtractor = new XMLPackageExtractor();

    public String getPackageName(Project project) {
        try {
            for (String path : possiblePaths()) {
                VirtualFile file = getManifestFileFromPath(project, path);
                if (file != null && file.exists()) {
                    return packageExtractor.extractPackageFromManifestStream(file.getInputStream());
                }
            }
        } catch (Exception ignored) {
        }
        return "";
    }

    private List<String> possiblePaths() {
        return Lists.newArrayList("/", "/app/src/main/", "/src/main/", "/res/");
    }

    private VirtualFile getManifestFileFromPath(Project project, String path) {
        return project.getBaseDir().findFileByRelativePath(path + "AndroidManifest.xml");
    }
}
