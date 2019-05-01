/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lbt.icon.artifact.plugin;

import com.lbt.icon.artifact.plugin.exception.IconArtifactException;
import com.lbt.icon.artifact.plugin.factory.file.FileService;
import com.lbt.icon.artifact.plugin.factory.pkg.PackageService;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.codehaus.plexus.components.interactivity.Prompter;
import org.codehaus.plexus.components.interactivity.PrompterException;

/**
 *
 * @author johnson3yo
 */
@Mojo(requiresProject = false, name = "start")
public class StarterMojo extends AbstractMojo {

    @Component
    private Prompter prompter;
    private Map<String, String> propertyMap;

    public void execute() throws MojoExecutionException {

        propertyMap = new HashMap();
        propertyMap.put("prop", "properties");
        propertyMap.put("yml", "yml");

        try {

            getLog().info("::::: Initializing Icon Module Generator ::::::::::::");

            String artifactAnswer = prompter.prompt("Title of the project (No Special Characters or Space , Preferably CamelCase)").trim();
            String descriptionAnswer = prompter.prompt("Brief Description of the project ").trim();

            /*
               Generate Base Package 
             */
            File generateBasePackage = PackageService.generateBasePackage(artifactAnswer);
            String[] titles = new String[]{artifactAnswer, descriptionAnswer};
            FileService.generateIconFile(generateBasePackage, titles, "pom");

            /*
                Generate Maven Source With Base Package 
             */
            File sourceFile = PackageService.generateExtendPackage("extendsrcmvnpackage", generateBasePackage, artifactAnswer);
            getLog().info(":::::::: Generated Source ::::::::::::");

            /*
                Generate SpringBoot Application Main
             */
            FileService.generateIconFile(sourceFile, titles, "application");
            getLog().info(":::::::: Generated Application Main ::::::::::::");

            /*
                Generate Default 
             */
            getLog().info(":::: Generating Default Packages :::::::::::");

            FileService.setUpDefaultPackageFiles(sourceFile, titles, prompter);

            getLog().info(":::: Domain Packages /Facade Generation Complete :::::::::::");

            /*
                Generate Test Source 
             */
            PackageService.generatePackage("testmvnpackage", generateBasePackage);
            getLog().info(":::::::: Generated Test Source ::::::::::::");

            /*
                Geneate Maven Resource 
             */
            File resourceFile = PackageService.generatePackage("rscmvnpackage", generateBasePackage);
            getLog().info(":::::::: Generated Resource ::::::::::::");

            String configAnswer = null;
            String propertyType = null;

            while (!Optional.ofNullable(propertyType).isPresent()) {
                configAnswer = prompter.prompt(" Do you prefer YAML or Properties configuration please specify (yml/prop) ");
                propertyType = propertyMap.get(configAnswer.toLowerCase().trim());
            }

            FileService.generatePropertiesFile(resourceFile, configAnswer.toLowerCase().trim());

        } catch (PrompterException | IconArtifactException | IOException ex) {
            Logger.getLogger(StarterMojo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
