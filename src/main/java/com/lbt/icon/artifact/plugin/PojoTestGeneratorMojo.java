/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lbt.icon.artifact.plugin;

import com.lbt.icon.artifact.plugin.exception.IconArtifactException;
import com.lbt.icon.artifact.plugin.factory.file.FileService;
import com.lbt.icon.artifact.plugin.factory.pkg.PackageService;
import com.lbt.icon.artifact.plugin.factory.pkg.PathProperty;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Mojo(requiresProject = false, name = "generatetests")
public class PojoTestGeneratorMojo extends AbstractMojo {

    private File root;
    private List<File> files;
    private List<PathProperty> packages;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        files = new ArrayList();
        packages = new ArrayList();

        getLog().info("::::: Select Icon Module Root  ::::::::::::");

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int r = fc.showOpenDialog(new JPanel());

        if (r == JFileChooser.APPROVE_OPTION) {
            root = fc.getSelectedFile();
        }

        root = fc.getSelectedFile();
        processPath(root);

        for (File file : files) {
            try {
                PathProperty retrieved = PackageService.retrievePackageWithClassName(file.getAbsolutePath());
                packages.add(retrieved);
            } catch (IconArtifactException ex) {
            }
        }

        packages.stream().forEach(property -> {

            try {
                File testRoot = PackageService.generatePackageForTests("testmvnpackage", root, property.getClassPath());
                FileService.generateIconFile(testRoot, new String[]{property.getClassName(),
                    property.getClassPackage()}, "testconfig");
            } catch (IconArtifactException | IOException ex) {
                Logger.getLogger(PojoTestGeneratorMojo.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        getLog().info("::::: Generation Complete  ::::::::::::");
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
