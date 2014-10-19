package com.morcinek.android.codegenerator.plugin.preferences.persistence;

import com.google.common.collect.Maps;
import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.morcinek.android.codegenerator.codegeneration.templates.TemplatesProvider;
import com.morcinek.android.codegenerator.plugin.codegenerator.CodeGeneratorFactory;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
@State(
        name = "CodeGeneratorTemplateSettings",
        storages = {
                @Storage(file = StoragePathMacros.APP_CONFIG + "/code_generator_template_settings.xml")
        }
)
public class TemplateSettings implements PersistentStateComponent<TemplateSettings>, TemplatesProvider {

    private Map<String, String> templateValues = Maps.newHashMap();

    private TemplatesProvider templatesProvider = new CodeGeneratorFactory.ResourceTemplateProvider();

    public static TemplateSettings getInstance() {
        return ServiceManager.getService(TemplateSettings.class);
    }

    public Map<String, String> getTemplateValues() {
        return templateValues;
    }

    public void setTemplateValues(Map<String, String> templateValues) {
        this.templateValues = templateValues;
    }

    @Nullable
    @Override
    public TemplateSettings getState() {
        return this;
    }

    @Override
    public void loadState(TemplateSettings templateSettings) {
        XmlSerializerUtil.copyBean(templateSettings, this);
    }

    @Override
    public String provideTemplateForName(String templateName) {
        if (isUsingCustomTemplateForName(templateName)) {
            return templateValues.get(templateName);
        }
        return templatesProvider.provideTemplateForName(templateName);
    }

    public void removeTemplateForName(String templateName) {
        templateValues.remove(templateName);
    }

    public boolean isUsingCustomTemplateForName(String templateName) {
        return templateValues.containsKey(templateName);
    }

    public void setTemplateForName(String templateName, String template) {
        templateValues.put(templateName, template);
    }
}
