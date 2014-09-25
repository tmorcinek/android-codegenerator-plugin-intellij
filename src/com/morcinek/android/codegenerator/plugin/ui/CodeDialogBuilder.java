package com.morcinek.android.codegenerator.plugin.ui;

import com.google.common.collect.Maps;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBTextField;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class CodeDialogBuilder {

    private final DialogBuilder dialogBuilder;

    private final JPanel topPanel;

    private final JTextArea codeArea;
    private final Map<String, JBTextField> textFieldMap = Maps.newHashMap();

    public CodeDialogBuilder(Project project, String title, String producedCode) {
        dialogBuilder = new DialogBuilder(project);
        dialogBuilder.setTitle(title);

        JPanel centerPanel = new JPanel(new BorderLayout());
        codeArea = prepareCodeArea(producedCode);
        centerPanel.add(codeArea, BorderLayout.CENTER);
        dialogBuilder.setCenterPanel(centerPanel);

        topPanel = new JPanel(new GridLayout(0, 2));
        centerPanel.add(topPanel, BorderLayout.PAGE_START);

        dialogBuilder.removeAllActions();
    }

    public void addAction(String title, final Runnable action) {
        dialogBuilder.addAction(new AbstractAction(title) {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
                dialogBuilder.getDialogWrapper().close(DialogWrapper.OK_EXIT_CODE);
            }
        });
    }

    public void addTextSection(String label, String defaultText) {
        topPanel.add(new JLabel(label));
        JBTextField textField = new JBTextField(defaultText);
        topPanel.add(textField);
        textFieldMap.put(label, textField);
    }

    public int showDialog() {
        return dialogBuilder.show();
    }

    public String getValueForLabel(String key) {
        return textFieldMap.get(key).getText();
    }

    public String getModifiedCode() {
        return codeArea.getText();
    }

    private JTextArea prepareCodeArea(String producedCode) {
        JTextArea codeArea = new JTextArea(producedCode);
        codeArea.setBorder(new LineBorder(JBColor.gray));
        return codeArea;
    }
}
