/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lbt.icon.artifact.plugin.factory.file;

import com.lbt.icon.artifact.plugin.exception.IconArtifactException;
import com.lbt.icon.artifact.plugin.factory.IconFileCreator;
import com.lbt.icon.artifact.plugin.factory.PackageCreator;
import com.lbt.icon.artifact.plugin.factory.pkgimpl.ExtendSourceMavenPackage;
import java.io.File;
import org.codehaus.plexus.components.interactivity.Prompter;
import org.codehaus.plexus.components.interactivity.PrompterException;

/**
 *
 * @author johnson3yo
 */
public class FileService {

    public static void generatePropertiesFile(File root, String configType) throws IconArtifactException {
        IconFileCreator instance = IconFileCreator.getInstance();
        if (configType.equals("yml")) {
            IconFile file = instance.getFile("yamlconfig");
            file.generateFile(root, null);
            return;
        }

        IconFile file = instance.getFile("propconfig");
        file.generateFile(root, null);
    }

    public static File generateIconFile(File root, String[] titles, String type) throws IconArtifactException {
        IconFileCreator instance = IconFileCreator.getInstance();
        IconFile file = instance.getFile(type);
        return file.generateFile(root, titles);
    }
   

    public static void setUpDefaultPackageFiles(File sourceFile, String[] titles, Prompter prompter) throws IconArtifactException, PrompterException {

        PackageCreator factory = PackageCreator.getInstance();
        com.lbt.icon.artifact.plugin.factory.pkg.Package domainImpl = factory.getPackage("extendsrcmvnpackage");
        ExtendSourceMavenPackage es = (ExtendSourceMavenPackage) domainImpl;
        String[] $packages = new String[]{"config", "event", "domain",
            "exception", "filter", "security", "util",
            "validator", "web", "type"};

        for (String $package : $packages) {
            File currentSourceFile = es.generatePackageOnRoot(sourceFile, $package);
            if ($package.equals("exception")) {
                generateIconFile(currentSourceFile, titles, "exceptionhandler");
            }

            if ($package.equals("config")) {
                generateIconFile(currentSourceFile, titles, "defaultconfig");
                generateIconFile(currentSourceFile, titles, "swaggerconfig");
            }

            if ($package.equals("domain")) {
                String domainAnswer = null;

                /*
                    Short Circuit Loop And Terminate On Quit
                 */
                while (domainAnswer == null || domainAnswer.length() < 1) {
                    domainAnswer = prompter.prompt(" What domain do you want configured (Quit to Terminate) ");

                    if (domainAnswer.length() < 1) {
                        continue;
                    }

                    if (domainAnswer.equals("quit")) {
                        break;
                    }
                    File domainCurrentFile = es.generatePackageOnRoot(currentSourceFile, domainAnswer.toLowerCase());
                    File innerCurrentFile = generateIconFile(domainCurrentFile, new String[]{domainAnswer, titles[0], titles[1]}, "controller");
                    es.generatePackageOnRoot(innerCurrentFile, "dto");
                    innerCurrentFile = generateIconFile(domainCurrentFile, new String[]{domainAnswer, titles[0], titles[1]}, "repository");
                    generateIconFile(domainCurrentFile, new String[]{domainAnswer, titles[0], titles[1]}, "domain");
                    generateIconFile(domainCurrentFile, new String[]{domainAnswer, titles[0], titles[1]}, "service");
                    generateIconFile(domainCurrentFile, new String[]{domainAnswer, titles[0], titles[1]}, "serviceimpl");
                    generateIconFile(domainCurrentFile, new String[]{domainAnswer, titles[0], titles[1]}, "validator");

                    domainAnswer = null;
                }
            }
        }

    }

}
