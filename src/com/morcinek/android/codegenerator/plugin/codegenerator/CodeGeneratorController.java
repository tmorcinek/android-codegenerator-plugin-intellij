package com.morcinek.android.codegenerator.plugin.codegenerator;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.morcinek.android.codegenerator.CodeGenerator;
import com.morcinek.android.codegenerator.codegeneration.providers.ResourceProvidersFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class CodeGeneratorController {

    private CodeGenerator codeGenerator;

    public CodeGeneratorController(String templateName, ResourceProvidersFactory resourceProvidersFactory) {
        codeGenerator = CodeGeneratorFactory.createCodeGenerator(templateName, resourceProvidersFactory);
    }

    public String generateCode(Project project, VirtualFile file, Editor editor) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        return codeGenerator.produceCode(getContents(project, editor, file), file.getName());
    }

    private InputStream getContents(Project project, Editor editor, VirtualFile file) throws IOException {
        editor = getEditor(project, editor, file);
        if (editor != null) {
            return new ByteArrayInputStream(getText(editor).getBytes());
        } else {
            return file.getInputStream();
        }
    }

    private Editor getEditor(Project project, Editor editor, VirtualFile file) {
        if (editor == null) {
            TextEditor textEditor = getTextEditor(project, file);
            if (textEditor != null) {
                return textEditor.getEditor();
            }
        }
        return editor;
    }

    private TextEditor getTextEditor(Project project, VirtualFile file) {
        return (TextEditor) FileEditorManager.getInstance(project).getSelectedEditor(file);
    }

    private String getText(Editor editor) {
        return editor.getDocument().getText();
    }
}
