package com.dennn66.tasktracker.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:private.properties")
@ComponentScan("com.dennn66.tasktracker")
@ComponentScan("com.dennn66.gwt.common")
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:8888", "http://127.0.0.1:8888").allowedMethods("POST", "DELETE", "PUT", "GET"); // работает получение токена, getAll не работает
    }
}
