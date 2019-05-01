/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lbt.icon.artifact.plugin.factory.file;

import com.lbt.icon.artifact.plugin.exception.IconArtifactException;
import java.io.File;

/**
 *
 * @author johnson3yo
 */
public interface  IconFile {
   File generateFile(File root,String[] titles) throws IconArtifactException;
}
