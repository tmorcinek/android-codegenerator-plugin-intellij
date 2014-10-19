package com.morcinek.android.codegenerator.plugin.preferences.configurables;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.morcinek.android.codegenerator.plugin.preferences.configurables.templates.TemplateConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class MainConfigurable implements Configurable.Composite, Configurable.NoScroll, Configurable {

    private Configurable[] configurables;

    public MainConfigurable() {
        super();
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Android Code Generator";
    }

    @Nullable
    @NonNls
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Override
    public JComponent createComponent() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("<html>Here you can edit 'Android Code Generator Plugin' settings. In children pages you can edit template for each code generation method.</html>");
        label.setVerticalAlignment(SwingConstants.TOP);
        panel.add(label, BorderLayout.PAGE_START);
        return panel;
    }

    public void disposeUIResources() {
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
    }

    @Override
    public void reset() {
    }

    public Configurable[] getConfigurables() {
        if (configurables == null) {
            configurables = new Configurable[]{new TemplateConfigurable()};
        }
        return configurables;
    }
}
