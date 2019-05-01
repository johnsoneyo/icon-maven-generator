/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lbt.icon.artifact.plugin.factory.file;

import com.lbt.icon.artifact.plugin.exception.IconArtifactException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 *
 * @author johnson3yo
 */
public class ValidatorIconFile implements IconFile {

    @Override
    public File generateFile(File root, String[] titles) throws IconArtifactException {
        try {
            Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

            Velocity.init();
            /*  create a context and add data */
            VelocityContext context = new VelocityContext();
            context.put("artifact", titles[1]);
            context.put("package", titles[1].toLowerCase());
            context.put("domain", titles[0].toLowerCase());
            context.put("domainArtifact", titles[0].substring(0, 1).toUpperCase().
                            concat(titles[0].substring(1)));

           
            Writer writer = new FileWriter(new File(root.getAbsolutePath().
                    concat("/").concat(titles[0].substring(0, 1).toUpperCase().
                            concat(titles[0].substring(1)).concat("Validator").concat(".").concat("java"))));

            Velocity.mergeTemplate("/templates/validator.vm", "UTF-8", context, writer);
            writer.flush();
            writer.close();

            return root;
        } catch (IOException ex) {
            Logger.getLogger(PomIconFile.class.getName()).log(Level.SEVERE, null, ex);
        }

        throw new IconArtifactException("Could Not Create Boot Validator File");
    }

}