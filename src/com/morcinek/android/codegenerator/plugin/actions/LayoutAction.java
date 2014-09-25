package com.morcinek.android.codegenerator.plugin.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBTextField;
import com.morcinek.android.codegenerator.CodeGenerator;
import com.morcinek.android.codegenerator.codegeneration.providers.factories.ActivityResourceProvidersFactory;
import com.morcinek.android.codegenerator.plugin.error.ErrorHandler;
import com.morcinek.android.codegenerator.plugin.ui.CodeDialogBuilder;
import com.morcinek.android.codegenerator.plugin.utils.ClipboardHelper;
import com.morcinek.android.codegenerator.plugin.utils.CodeGeneratorFactory;
import com.morcinek.android.codegenerator.plugin.utils.PackageHelper;
import com.morcinek.android.codegenerator.plugin.utils.PathHelper;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class LayoutAction extends AnAction {

    private final ErrorHandler errorHandler = new ErrorHandler();

    private final PackageHelper packageHelper = new PackageHelper();

    private final PathHelper pathHelper = new PathHelper();

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        VirtualFile selectedFile = event.getData(PlatformDataKeys.VIRTUAL_FILE);
        try {
            PackageHelper packageHelper = new PackageHelper();
            String produceCode = getGeneratedCode(selectedFile);
            final CodeDialogBuilder codeDialogBuilder = new CodeDialogBuilder(project,
                    String.format("Code generated from: '%s'", selectedFile.getName()), produceCode);
            codeDialogBuilder.addTextSection("Source Path", "src");
            codeDialogBuilder.addTextSection("Package", packageHelper.getPackageName(project));
            codeDialogBuilder.addAction("Copy Code To Clipboard", new Runnable() {
                @Override
                public void run() {
                    ClipboardHelper.copy(getFinalCode(codeDialogBuilder));
                }
            });
            codeDialogBuilder.addAction("Create File", new Runnable() {
                @Override
                public void run() {
                    //TODO implement
                }
            });
            codeDialogBuilder.showDialog();
        } catch (Exception exception) {
            errorHandler.handleError(project, exception);
        }
    }

    private String getFinalCode(CodeDialogBuilder codeDialogBuilder) {
        return pathHelper.getMergedCodeWithPackage(codeDialogBuilder.getValueForLabel("Package"), codeDialogBuilder.getModifiedCode());
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
