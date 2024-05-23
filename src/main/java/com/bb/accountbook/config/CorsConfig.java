package com.bb.accountbook.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {

    private final CorsAllowedProperties corsAllowedProperties;

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        corsAllowedProperties.getOrigins().forEach(config::addAllowedOrigin);
        corsAllowedProperties.getHeaders().forEach(config::addAllowedHeader);
        config.addAllowedMethod("*");
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean<CorsFilter> filterRegistrationBean = new FilterRegistrationBean<>(new CorsFilter(source));
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return filterRegistrationBean;
    }

}
