/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lbt.icon.artifact.plugin.factory.pkgimpl;

import com.lbt.icon.artifact.plugin.exception.IconArtifactException;
import com.lbt.icon.artifact.plugin.factory.MavenPackage;
import java.io.File;

/**
 *
 * @author johnson3yo
 */
public class TestMavenPackage implements MavenPackage {

    @Override
    public File generatePackage(File root) throws IconArtifactException {
        File newFile = new File(root, "src/test/java/com/lbt/icon");
        if (newFile.mkdirs()) {
            return newFile;
        }
        throw new IconArtifactException("test could not be created ");
    }

}
