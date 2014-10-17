package com.morcinek.android.codegenerator.plugin.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.util.ui.UIUtil;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class DialogsFactory {

    public static int openOverrideFileDialog(Project project, String folderPath, String fileName) {
        return Messages.showYesNoDialog(
                project,
                String.format(StringResources.OVERRIDE_DIALOG_MESSAGE, folderPath, fileName),
                StringResources.OVERRIDE_DIALOG_TITLE,
                StringResources.OVERRIDE_DIALOG_YES_TEXT,
                StringResources.OVERRIDE_DIALOG_NO_TEXT,
                UIUtil.getWarningIcon()
        );
    }
}
