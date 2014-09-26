package com.morcinek.android.codegenerator.plugin.persistence;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */

@State(
        name = "Settings", storages = {
        @Storage(
                id = "default",
                file = StoragePathMacros.APP_CONFIG + "/settings.xml"
        )
}
)
public class Settings implements PersistentStateComponent<Settings> {

    private String sourcePath;

    public Settings() {
        this.sourcePath = "src/main/java/";
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    @Nullable
    @Override
    public Settings getState() {
        return this;
    }

    @Override
    public void loadState(Settings settings) {
        XmlSerializerUtil.copyBean(settings, this);
    }
}
