/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lbt.icon.artifact.plugin.factory.pkg;

import com.lbt.icon.artifact.plugin.exception.IconArtifactException;
import com.lbt.icon.artifact.plugin.factory.PackageCreator;
import com.lbt.icon.artifact.plugin.factory.pkgimpl.ExtendSourceMavenPackage;
import com.lbt.icon.artifact.plugin.factory.pkgimpl.TestMavenPackage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static File generatePackageForTests(String domain, File root, String path) throws IconArtifactException, IOException {
        PackageCreator factory = PackageCreator.getInstance();
        Package domainImpl = factory.getPackage(domain);
        TestMavenPackage tm = (TestMavenPackage) domainImpl;
        return tm.generatePackageWithPath(root, path);
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

    public static PathProperty retrievePackageWithClassName(String path) throws IconArtifactException {

        String packages$ = path.substring(path.indexOf("java") + 5).
                replaceAll(".[A-Za-z]+.\\.java", "").replaceAll("/", ".").replace("\\", ".");
        String className = null;
        Pattern pattern = Pattern.compile("[A-Za-z]+.\\.java");
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            className = matcher.group().replaceAll(".java", "");
        }
        if (className == null) {
            throw new IconArtifactException(" No Java Class Specified in the Path");
        }

        
        return new PathProperty(className,packages$, path.substring(path.indexOf("java") + 5).
                replaceAll(".[A-Za-z]+.\\.java", ""));
    }

}
