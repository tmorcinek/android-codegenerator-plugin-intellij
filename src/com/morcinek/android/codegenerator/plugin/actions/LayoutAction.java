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
            String packageName = packageHelper.getPackageName(project);

            String produceCode = getGeneratedCode(selectedFile.getCanonicalFile());
            final DialogBuilder dialogBuilder = new DialogBuilder(project);
            dialogBuilder.setTitle(String.format("Code generated from: '%s'", selectedFile.getName()));
            JTextArea codeArea = new JTextArea(produceCode);
            codeArea.setBorder(new LineBorder(Color.gray));
            JPanel centerPanel = new JPanel(new BorderLayout());
            JPanel topPanel = new JPanel(new GridLayout(0, 2));
            JBTextField pathTextField = addTextSection(topPanel, "Source Path", "src");
            JBTextField packageTextField = addTextSection(topPanel, "Package", "com.morcinek.test");


            centerPanel.add(topPanel, BorderLayout.PAGE_START);
            centerPanel.add(codeArea, BorderLayout.CENTER);
            dialogBuilder.setCenterPanel(centerPanel);
            dialogBuilder.removeAllActions();
            dialogBuilder.addAction(new AbstractAction("Copy Code To Clipboard") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Object source = e.getSource();
                    dialogBuilder.getDialogWrapper().close(DialogWrapper.OK_EXIT_CODE);
                }
            });
            dialogBuilder.addAction( new AbstractAction("Create File") {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            int show = dialogBuilder.show();
        } catch (Exception exception) {
            errorHandler.handleError(project, exception);
        }
    }

    private JBTextField addTextSection(JPanel topPanel, String label, String defaultText) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.fill = GridBagConstraints.BOTH;
        labelConstraints.weightx = 1;
        topPanel.add(new JLabel(label), labelConstraints);
        GridBagConstraints textFieldConstraints = new GridBagConstraints();
        textFieldConstraints.fill = GridBagConstraints.BOTH;
        textFieldConstraints.weightx = 3;
//        textFieldConstraints.gridwidth =
        JBTextField textField = new JBTextField(defaultText);
        topPanel.add(textField, textFieldConstraints);
        return textField;
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
