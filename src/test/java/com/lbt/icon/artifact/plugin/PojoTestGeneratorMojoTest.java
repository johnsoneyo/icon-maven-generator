/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lbt.icon.artifact.plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author johnson3yo
 */
public class PojoTestGeneratorMojoTest {

    @Test
    public void testRecursiveTraversal() {
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
        if (file.getAbsolutePath().endsWith(".java")) {
            try {
                Path path = Paths.get(file.getAbsolutePath());

                for (String line : Files.readAllLines(path)) {
                    if (line.contains("@Table")) {
                        System.out.println(file.getAbsolutePath());
                        return;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(PojoTestGeneratorMojoTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
