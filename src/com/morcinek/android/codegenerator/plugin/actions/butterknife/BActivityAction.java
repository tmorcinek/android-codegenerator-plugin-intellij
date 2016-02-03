package com.morcinek.android.codegenerator.plugin.actions.butterknife;

import com.morcinek.android.codegenerator.codegeneration.providers.ResourceProvidersFactory;
import com.morcinek.android.codegenerator.codegeneration.providers.factories.BActivityResourceProvidersFactory;
import com.morcinek.android.codegenerator.plugin.actions.LayoutAction;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class BActivityAction extends LayoutAction {

    @Override
    protected String getResourceName() {
        return "Activity";
    }

    @Override
    protected String getTemplateName() {
        return "BActivity_template";
    }

    @Override
    protected ResourceProvidersFactory getResourceProvidersFactory() {
        return new BActivityResourceProvidersFactory();
    }
}
