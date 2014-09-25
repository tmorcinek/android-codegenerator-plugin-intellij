package com.morcinek.android.codegenerator.plugin.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class ProjectHelper {

    public VirtualFile createFileWithGeneratedCode(Project project, String fileName, String folderPath, String code) throws IOException {
        VirtualFile folder = createFolderIfNotExist(project, folderPath);
        VirtualFile createdFile = folder.createChildData(project, fileName);
        createdFile.setBinaryContent(code.getBytes());
        return createdFile;
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
