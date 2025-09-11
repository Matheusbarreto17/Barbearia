package com.wmdigital.barbearia.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class StaticResourceConfig implements WebMvcConfigurer  {
	
	private final String baseDir;

    public StaticResourceConfig(@Value("${storage.base-dir:uploads}") String baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(System.getProperty("user.dir"), baseDir).toAbsolutePath().normalize();
        String location = uploadDir.toUri().toString(); // file:///.../uploads/

        registry.addResourceHandler("/files/**")
                .addResourceLocations(location)
                .setCachePeriod(3600)
                .resourceChain(true);
    }

}
