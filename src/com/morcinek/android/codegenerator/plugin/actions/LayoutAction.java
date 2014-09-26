package com.morcinek.android.codegenerator.plugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.morcinek.android.codegenerator.CodeGenerator;
import com.morcinek.android.codegenerator.plugin.actions.visibility.ActionVisibilityHelper;
import com.morcinek.android.codegenerator.plugin.error.ErrorHandler;
import com.morcinek.android.codegenerator.plugin.ui.CodeDialogBuilder;
import com.morcinek.android.codegenerator.plugin.ui.StringResources;
import com.morcinek.android.codegenerator.plugin.utils.ClipboardHelper;
import com.morcinek.android.codegenerator.plugin.utils.PackageHelper;
import com.morcinek.android.codegenerator.plugin.utils.PathHelper;
import com.morcinek.android.codegenerator.plugin.utils.ProjectHelper;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
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

    @Override
    public void actionPerformed(AnActionEvent event) {
        final Project project = event.getData(PlatformDataKeys.PROJECT);
        final VirtualFile selectedFile = event.getData(PlatformDataKeys.VIRTUAL_FILE);
        try {
            String generatedCode = getGeneratedCode(selectedFile);
            final CodeDialogBuilder codeDialogBuilder = new CodeDialogBuilder(project,
                    String.format(StringResources.TITLE_FORMAT_TEXT, selectedFile.getName()), generatedCode);
            codeDialogBuilder.addTextSection(StringResources.SOURCE_PATH_LABEL, "src");
            codeDialogBuilder.addTextSection(StringResources.PACKAGE_LABEL, packageHelper.getPackageName(project));
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
            codeDialogBuilder.showDialog();
        } catch (Exception exception) {
            errorHandler.handleError(project, exception);
        }
    }

    protected abstract String getResourceName();

    protected abstract CodeGenerator getCodeGenerator();

    private String getFolderPath(CodeDialogBuilder codeDialogBuilder) {
        String sourcePath = codeDialogBuilder.getValueForLabel(StringResources.SOURCE_PATH_LABEL);
        String packageName = codeDialogBuilder.getValueForLabel(StringResources.PACKAGE_LABEL);
        return pathHelper.getFolderPath(sourcePath, packageName);
    }

    private String getFinalCode(CodeDialogBuilder codeDialogBuilder) {
        String packageName = codeDialogBuilder.getValueForLabel(StringResources.PACKAGE_LABEL);
        String modifiedCode = codeDialogBuilder.getModifiedCode();
        return pathHelper.getMergedCodeWithPackage(packageName, modifiedCode);
    }

    private String getGeneratedCode(VirtualFile file) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        return getCodeGenerator().produceCode(file.getInputStream(), file.getName());
    }

    @Override
    public void update(AnActionEvent event) {
        event.getPresentation().setVisible(actionVisibilityHelper.isVisible(event.getDataContext()));
    }
}
