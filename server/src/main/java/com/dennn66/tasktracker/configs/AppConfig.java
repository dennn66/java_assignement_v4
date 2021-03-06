package com.dennn66.tasktracker.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
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
        //registry.addMapping("/**");  // работает получение токена, getAll не работает
        //registry.addMapping("/**").allowedOrigins("http://localhost"); // работает получение токена, getAll не работает
        registry.addMapping("/**").allowedOrigins("http://localhost:8888").allowedMethods("POST", "DELETE", "PUT", "GET"); // работает получение токена, getAll не работает
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
