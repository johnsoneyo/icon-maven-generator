/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lbt.icon.artifact.plugin.exception;

/**
 *
 * @author johnson3yo
 */
public class IconArtifactException  extends Exception{
    private String messsage ;
    
    public IconArtifactException(String message){
        this.messsage = message;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }
    
    
}
