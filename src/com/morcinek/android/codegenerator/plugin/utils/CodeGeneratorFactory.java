package com.morcinek.android.codegenerator.plugin.utils;

import com.morcinek.android.codegenerator.CodeGenerator;
import com.morcinek.android.codegenerator.codegeneration.TemplateCodeGenerator;
import com.morcinek.android.codegenerator.codegeneration.providers.ResourceProvidersFactory;
import com.morcinek.android.codegenerator.codegeneration.templates.ResourceTemplatesProvider;
import com.morcinek.android.codegenerator.extractor.XMLResourceExtractor;
import com.morcinek.android.codegenerator.extractor.string.FileNameExtractor;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class CodeGeneratorFactory {

    public static CodeGenerator createCodeGenerator(String templateName, ResourceProvidersFactory resourceProvidersFactory) {
        return new CodeGenerator(XMLResourceExtractor.createResourceExtractor(),
                new FileNameExtractor(),
                //FIXME change ResourceTemplateProvider for PreferencesTemplateProvider
                new TemplateCodeGenerator(templateName, resourceProvidersFactory, new ResourceTemplatesProvider()));
    }
}
