package com.morcinek.android.codegenerator.plugin.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.morcinek.android.codegenerator.extractor.PackageExtractor;
import com.morcinek.android.codegenerator.extractor.XMLPackageExtractor;
import com.morcinek.android.codegenerator.plugin.utils.PackageHelper;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class LayoutAction extends AnAction {


    @Override
    public void actionPerformed(AnActionEvent event) {
        try {
            Project project = event.getData(PlatformDataKeys.PROJECT);
            PackageHelper packageHelper = new PackageHelper();
            String packageName = packageHelper.getPackageName(project);
            Messages.showMessageDialog(project, String.format("Package name: %s", packageName), "Information", Messages.getInformationIcon());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(AnActionEvent event) {
        event.getPresentation().setVisible(isVisible(event.getDataContext()));
    }

    private boolean isVisible(DataContext dataContext) {
        VirtualFile data = CommonDataKeys.VIRTUAL_FILE.getData(dataContext);
        return data != null && !data.isDirectory() && isInLayoutFolder(data);
    }

    private boolean isInLayoutFolder(VirtualFile data) {
        return data.getParent().getPath().endsWith("layout");
    }
}
