package com.ethsoft.mydatn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload.root-dir}")
    private String uploadRootDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map: /uploads/sanpham/** -> file:uploads/sanpham/
        String location = "file:" + (uploadRootDir.endsWith("/") ? uploadRootDir : uploadRootDir + "/");
        registry.addResourceHandler("/uploads/sanpham/**")
                .addResourceLocations(location)
                .setCachePeriod(0);
    }
}
