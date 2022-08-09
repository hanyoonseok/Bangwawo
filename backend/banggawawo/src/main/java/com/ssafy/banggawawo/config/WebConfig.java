package com.ssafy.banggawawo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public  void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedOriginPatterns("*")
                .maxAge(3000);
    }

    //class_thumbnail을 위한 resource 접근허용

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/class_thumbnail/**")
                .addResourceLocations("classpath:/class_thumbnail/")
                .setCachePeriod(20);
    }
}
