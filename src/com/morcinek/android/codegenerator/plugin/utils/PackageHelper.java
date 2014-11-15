package com.morcinek.android.codegenerator.plugin.utils;

import com.google.common.collect.Lists;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.morcinek.android.codegenerator.extractor.PackageExtractor;
import com.morcinek.android.codegenerator.extractor.XMLPackageExtractor;

import java.util.List;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
//FIXME needs to search for AndroidManifest under source roots
public class PackageHelper {

    private final PackageExtractor packageExtractor = new XMLPackageExtractor();

    private final ProjectHelper projectHelper = new ProjectHelper();

    public String getPackageName(Project project, AnActionEvent event) {
        try {
            for (String path : possiblePaths(project, event)) {
                VirtualFile file = getManifestFileFromPath(project, path);
                if (file != null && file.exists()) {
                    return packageExtractor.extractPackageFromManifestStream(file.getInputStream());
                }
            }
        } catch (Exception ignored) {
        }
        return "";
    }

    private List<String> possiblePaths(Project project, AnActionEvent event) {
        return Lists.asList("", "app/", projectHelper.getSourceRootPathList(project, event).toArray(new String[0]));
    }

    private VirtualFile getManifestFileFromPath(Project project, String path) {
        return project.getBaseDir().findFileByRelativePath(path).findChild("AndroidManifest.xml");
    }
}
