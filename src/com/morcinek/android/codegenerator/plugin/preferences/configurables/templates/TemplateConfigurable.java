package com.morcinek.android.codegenerator.plugin.preferences.configurables.templates;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.application.ApplicationManager;
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
import com.intellij.ui.roots.ToolbarPanel;
import com.morcinek.android.codegenerator.plugin.preferences.persistence.TemplateSettings;
import com.morcinek.android.codegenerator.plugin.ui.DialogsFactory;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class TemplateConfigurable extends BaseConfigurable {

    private JPanel editorPanel = new JPanel(new GridLayout());

    private Editor editor;

    private TemplateSettings templateSettings;

    private final String templateName;
    private final String templateHeaderText;
    private final String displayName;

    public TemplateConfigurable(String displayName, String templateHeaderText, String templateName) {
        this.displayName = displayName;
        this.templateHeaderText = templateHeaderText;
        this.templateName = templateName;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        templateSettings = TemplateSettings.getInstance();
        editor = createEditorInPanel(templateSettings.provideTemplateForName(templateName));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(400, 300));
        panel.add(SeparatorFactory.createSeparator(templateHeaderText, null), BorderLayout.PAGE_START);
        panel.add(new ToolbarPanel(editorPanel, new DefaultActionGroup(createResetToDefaultAction())), BorderLayout.CENTER);
        return panel;
    }

    private AnAction createResetToDefaultAction() {
        return new AnAction("Reset to Defaults", "Reset templates to Defaults", AllIcons.Actions.Reset) {
            @Override
            public void actionPerformed(AnActionEvent anActionEvent) {
                if (DialogsFactory.openResetTemplateDialog()) {
                    templateSettings.removeTemplateForName(templateName);
                    ApplicationManager.getApplication().runWriteAction(new Runnable() {
                        @Override
                        public void run() {
                            editor.getDocument().setText(templateSettings.provideTemplateForName(templateName));
                            setUnmodified();
                        }
                    });
                }
            }

            @Override
            public void update(AnActionEvent e) {
                super.update(e);
                e.getPresentation().setEnabled(templateSettings.isUsingCustomTemplateForName(templateName));
            }
        };
    }

    private Editor createEditorInPanel(String string) {
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

        addEditorToPanel(editor);

        return editor;
    }

    private void onTextChanged() {
        myModified = true;
    }

    private void addEditorToPanel(Editor editor) {
        editorPanel.removeAll();
        editorPanel.add(editor.getComponent());
    }

    @Override
    public void disposeUIResources() {
        if (editor != null) {
            EditorFactory.getInstance().releaseEditor(editor);
            editor = null;
        }
        templateSettings = null;
    }

    @Override
    public void apply() throws ConfigurationException {
        templateSettings.setTemplateForName(templateName, editor.getDocument().getText());
        setUnmodified();
    }

    @Override
    public void reset() {
        EditorFactory.getInstance().releaseEditor(editor);
        editor = createEditorInPanel(templateSettings.provideTemplateForName(templateName));
        setUnmodified();
    }

    private void setUnmodified() {
        setModified(false);
    }

    @Nls
    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return editorPanel;
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }
}
