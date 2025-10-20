// src/main/java/com/ethsoft/mydatn/config/WebConfig.java
package com.ethsoft.mydatn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Ví dụ: /datn/uploads/sanpham
    @Value("${app.upload.root-abs}")
    private String uploadRootAbs;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map: /uploads/sanpham/** -> file:/datn/uploads/sanpham/
        String location = "file:" + (uploadRootAbs.endsWith("/") ? uploadRootAbs : uploadRootAbs + "/");
        registry.addResourceHandler("/uploads/sanpham/**")
                .addResourceLocations(location)
                .setCachePeriod(0);
    }
}
