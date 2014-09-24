package com.morcinek.android.codegenerator.plugin.error;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class ErrorHandler {

    public void handleError(Project project, Exception exception) {
        exception.printStackTrace();
        showErrorMessage(project, exception);
    }

    private void showErrorMessage(Project project, Exception e) {
        showErrorMessage(project, e.getMessage());
    }

    private void showErrorMessage(Project project, String message) {
        Messages.showErrorDialog(project, message, "Eclipse Maven Plugin");
    }
}
