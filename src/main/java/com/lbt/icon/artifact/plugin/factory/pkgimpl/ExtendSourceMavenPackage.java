/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lbt.icon.artifact.plugin.factory.pkgimpl;

import com.lbt.icon.artifact.plugin.exception.IconArtifactException;
import java.io.File;

/**
 *
 * @author johnson3yo
 */
public class ExtendSourceMavenPackage extends SourceMavenPackage {

    public File generatePackage(File root, String extend) throws IconArtifactException {
        File newFile = new File(root, "src/main/java/com/lbt/icon/".concat(extend.toLowerCase().trim()));
        if (newFile.mkdirs()) {
            return newFile;
        }

        throw new IconArtifactException("source  could not be created ");
    }

    public File generatePackageOnRoot(File root, String extend) throws IconArtifactException {
        File newFile = new File(root, extend.toLowerCase().trim());
        if (newFile.mkdirs()) {
            return newFile;
        }

        throw new IconArtifactException("source  could not be created ");
    }

}
