package com.morcinek.android.codegenerator.plugin.actions.butterknife;

import com.morcinek.android.codegenerator.codegeneration.providers.ResourceProvidersFactory;
import com.morcinek.android.codegenerator.codegeneration.providers.factories.BActivityResourceProvidersFactory;
import com.morcinek.android.codegenerator.codegeneration.providers.factories.FragmentResourceProvidersFactory;
import com.morcinek.android.codegenerator.plugin.actions.LayoutAction;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class BFragmentAction extends LayoutAction {

    @Override
    protected String getResourceName() {
        return "Fragment";
    }

    @Override
    protected String getTemplateName() {
        return "BFragment_template";
    }

    @Override
    protected ResourceProvidersFactory getResourceProvidersFactory() {
        return new BActivityResourceProvidersFactory();
    }
}
