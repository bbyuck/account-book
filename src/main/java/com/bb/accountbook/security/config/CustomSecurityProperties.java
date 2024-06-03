package com.bb.accountbook.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.security")
public class CustomSecurityProperties {
    private Set<String> whiteList;
}
