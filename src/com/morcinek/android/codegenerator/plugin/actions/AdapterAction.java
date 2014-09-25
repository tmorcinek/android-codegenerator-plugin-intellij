package com.morcinek.android.codegenerator.plugin.actions;

import com.morcinek.android.codegenerator.CodeGenerator;
import com.morcinek.android.codegenerator.codegeneration.providers.factories.AdapterResourceProvidersFactory;
import com.morcinek.android.codegenerator.plugin.utils.CodeGeneratorFactory;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class AdapterAction extends LayoutAction {

    @Override
    protected String getResourceName() {
        return "Adapter";
    }

    @Override
    protected CodeGenerator getCodeGenerator() {
        return CodeGeneratorFactory.createCodeGenerator("Adapter_template", new AdapterResourceProvidersFactory());
    }
}
