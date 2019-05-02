/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lbt.icon.artifact.plugin.factory.pkg;

/**
 *
 * @author johnson3yo
 */
public class PathProperty {
    
    private String className;
    private String classPackage;
    private String classPath;

    public PathProperty(String className, String classPackage, String classPath) {
        this.className = className;
        this.classPackage = classPackage;
        this.classPath = classPath;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassPackage() {
        return classPackage;
    }

    public void setClassPackage(String classPackage) {
        this.classPackage = classPackage;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    @Override
    public String toString() {
        return "PathProperty{" + "className=" + className + ", classPackage=" + classPackage + ", classPath=" + classPath + '}';
    }
    
    
    
    
}
