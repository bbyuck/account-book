package com.bb.accountbook.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Data
@Configuration
@ConfigurationProperties(prefix = "cors.allowed")
public class CorsAllowedProperties {
    private Set<String> origins;
    private Set<String> headers;
}
