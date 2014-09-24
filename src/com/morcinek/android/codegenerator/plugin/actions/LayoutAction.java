package com.morcinek.android.codegenerator.plugin.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.morcinek.android.codegenerator.CodeGenerator;
import com.morcinek.android.codegenerator.codegeneration.providers.factories.ActivityResourceProvidersFactory;
import com.morcinek.android.codegenerator.plugin.error.ErrorHandler;
import com.morcinek.android.codegenerator.plugin.utils.CodeGeneratorFactory;
import com.morcinek.android.codegenerator.plugin.utils.PackageHelper;
import com.morcinek.android.codegenerator.plugin.utils.PathHelper;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
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
        try {
            PackageHelper packageHelper = new PackageHelper();
            String packageName = packageHelper.getPackageName(project);
            Messages.showMessageDialog(project, String.format("Package name: %s", packageName), "Information", Messages.getInformationIcon());

        } catch (Exception exception) {
            errorHandler.handleError(project, exception);
        }
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
