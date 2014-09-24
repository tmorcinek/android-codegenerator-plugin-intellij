package com.morcinek.android.codegenerator.plugin.utils;

import com.morcinek.android.codegenerator.codegeneration.builders.file.ClassNameBuilder;
import com.morcinek.android.codegenerator.codegeneration.builders.file.PackageBuilder;
import com.morcinek.android.codegenerator.extractor.string.FileNameExtractor;

/**
 * Copyright 2014 Tomasz Morcinek. All rights reserved.
 */
public class PathHelper {

    public String getMergedCodeWithPackage(String packageName, String generateCode) {
        if (!packageName.isEmpty()) {
            return new PackageBuilder(packageName).builtString() + generateCode;
        }
        return generateCode;
    }

    public String getFileName(String filePath, String resourceName) {
        String fileName = new FileNameExtractor().extractFromString(filePath);
        return new ClassNameBuilder(fileName).builtString() + resourceName + ".java";
    }

    public String getFolderPath(String sourcePath, String packageName) {
        return sourcePath + "/" + packageName.replace(".", "/");
    }
}
