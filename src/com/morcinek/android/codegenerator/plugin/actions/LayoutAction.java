package com.morcinek.android.codegenerator.plugin.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class LayoutAction extends AnAction {

    // If you register the action from Java code, this constructor is used to set the menu item name
    // (optionally, you can specify the menu description and an icon to display next to the menu item).
    // You can omit this constructor when registering the action in the plugin.xml file.
    public LayoutAction() {
        // Set the menu item name.
        super("Text _Boxes");
        // Set the menu item name, description and icon.
        // super("Text _Boxes","Item description",IconLoader.getIcon("/Mypackage/icon.png"));
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        String txt = Messages.showInputDialog(project, "What is your name?", "Input your name", Messages.getQuestionIcon());
        Messages.showMessageDialog(project, "Hello, " + txt + "!\n I am glad to see you.", "Information", Messages.getInformationIcon());
    }

    @Override
    public void update(AnActionEvent e) {
        e.getPresentation().setVisible(isVisible(e.getDataContext()));
    }

    private boolean isVisible(DataContext dataContext) {
        VirtualFile data = CommonDataKeys.VIRTUAL_FILE.getData(dataContext);
        return data != null && !data.isDirectory() && isInLayoutFolder(data);
    }

    private boolean isInLayoutFolder(VirtualFile data) {
        return data.getParent().getPath().endsWith("layout");
    }
}
