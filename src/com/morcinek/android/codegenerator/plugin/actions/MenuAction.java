package com.morcinek.android.codegenerator.plugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.morcinek.android.codegenerator.codegeneration.providers.factories.MenuResourceProvidersFactory;
import com.morcinek.android.codegenerator.plugin.actions.visibility.ActionVisibilityHelper;
import com.morcinek.android.codegenerator.plugin.codegenerator.CodeGeneratorController;
import com.morcinek.android.codegenerator.plugin.error.ErrorHandler;
import com.morcinek.android.codegenerator.plugin.ui.CodeDialogBuilder;
import com.morcinek.android.codegenerator.plugin.ui.StringResources;
import com.morcinek.android.codegenerator.plugin.utils.ClipboardHelper;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class MenuAction extends AnAction {

    private final ActionVisibilityHelper actionVisibilityHelper = new ActionVisibilityHelper("menu", "xml");

    private final ErrorHandler errorHandler = new ErrorHandler();

    @Override
    public void actionPerformed(AnActionEvent event) {
        final Project project = event.getData(PlatformDataKeys.PROJECT);
        final VirtualFile selectedFile = event.getData(PlatformDataKeys.VIRTUAL_FILE);
        try {
            CodeGeneratorController codeGeneratorController = new CodeGeneratorController("Menu_template", new MenuResourceProvidersFactory());
            String generatedCode = codeGeneratorController.generateCode(project, selectedFile, event.getData(PlatformDataKeys.EDITOR));
            final CodeDialogBuilder codeDialogBuilder = new CodeDialogBuilder(project,
                    String.format(StringResources.TITLE_FORMAT_TEXT, selectedFile.getName()), generatedCode);
            codeDialogBuilder.addAction(StringResources.COPY_ACTION_LABEL, new Runnable() {
                @Override
                public void run() {
                    ClipboardHelper.copy(codeDialogBuilder.getModifiedCode());
                    codeDialogBuilder.closeDialog();
                }
            });
            codeDialogBuilder.showDialog();
        } catch (Exception exception) {
            errorHandler.handleError(project, exception);
        }
    }

    @Override
    public void update(AnActionEvent event) {
        event.getPresentation().setVisible(actionVisibilityHelper.isVisible(event.getDataContext()));
    }
}
