package com.embraiz.server.config;

import com.embraiz.entity.common.Constant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 配置静态访问资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = Constant.getRootPath();
        registry.addResourceHandler(Constant.File_Url_Path + "**").addResourceLocations("file:" + path + Constant.File_Path);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedHeaders("*").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedOrigins("*");
    }
}
