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
public class ResourceMavenPackage implements MavenPackage{

    @Override
    public File generatePackage(File root) throws IconArtifactException {
          File newFile = new File(root, "src/main/resources");
        if (newFile.mkdirs()) {
           return newFile;
        }
       throw new IconArtifactException("resource  could not be created ");
    }
    
}
