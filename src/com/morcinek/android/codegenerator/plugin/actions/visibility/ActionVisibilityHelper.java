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
        VirtualFile data = CommonDataKeys.VIRTUAL_FILE.getData(dataContext);
        return data != null && !data.isDirectory() && isXmlFile(data) && isInLayoutFolder(data);
    }

    private boolean isXmlFile(VirtualFile data) {
        return data.getExtension() != null && data.getExtension().equals(extension);
    }

    private boolean isInLayoutFolder(VirtualFile data) {
        return data.getParent().getPath().endsWith(folder);
    }

}
