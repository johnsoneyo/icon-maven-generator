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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author johnson3yo
 */
public class PojoTestGeneratorMojoTest {

    private List<File> files = new ArrayList();
    private List<String> packages = new ArrayList();

    @Test
    public void testRecursiveTraversal() {
        operation1(new File("/home/johnson3yo/NetBeansProjects/pevi-core"));

        Integer value = new Integer(0);

        operation2(value);
    }

    private void operation1(File dir) {
        File[] subdirs = dir.listFiles();
        for (File subdir : subdirs) {
            if (subdir.isDirectory()) {
                operation1(subdir);
            } else {
                doFile(subdir);
            }
        }
    }

    private void doFile(File file) {

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
                Logger.getLogger(PojoTestGeneratorMojoTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void operation2(Integer index) {

        for (File file : files) {
            try {
                TestPath retrieved = PackageService.retrievePackageWithClassName(file.getAbsolutePath());
                packages.add(retrieved.getClassPackage());
            } catch (IconArtifactException ex) {
                Logger.getLogger(PojoTestGeneratorMojoTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        packages.forEach(System.out::println);

    }

    @Test
    public void testGetPackageFromLinuxPath() throws IconArtifactException {
        String path = "/home/johnson3yo/NetBeansProjects/pevi-core/src/main/java/com/pevi/core/models/entity/PvAdmin.java";
        TestPath retrieved = PackageService.retrievePackageWithClassName(path);
        Assert.assertTrue(!retrieved.getClassPackage().contains("/"));
        Assert.assertTrue(retrieved.getClassPackage().contains("."));
    }

    @Test
    public void testGetClassNameFromLinuxPath() throws IconArtifactException {
        String path = "/home/johnson3yo/NetBeansProjects/pevi-core/src/main/java/com/pevi/core/models/entity/PvAdmin.java";
        TestPath retrieved = PackageService.retrievePackageWithClassName(path);
        Assert.assertEquals("PvAdmin", retrieved.getClassName());
    }

    @Test
    public void testGetPackageFromWindowPath() throws IconArtifactException {
        String path = "C:\\home\\johnson3yo\\NetBeansProjects\\pevi-core\\src\\main\\java\\com\\pevi\\core\\models\\entity\\PvAdmin.java";
        TestPath retrieved = PackageService.retrievePackageWithClassName(path);
        Assert.assertTrue(!retrieved.getClassPackage().contains("\\"));

    }

    @Test
    public void testGetClassNameFromWindowPath() throws IconArtifactException {
        String path = "C:\\home\\johnson3yo\\NetBeansProjects\\pevi-core\\src\\main\\java\\com\\pevi\\core\\models\\entity\\PvAdmin.java";
        TestPath retrieved = PackageService.retrievePackageWithClassName(path);

        Assert.assertEquals("PvAdmin", retrieved.getClassName());
    }

    @Test(expected = IconArtifactException.class)
    public void testRetrieveWithoutClassName() throws IconArtifactException {
        String path = "C:\\home\\johnson3yo\\NetBeansProjects\\pevi-core\\src\\main\\java\\com\\pevi\\core\\models\\entity";
        PackageService.retrievePackageWithClassName(path);

    }

}
