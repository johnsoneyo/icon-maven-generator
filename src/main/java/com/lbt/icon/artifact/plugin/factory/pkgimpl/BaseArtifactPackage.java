/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lbt.icon.artifact.plugin.factory.pkgimpl;

import com.lbt.icon.artifact.plugin.exception.IconArtifactException;
import com.lbt.icon.artifact.plugin.factory.pkg.Package;
import java.io.File;

/**
 *
 * @author johnson3yo
 */
public class BaseArtifactPackage implements Package {

    @Override
    public File generatePackage(File root) throws IconArtifactException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
