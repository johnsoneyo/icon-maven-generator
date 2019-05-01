/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lbt.icon.artifact.plugin.factory;

import com.lbt.icon.artifact.plugin.factory.pkgimpl.SourceMavenPackage;
import com.lbt.icon.artifact.plugin.factory.pkgimpl.ResourceMavenPackage;
import com.lbt.icon.artifact.plugin.factory.pkgimpl.DomainPackage;
import com.lbt.icon.artifact.plugin.factory.pkgimpl.TestMavenPackage;
import com.lbt.icon.artifact.plugin.factory.pkg.Package;
import com.lbt.icon.artifact.plugin.factory.pkgimpl.ExtendSourceMavenPackage;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author johnson3yo
 */
public class PackageCreator {

    private static final PackageCreator INSTANCE = new PackageCreator();
    private static Map<String, Package> packageMap = new HashMap();

    public static final PackageCreator getInstance() {
        packageMap.put("domain", new DomainPackage());
        packageMap.put("srcmvnpackage", new SourceMavenPackage());
        packageMap.put("testmvnpackage", new TestMavenPackage());
        packageMap.put("rscmvnpackage", new ResourceMavenPackage());
        packageMap.put("extendsrcmvnpackage", new ExtendSourceMavenPackage());
        return INSTANCE;
    }

    public Package getPackage(String impl) {
        return packageMap.get(impl);
    }

}
