package com.example.DBMS;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // exposeDirectory("data", registry);
        // exposeDirectory("table", registry);
        exposeDirectory("user-photos", registry);
        exposeDirectory("test-photos", registry);

        exposeDirectory("medicine-photos", registry);
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }   
    public void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();
        // System.out.println(uploadPath);
        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");
        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:" + uploadPath + "/");
        // registry.addResourceHandler("/" + "pics" + "/**").addResourceLocations("file:/" + uploadPath + "/");
        
    }   
}