/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lbt.icon.artifact.plugin.factory;

import com.lbt.icon.artifact.plugin.factory.file.ApplicationIconFile;
import com.lbt.icon.artifact.plugin.factory.file.IconFile;
import com.lbt.icon.artifact.plugin.factory.file.YamlConfigurationIconFile;
import com.lbt.icon.artifact.plugin.factory.file.ControllerIconFile;
import com.lbt.icon.artifact.plugin.factory.file.DefaultConfigIconFile;
import com.lbt.icon.artifact.plugin.factory.file.DomainIconFile;
import com.lbt.icon.artifact.plugin.factory.file.ExceptionHandlerIconFile;
import com.lbt.icon.artifact.plugin.factory.file.PomIconFile;
import com.lbt.icon.artifact.plugin.factory.file.PropertiesConfigurationIconFile;
import com.lbt.icon.artifact.plugin.factory.file.RepositoryIconFile;
import com.lbt.icon.artifact.plugin.factory.file.ServiceIconFile;
import com.lbt.icon.artifact.plugin.factory.file.ServiceImplIconFile;
import com.lbt.icon.artifact.plugin.factory.file.SwaggerConfigIconFile;
import com.lbt.icon.artifact.plugin.factory.file.TestIconFile;
import com.lbt.icon.artifact.plugin.factory.file.ValidatorIconFile;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author johnson3yo
 */
public class IconFileCreator {

    private static final IconFileCreator INSTANCE = new IconFileCreator();
    private static Map<String, IconFile> fileMap = new HashMap();

    public static final IconFileCreator getInstance() {
        fileMap.put("propconfig", new PropertiesConfigurationIconFile());
        fileMap.put("yamlconfig", new YamlConfigurationIconFile());
        fileMap.put("pom", new PomIconFile());
        fileMap.put("application", new ApplicationIconFile());
        fileMap.put("exceptionhandler", new ExceptionHandlerIconFile());
        fileMap.put("controller", new ControllerIconFile());
        fileMap.put("repository", new RepositoryIconFile());
        fileMap.put("domain", new DomainIconFile());
        fileMap.put("service", new ServiceIconFile());
        fileMap.put("serviceimpl", new ServiceImplIconFile());
        fileMap.put("validator", new ValidatorIconFile());
        fileMap.put("defaultconfig", new DefaultConfigIconFile());
        fileMap.put("swaggerconfig", new SwaggerConfigIconFile());
        fileMap.put("testconfig", new TestIconFile());
        return INSTANCE;
    }

    public IconFile getFile(String impl) {
        return fileMap.get(impl);
    }
}
