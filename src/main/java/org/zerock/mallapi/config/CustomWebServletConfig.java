package org.zerock.mallapi.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zerock.mallapi.controller.advice.formatter.LocalDateFormatter;


@Configuration
@Log4j2
public class CustomWebServletConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {


        log.info("-----------");
        log.info("addformater");

        registry.addFormatter(new LocalDateFormatter());
    }
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .maxAge(500)
                .allowedMethods("GET","POST","PUT","DELETE","HEAD","OPTIONS")
                .allowedOrigins("*");

    }
}

