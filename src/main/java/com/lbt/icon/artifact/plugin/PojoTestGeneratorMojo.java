/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lbt.icon.artifact.plugin;

import java.io.File;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 *
 * @author johnson3yo
 */
@Mojo(requiresProject = false, name = "pojotest")
public class PojoTestGeneratorMojo extends AbstractMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().info("::::: Create Test From Root  ::::::::::::");

        
        dirTree(new File("/home/johnson3yo/icon-multimodule"));
    }

    public static void dirTree(File dir) {
        File[] subdirs = dir.listFiles();
        for (File subdir : subdirs) {
            if (subdir.isDirectory()) {
                dirTree(subdir);
            } else {
                doFile(subdir);
            }
        }
    }

    public static void doFile(File file) {
        System.out.println(file.getAbsolutePath());
    }

}
