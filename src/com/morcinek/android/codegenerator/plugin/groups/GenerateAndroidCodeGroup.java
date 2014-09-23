package com.morcinek.android.codegenerator.plugin.groups;

import com.intellij.openapi.actionSystem.DefaultActionGroup;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class GenerateAndroidCodeGroup extends DefaultActionGroup {

    @Override
    public boolean hideIfNoVisibleChildren() {
        return true;
    }
}
