package com.morcinek.android.codegenerator.plugin.codegenerator;

import com.google.common.io.Resources;
import com.morcinek.android.codegenerator.CodeGenerator;
import com.morcinek.android.codegenerator.codegeneration.TemplateCodeGenerator;
import com.morcinek.android.codegenerator.codegeneration.providers.ResourceProvidersFactory;
import com.morcinek.android.codegenerator.codegeneration.templates.TemplatesProvider;
import com.morcinek.android.codegenerator.extractor.XMLResourceExtractor;
import com.morcinek.android.codegenerator.extractor.string.FileNameExtractor;
import com.morcinek.android.codegenerator.plugin.preferences.persistence.TemplateSettings;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class CodeGeneratorFactory {

    public static CodeGenerator createCodeGenerator(String templateName, ResourceProvidersFactory resourceProvidersFactory) {
        return new CodeGenerator(XMLResourceExtractor.createResourceExtractor(),
                new FileNameExtractor(),
                //FIXME change ResourceTemplateProvider for PreferencesTemplateProvider
                new TemplateCodeGenerator(templateName, resourceProvidersFactory, TemplateSettings.getInstance()));
    }

    public static class ResourceTemplateProvider implements TemplatesProvider {

        @Override
        public String provideTemplateForName(String templateName) {
            URL url = getClass().getClassLoader().getResource(templateName);
            try {
                return Resources.toString(url, Charset.defaultCharset());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
