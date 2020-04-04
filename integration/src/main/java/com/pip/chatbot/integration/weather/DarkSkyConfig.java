package com.pip.chatbot.integration.weather;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "application.darksky")
@Data
public class DarkSkyConfig {
    private String language;
    private String unitsType;
    private String key;
}
