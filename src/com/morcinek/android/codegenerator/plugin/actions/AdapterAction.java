package com.morcinek.android.codegenerator.plugin.actions;

import com.morcinek.android.codegenerator.codegeneration.providers.ResourceProvidersFactory;
import com.morcinek.android.codegenerator.codegeneration.providers.factories.AdapterResourceProvidersFactory;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class AdapterAction extends LayoutAction {

    @Override
    protected String getResourceName() {
        return "Adapter";
    }

    @Override
    protected String getTemplateName() {
        return "Adapter_template";
    }

    @Override
    protected ResourceProvidersFactory getResourceProvidersFactory() {
        return new AdapterResourceProvidersFactory();
    }
}
