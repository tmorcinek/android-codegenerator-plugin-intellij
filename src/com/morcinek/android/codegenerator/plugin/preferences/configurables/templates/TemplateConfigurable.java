package com.morcinek.android.codegenerator.plugin.preferences.configurables.templates;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.editor.colors.EditorColors;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.event.DocumentAdapter;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.options.BaseConfigurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.ui.SeparatorFactory;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class TemplateConfigurable extends BaseConfigurable {

    public TemplateConfigurable() {
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(SeparatorFactory.createSeparator("This is sparta", null), BorderLayout.PAGE_START);

        String string = "thids is content jkdlsjfd sfjkdls fjdslkf dsf djks fds fjdks fjd sfjd skf dksj fdsf dksf djs fdsfds f\n TO powinna byc nowa linia";
        Editor editor = createEditor(string);
        panel.add(editor.getComponent(), BorderLayout.CENTER);
        return panel;
    }

    private Editor createEditor(String string) {
        EditorFactory editorFactory = EditorFactory.getInstance();
        Editor editor = editorFactory.createEditor(editorFactory.createDocument(string));

        EditorSettings editorSettings = editor.getSettings();
        editorSettings.setVirtualSpace(false);
        editorSettings.setLineMarkerAreaShown(false);
        editorSettings.setIndentGuidesShown(false);
        editorSettings.setLineNumbersShown(false);
        editorSettings.setFoldingOutlineShown(false);
        editorSettings.setAdditionalColumnsCount(3);
        editorSettings.setAdditionalLinesCount(3);

        EditorColorsScheme scheme = editor.getColorsScheme();
        scheme.setColor(EditorColors.CARET_ROW_COLOR, null);

        editor.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            public void documentChanged(DocumentEvent e) {
                onTextChanged();
            }
        });
        return editor;
    }

    private void onTextChanged() {
        myModified = true;
    }

    @Override
    public void apply() throws ConfigurationException {
        System.out.println("apply");
    }

    @Override
    public void reset() {
        System.out.println("reset");
    }

    @Override
    public void disposeUIResources() {
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Activity Template";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }
}