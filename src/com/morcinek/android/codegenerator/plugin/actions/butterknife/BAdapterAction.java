package com.morcinek.android.codegenerator.plugin.actions.butterknife;

import com.morcinek.android.codegenerator.codegeneration.providers.ResourceProvidersFactory;
import com.morcinek.android.codegenerator.codegeneration.providers.factories.BAdapterResourceProvidersFactory;
import com.morcinek.android.codegenerator.plugin.actions.LayoutAction;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class BAdapterAction extends LayoutAction {

    @Override
    protected String getResourceName() {
        return "Adapter";
    }

    @Override
    protected String getTemplateName() {
        return "BAdapter_template";
    }

    @Override
    protected ResourceProvidersFactory getResourceProvidersFactory() {
        return new BAdapterResourceProvidersFactory();
    }
}
