package com.morcinek.android.codegenerator.plugin.utils;

import com.google.common.collect.Lists;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.morcinek.android.codegenerator.extractor.PackageExtractor;
import com.morcinek.android.codegenerator.extractor.XMLPackageExtractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class PackageHelper {

    private final PackageExtractor packageExtractor = new XMLPackageExtractor();

    private final ProjectHelper projectHelper = new ProjectHelper();

    public String getPackageName(Project project, AnActionEvent event) {
        try {
            for (String path : possibleManifestPaths()) {
                VirtualFile file = getManifestFileFromPath(project, path);
                if (file != null && file.exists()) {
                    return packageExtractor.extractPackageFromManifestStream(file.getInputStream());
                }
            }
            for (String path : sourceRootPaths(project, event)) {
                VirtualFile file = getManifestFileFromPath(project, path);
                if (file != null && file.exists()) {
                    return packageExtractor.extractPackageFromManifestStream(file.getInputStream());
                }
            }
        } catch (Exception ignored) {
        }
        return "";
    }

    private ArrayList<String> possibleManifestPaths() {
        return Lists.newArrayList("", "app/", "app/src/main/", "src/main/", "res/");
    }

    private List<String> sourceRootPaths(Project project, AnActionEvent event) {
        return projectHelper.getSourceRootPathList(project, event);
    }

    private VirtualFile getManifestFileFromPath(Project project, String path) {
        VirtualFile folder = project.getBaseDir().findFileByRelativePath(path);
        if (folder != null) {
            return folder.findChild("AndroidManifest.xml");
        }
        return null;
    }
}
