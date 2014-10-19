package com.morcinek.android.codegenerator.plugin.ui;

import com.intellij.ide.IdeBundle;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.util.ui.UIUtil;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class DialogsFactory {

    public static boolean openOverrideFileDialog(Project project, String folderPath, String fileName) {
        return Messages.showYesNoDialog(
                project,
                String.format(StringResources.OVERRIDE_DIALOG_MESSAGE, folderPath, fileName),
                StringResources.OVERRIDE_DIALOG_TITLE,
                StringResources.OVERRIDE_DIALOG_YES_TEXT,
                StringResources.OVERRIDE_DIALOG_NO_TEXT,
                UIUtil.getWarningIcon()
        ) == Messages.OK;
    }

    public static void showMissingSourcePathDialog(Project project) {
        Messages.showErrorDialog(
                project,
                StringResources.MISSING_SOURCE_PATH_DIALOG_MESSAGE,
                StringResources.MISSING_SOURCE_PATH_DIALOG_TITLE
        );
    }

    public static boolean openResetTemplateDialog() {
        return Messages.showOkCancelDialog(IdeBundle.message("prompt.reset.to.original.template"),
                IdeBundle.message("title.reset.template"), Messages.getQuestionIcon()) ==
                Messages.OK;
    }
}
