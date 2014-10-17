package com.morcinek.android.codegenerator.plugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;
import com.morcinek.android.codegenerator.codegeneration.providers.ResourceProvidersFactory;
import com.morcinek.android.codegenerator.plugin.actions.visibility.ActionVisibilityHelper;
import com.morcinek.android.codegenerator.plugin.codegenerator.CodeGeneratorController;
import com.morcinek.android.codegenerator.plugin.error.ErrorHandler;
import com.morcinek.android.codegenerator.plugin.persistence.Settings;
import com.morcinek.android.codegenerator.plugin.ui.CodeDialogBuilder;
import com.morcinek.android.codegenerator.plugin.ui.StringResources;
import com.morcinek.android.codegenerator.plugin.utils.ClipboardHelper;
import com.morcinek.android.codegenerator.plugin.utils.PackageHelper;
import com.morcinek.android.codegenerator.plugin.utils.PathHelper;
import com.morcinek.android.codegenerator.plugin.utils.ProjectHelper;

import java.io.IOException;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public abstract class LayoutAction extends AnAction {

    private final ActionVisibilityHelper actionVisibilityHelper = new ActionVisibilityHelper("layout", "xml");

    private final ErrorHandler errorHandler = new ErrorHandler();

    private final PackageHelper packageHelper = new PackageHelper();

    private final ProjectHelper projectHelper = new ProjectHelper();

    private final PathHelper pathHelper = new PathHelper();

    private final Settings settings = ServiceManager.getService(Settings.class);

    @Override
    public void actionPerformed(AnActionEvent event) {
        final Project project = event.getData(PlatformDataKeys.PROJECT);
        final VirtualFile selectedFile = event.getData(PlatformDataKeys.VIRTUAL_FILE);
        try {
            CodeGeneratorController codeGeneratorController = new CodeGeneratorController(getTemplateName(), getResourceProvidersFactory());
            String generatedCode = codeGeneratorController.generateCode(project, selectedFile, event.getData(PlatformDataKeys.EDITOR));
            final CodeDialogBuilder codeDialogBuilder = new CodeDialogBuilder(project,
                    String.format(StringResources.TITLE_FORMAT_TEXT, selectedFile.getName()), generatedCode);
            codeDialogBuilder.addSourcePathSection(projectHelper.getSourceRootPathList(project, event), settings.getSourcePath());
            codeDialogBuilder.addPackageSection(packageHelper.getPackageName(project));
            codeDialogBuilder.addAction(StringResources.COPY_ACTION_LABEL, new Runnable() {
                @Override
                public void run() {
                    ClipboardHelper.copy(getFinalCode(codeDialogBuilder));
                }
            });
            codeDialogBuilder.addAction(StringResources.CREATE_ACTION_LABEL, new Runnable() {
                @Override
                public void run() {
                    try {
                        String folderPath = getFolderPath(codeDialogBuilder);
                        String fileName = pathHelper.getFileName(selectedFile.getName(), getResourceName());
                        String finalCode = getFinalCode(codeDialogBuilder);
                        projectHelper.createFileAndOpenInEditor(project, fileName, folderPath, finalCode);
                    } catch (IOException exception) {
                        errorHandler.handleError(project, exception);
                    }
                }
            }, true);
            if (codeDialogBuilder.showDialog() == DialogWrapper.OK_EXIT_CODE) {
                settings.setSourcePath(codeDialogBuilder.getSourcePath());
            }
        } catch (Exception exception) {
            errorHandler.handleError(project, exception);
        }
    }

    protected abstract String getResourceName();

    protected abstract String getTemplateName();

    protected abstract ResourceProvidersFactory getResourceProvidersFactory();

    private String getFolderPath(CodeDialogBuilder codeDialogBuilder) {
        String sourcePath = codeDialogBuilder.getSourcePath();
        String packageName = codeDialogBuilder.getPackage();
        return pathHelper.getFolderPath(sourcePath, packageName);
    }

    private String getFinalCode(CodeDialogBuilder codeDialogBuilder) {
        String packageName = codeDialogBuilder.getPackage();
        String modifiedCode = codeDialogBuilder.getModifiedCode();
        return pathHelper.getMergedCodeWithPackage(packageName, modifiedCode);
    }

    @Override
    public void update(AnActionEvent event) {
        event.getPresentation().setVisible(actionVisibilityHelper.isVisible(event.getDataContext()));
    }
}
