package com.morcinek.android.codegenerator.plugin.actions;

import com.morcinek.android.codegenerator.CodeGenerator;
import com.morcinek.android.codegenerator.codegeneration.providers.factories.FragmentResourceProvidersFactory;
import com.morcinek.android.codegenerator.plugin.utils.CodeGeneratorFactory;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class FragmentAction extends LayoutAction {

    @Override
    protected String getResourceName() {
        return "Fragment";
    }

    @Override
    protected CodeGenerator getCodeGenerator() {
        return CodeGeneratorFactory.createCodeGenerator("Fragment_template", new FragmentResourceProvidersFactory());
    }
}
