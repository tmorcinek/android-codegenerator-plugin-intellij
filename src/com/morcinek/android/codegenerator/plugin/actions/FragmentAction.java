package com.morcinek.android.codegenerator.plugin.actions;

import com.morcinek.android.codegenerator.codegeneration.providers.ResourceProvidersFactory;
import com.morcinek.android.codegenerator.codegeneration.providers.factories.FragmentResourceProvidersFactory;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class FragmentAction extends LayoutAction {

    @Override
    protected String getResourceName() {
        return "Fragment";
    }

    @Override
    protected String getTemplateName() {
        return "Fragment_template";
    }

    @Override
    protected ResourceProvidersFactory getResourceProvidersFactory() {
        return new FragmentResourceProvidersFactory();
    }
}
