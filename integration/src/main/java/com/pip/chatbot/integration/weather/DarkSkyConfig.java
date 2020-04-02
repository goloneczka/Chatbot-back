package com.pip.chatbot.integration.weather;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "application.darksky")
public class DarkSkyConfig {
    @Getter
    @Setter
    private String language;
    @Getter
    @Setter
    private String unitsType;
    @Getter
    @Setter
    private String key;
}
