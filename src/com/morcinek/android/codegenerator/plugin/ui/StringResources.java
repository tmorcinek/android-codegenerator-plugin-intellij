package com.morcinek.android.codegenerator.plugin.ui;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public interface StringResources {

    String PACKAGE_LABEL = "Package";
    String SOURCE_PATH_LABEL = "Source Path";
    String TITLE_FORMAT_TEXT = "Code generated from: '%s'";
    String COPY_ACTION_LABEL = "Copy Code To Clipboard";
    String CREATE_ACTION_LABEL = "Create File";

    String OVERRIDE_DIALOG_TITLE = "File Already Exists";
    String OVERRIDE_DIALOG_MESSAGE = "File '%s/%s' already exists.\nDo you want to override file content with generated code.";
    String OVERRIDE_DIALOG_YES_TEXT = "Override";
    String OVERRIDE_DIALOG_NO_TEXT = "Cancel";

    String MISSING_SOURCE_PATH_DIALOG_MESSAGE = "You need to select 'Source Path' in which the file will be created.\nSelect one from the project's source roots.";
    String MISSING_SOURCE_PATH_DIALOG_TITLE = "Missing Source Path";
}
