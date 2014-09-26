package com.morcinek.android.codegenerator.plugin.actions.visibility;

import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class ActionVisibilityHelper {

    private String folder;

    private String extension;

    public ActionVisibilityHelper(String folder, String extension) {
        this.folder = folder;
        this.extension = extension;
    }

    public boolean isVisible(DataContext dataContext) {
        VirtualFile[] files = CommonDataKeys.VIRTUAL_FILE_ARRAY.getData(dataContext);
        if (files != null && files.length == 1) {
            VirtualFile file = files[0];
            return !file.isDirectory() && hasCorrectExtension(file) && hasCorrectFolder(file);
        } else {
            return false;
        }
    }

    private boolean hasCorrectExtension(VirtualFile data) {
        return data.getExtension() != null && data.getExtension().equals(extension);
    }

    private boolean hasCorrectFolder(VirtualFile data) {
        return data.getParent().getName().equals(folder);
    }
}
