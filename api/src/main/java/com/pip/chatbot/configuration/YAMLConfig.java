package com.pip.chatbot.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Getter
@Setter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YAMLConfig {
    private HashMap<String, String> db;
}