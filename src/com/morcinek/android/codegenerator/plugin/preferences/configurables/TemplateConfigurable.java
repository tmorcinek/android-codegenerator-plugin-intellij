package com.morcinek.android.codegenerator.plugin.preferences.configurables;

import com.intellij.openapi.options.BaseConfigurable;
import com.intellij.openapi.options.ConfigurationException;
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
        JPanel myPanel = new JPanel(new GridLayout(2,2));
        myPanel.setPreferredSize(new Dimension(700, 500));
        myPanel.add(new JButton("tomek"));
        myPanel.add(new JButton("tomek"));
        myPanel.add(new JButton("tomek"));
        myPanel.add(new JButton("tomek"));
        return myPanel;
    }

    @Override
    public void apply() throws ConfigurationException {

    }

    @Override
    public void reset() {

    }

    @Override
    public boolean isModified() {
        return false;
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