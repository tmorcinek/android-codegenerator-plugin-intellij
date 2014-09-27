package com.morcinek.android.codegenerator.plugin.codegenerator;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.morcinek.android.codegenerator.CodeGenerator;
import com.morcinek.android.codegenerator.codegeneration.providers.ResourceProvidersFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class CodeGeneratorController {

    private CodeGenerator codeGenerator;

    public CodeGeneratorController(String templateName, ResourceProvidersFactory resourceProvidersFactory) {
        codeGenerator = CodeGeneratorFactory.createCodeGenerator(templateName, resourceProvidersFactory);
    }

    public String generateCode(Project project, VirtualFile file, Editor editor) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        editor = getTextEditor(editor, project);
        return codeGenerator.produceCode(getEditorContents(editor), file.getName());
    }

    private Editor getTextEditor(Editor editor, Project project) {
        if (editor == null) {
            return FileEditorManager.getInstance(project).getSelectedTextEditor();
        }
        return editor;
    }

    private ByteArrayInputStream getEditorContents(Editor editor) {
        return new ByteArrayInputStream(getEditorText(editor).getBytes());
    }

    private String getEditorText(Editor editor) {
        return editor.getDocument().getText();
    }
}
