package com.morcinek.android.codegenerator.plugin.utils;

import com.google.common.collect.Lists;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;
import java.util.List;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class ProjectHelper {

    public VirtualFile createFileAndOpenInEditor(Project project, String fileName, String folderPath, String code) throws IOException {
        VirtualFile folder = createFolderIfNotExist(project, folderPath);
        VirtualFile createdFile = folder.createChildData(project, fileName);
        createdFile.setBinaryContent(code.getBytes());
        openFileInEditor(project, createdFile);
        return createdFile;
    }

    private void openFileInEditor(Project project, VirtualFile fileWithGeneratedCode) {
        FileEditorManager.getInstance(project).openFile(fileWithGeneratedCode, true);
    }

    private VirtualFile createFolderIfNotExist(Project project, String folder) throws IOException {
        VirtualFile directory = project.getBaseDir();
        String[] folders = folder.split("/");
        for (String childFolder : folders) {
            VirtualFile childDirectory = directory.findChild(childFolder);
            if (childDirectory != null && childDirectory.isDirectory()) {
                directory = childDirectory;
            } else {
                directory = directory.createChildDirectory(project, childFolder);
            }
        }
        return directory;
    }

    public List<String> getSourceRootPathList(Project project, AnActionEvent event) {
        List<String> sourceRoots = Lists.newArrayList();
        for (VirtualFile virtualFile : getModuleRootManager(event).getSourceRoots(false)) {
            sourceRoots.add(virtualFile.getPath().replace(project.getBasePath(), ""));
        }
        return sourceRoots;
    }

    private ModuleRootManager getModuleRootManager(AnActionEvent event) {
        return ModuleRootManager.getInstance(event.getData(LangDataKeys.MODULE));
    }
}