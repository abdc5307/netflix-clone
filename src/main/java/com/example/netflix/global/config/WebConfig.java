package com.example.netflix.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir:uploads/}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File directory = new File(uploadDir);
        String absolutePath = directory.getAbsolutePath() + File.separator;

        // /images/** 요청이 들어오면 uploads 폴더의 실제 파일로 연결
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + absolutePath);
    }
}