package com.morcinek.android.codegenerator.plugin.utils;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;

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
}
