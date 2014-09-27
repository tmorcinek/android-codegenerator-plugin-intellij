package com.morcinek.android.codegenerator.plugin.actions;

import com.morcinek.android.codegenerator.codegeneration.providers.ResourceProvidersFactory;
import com.morcinek.android.codegenerator.codegeneration.providers.factories.ActivityResourceProvidersFactory;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class ActivityAction extends LayoutAction {

    @Override
    protected String getResourceName() {
        return "Activity";
    }

    @Override
    protected String getTemplateName() {
        return "Activity_template";
    }

    @Override
    protected ResourceProvidersFactory getResourceProvidersFactory() {
        return new ActivityResourceProvidersFactory();
    }
}
