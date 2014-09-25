package com.morcinek.android.codegenerator.plugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.morcinek.android.codegenerator.CodeGenerator;
import com.morcinek.android.codegenerator.codegeneration.providers.factories.MenuResourceProvidersFactory;
import com.morcinek.android.codegenerator.plugin.actions.visibility.ActionVisibilityHelper;
import com.morcinek.android.codegenerator.plugin.error.ErrorHandler;
import com.morcinek.android.codegenerator.plugin.ui.CodeDialogBuilder;
import com.morcinek.android.codegenerator.plugin.ui.StringResources;
import com.morcinek.android.codegenerator.plugin.utils.ClipboardHelper;
import com.morcinek.android.codegenerator.plugin.utils.CodeGeneratorFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

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
            String generatedCode = getGeneratedCode(selectedFile);
            final CodeDialogBuilder codeDialogBuilder = new CodeDialogBuilder(project,
                    String.format(StringResources.TITLE_FORMAT_TEXT, selectedFile.getName()), generatedCode);
            codeDialogBuilder.addAction(StringResources.COPY_ACTION_LABEL, new Runnable() {
                @Override
                public void run() {
                    ClipboardHelper.copy(codeDialogBuilder.getModifiedCode());
                }
            });
            codeDialogBuilder.showDialog();
        } catch (Exception exception) {
            errorHandler.handleError(project, exception);
        }
    }

    private String getGeneratedCode(VirtualFile file) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        return getCodeGenerator().produceCode(file.getInputStream(), file.getName());
    }

    private CodeGenerator getCodeGenerator() {
        return CodeGeneratorFactory.createCodeGenerator("Menu_template", new MenuResourceProvidersFactory());
    }

    @Override
    public void update(AnActionEvent event) {
        event.getPresentation().setVisible(actionVisibilityHelper.isVisible(event.getDataContext()));
    }
}
