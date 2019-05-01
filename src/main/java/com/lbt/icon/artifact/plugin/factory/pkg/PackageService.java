/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lbt.icon.artifact.plugin.factory.pkg;

import com.lbt.icon.artifact.plugin.exception.IconArtifactException;
import com.lbt.icon.artifact.plugin.factory.PackageCreator;
import com.lbt.icon.artifact.plugin.factory.pkgimpl.ExtendSourceMavenPackage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author johnson3yo
 */
public class PackageService {
    

    public static File generatePackage(String domain, File root) throws IconArtifactException {
        PackageCreator factory = PackageCreator.getInstance();
        Package domainImpl = factory.getPackage(domain);
        return domainImpl.generatePackage(root);
    }

    public static File generateExtendPackage(String domain, File root, String extend) throws IconArtifactException {
        PackageCreator factory = PackageCreator.getInstance();
        Package domainImpl = factory.getPackage(domain);
        ExtendSourceMavenPackage es = (ExtendSourceMavenPackage) domainImpl;
        return es.generatePackage(root, extend);
    }

    public static File generateBasePackage(String artifactId) throws IconArtifactException, IOException {
        File baseFile = new File(artifactId);
        deleteDirectoryRecursion(Paths.get(baseFile.getAbsolutePath()));
        if (baseFile.mkdir()) {
            return baseFile;
        }

        throw new IconArtifactException(" Could Not Create Directory");
    }

    public static void deleteDirectoryRecursion(Path path) throws IOException {
        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                for (Path entry : entries) {
                    deleteDirectoryRecursion(entry);
                }
            }
        }
        if (Files.exists(path)) {
            Files.delete(path);
        }

    }

}
