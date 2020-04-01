package com.pip.chatbot.integration.weather;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="application.dark-sky")
public class DarkSkyConfig {
    private String language;
    private String unitsType;
    private String key;

    public void setLanguage(String language){
        this.language = language;
    }
    public void setUnitsType(String unitsType){
        this.unitsType = unitsType;
    }
    public void setKey(String key){
        this.key = key;
    }
    public String getLanguage(){
        return language;
    }
    public String getUnitsType(){
        return unitsType;
    }
    public String getKey(){
        return key;
    }
}
