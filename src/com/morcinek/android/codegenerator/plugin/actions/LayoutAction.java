package com.morcinek.android.codegenerator.plugin.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.morcinek.android.codegenerator.extractor.PackageExtractor;
import com.morcinek.android.codegenerator.extractor.XMLPackageExtractor;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class LayoutAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        try {
            Project project = event.getData(PlatformDataKeys.PROJECT);
            String path = "/app/src/main/";
            VirtualFile file = getManifestFileFromPath(project, path);
            if (file.exists()) {
                PackageExtractor packageExtractor = new XMLPackageExtractor();
                String packageName = packageExtractor.extractPackageFromManifestStream(file.getInputStream());
                Messages.showMessageDialog(project, String.format("Package name: %s", packageName), "Information", Messages.getInformationIcon());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private VirtualFile getManifestFileFromPath(Project project, String path) {
        return project.getBaseDir().findFileByRelativePath(path + "AndroidManifest.xml");
    }

    @Override
    public void update(AnActionEvent event) {
        event.getPresentation().setVisible(isVisible(event.getDataContext()));
    }

    private boolean isVisible(DataContext dataContext) {
        VirtualFile data = CommonDataKeys.VIRTUAL_FILE.getData(dataContext);
        return data != null && !data.isDirectory() && isInLayoutFolder(data);
    }

    private boolean isInLayoutFolder(VirtualFile data) {
        return data.getParent().getPath().endsWith("layout");
    }
}
