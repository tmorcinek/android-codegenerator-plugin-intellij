package com.morcinek.android.codegenerator.plugin.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.morcinek.android.codegenerator.CodeGenerator;
import com.morcinek.android.codegenerator.codegeneration.providers.factories.ActivityResourceProvidersFactory;
import com.morcinek.android.codegenerator.plugin.error.ErrorHandler;
import com.morcinek.android.codegenerator.plugin.ui.CodeDialogBuilder;
import com.morcinek.android.codegenerator.plugin.utils.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class LayoutAction extends AnAction {

    public static final String PACKAGE_LABEL = "Package";
    public static final String SOURCE_PATH_LABEL = "Source Path";

    private final ErrorHandler errorHandler = new ErrorHandler();

    private final PackageHelper packageHelper = new PackageHelper();

    private final ProjectHelper projectHelper = new ProjectHelper();

    private final PathHelper pathHelper = new PathHelper();

    @Override
    public void actionPerformed(AnActionEvent event) {
        final Project project = event.getData(PlatformDataKeys.PROJECT);
        final VirtualFile selectedFile = event.getData(PlatformDataKeys.VIRTUAL_FILE);
        try {
            PackageHelper packageHelper = new PackageHelper();
            String produceCode = getGeneratedCode(selectedFile);
            final CodeDialogBuilder codeDialogBuilder = new CodeDialogBuilder(project,
                    String.format("Code generated from: '%s'", selectedFile.getName()), produceCode);
            codeDialogBuilder.addTextSection(SOURCE_PATH_LABEL, "src");
            codeDialogBuilder.addTextSection(PACKAGE_LABEL, packageHelper.getPackageName(project));
            codeDialogBuilder.addAction("Copy Code To Clipboard", new Runnable() {
                @Override
                public void run() {
                    ClipboardHelper.copy(getFinalCode(codeDialogBuilder));
                }
            });
            codeDialogBuilder.addAction("Create File", new Runnable() {
                @Override
                public void run() {
                    try {
                        String folderPath = getFolderPath(codeDialogBuilder);
                        String fileName = pathHelper.getFileName(selectedFile.getName(), "Activity");
                        projectHelper.createFileWithGeneratedCode(project, fileName, folderPath, getFinalCode(codeDialogBuilder));
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

    private String getFolderPath(CodeDialogBuilder codeDialogBuilder) {
        String sourcePath = codeDialogBuilder.getValueForLabel(SOURCE_PATH_LABEL);
        String packageName = codeDialogBuilder.getValueForLabel(PACKAGE_LABEL);
        return pathHelper.getFolderPath(sourcePath, packageName);
    }

    private String getFinalCode(CodeDialogBuilder codeDialogBuilder) {
        String packageName = codeDialogBuilder.getValueForLabel(PACKAGE_LABEL);
        String modifiedCode = codeDialogBuilder.getModifiedCode();
        return pathHelper.getMergedCodeWithPackage(packageName, modifiedCode);
    }

    private String getGeneratedCode(VirtualFile file) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        return getCodeGenerator().produceCode(file.getInputStream(), file.getName());
    }

    private CodeGenerator getCodeGenerator() {
        return CodeGeneratorFactory.createCodeGenerator("Activity_template", new ActivityResourceProvidersFactory());
    }

    @Override
    public void update(AnActionEvent event) {
        event.getPresentation().setVisible(isVisible(event.getDataContext()));
    }

    private boolean isVisible(DataContext dataContext) {
        VirtualFile data = CommonDataKeys.VIRTUAL_FILE.getData(dataContext);
        return data != null && !data.isDirectory() && isXmlFile(data) && isInLayoutFolder(data);
    }

    private boolean isXmlFile(VirtualFile data) {
        return data.getExtension() != null && data.getExtension().equals("xml");
    }

    private boolean isInLayoutFolder(VirtualFile data) {
        return data.getParent().getPath().endsWith("layout");
    }
}
