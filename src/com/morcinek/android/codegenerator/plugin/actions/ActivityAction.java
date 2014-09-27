package com.morcinek.android.codegenerator.plugin.actions;

import com.morcinek.android.codegenerator.CodeGenerator;
import com.morcinek.android.codegenerator.codegeneration.providers.factories.ActivityResourceProvidersFactory;
import com.morcinek.android.codegenerator.plugin.codegenerator.CodeGeneratorFactory;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class ActivityAction extends LayoutAction {

    @Override
    protected String getResourceName() {
        return "Activity";
    }

    @Override
    protected CodeGenerator getCodeGenerator() {
        return CodeGeneratorFactory.createCodeGenerator("Activity_template", new ActivityResourceProvidersFactory());
    }
}
