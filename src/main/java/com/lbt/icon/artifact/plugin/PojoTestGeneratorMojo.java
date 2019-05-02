/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lbt.icon.artifact.plugin;

import com.lbt.icon.artifact.plugin.exception.IconArtifactException;
import com.lbt.icon.artifact.plugin.factory.pkg.PackageService;
import com.lbt.icon.artifact.plugin.factory.pkg.TestPath;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
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

    private File root;
    private List<File> files;
    private List<String> packages;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().info("::::: Select Icon Module Root  ::::::::::::");

        JFileChooser fc = new JFileChooser();
        int retValue = fc.showOpenDialog(new JPanel());
        if (retValue == JFileChooser.APPROVE_OPTION) {
            root = fc.getSelectedFile();
            processPath(root);
        }
        
        files = new ArrayList();
        packages = new ArrayList();
        for (File file : files) {
            try {
                TestPath retrieved = PackageService.retrievePackageWithClassName(file.getAbsolutePath());
                packages.add(retrieved.getClassPackage());
            } catch (IconArtifactException ex) {
            }
        }

        packages.forEach(System.out::println);
    }

    public void processPath(File root) {
        File[] subdirs = root.listFiles();
        for (File subdir : subdirs) {
            if (subdir.isDirectory()) {
                processPath(subdir);
            } else {
                generatePaths(subdir);
            }
        }
    }

    public void generatePaths(File file) {
        if (file.getAbsolutePath().endsWith(".java")) {
            try {
                Path path = Paths.get(file.getAbsolutePath());

                for (String line : Files.readAllLines(path)) {
                    if (line.contains("@Table")) {
                        files.add(file);
                        return;
                    }
                }
            } catch (IOException ex) {

            }
        }
    }

}
