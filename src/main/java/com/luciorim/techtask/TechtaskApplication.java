package com.luciorim.techtask;

import com.luciorim.techtask.service.ImageService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@Slf4j
public class TechtaskApplication implements WebMvcConfigurer {

    @Value("${application.delete-all-files}")
    private Boolean deleteAllFiles;


    public static void main(String[] args){
        SpringApplication.run(TechtaskApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ImageService imageService) {
        return (args) -> {
            if (deleteAllFiles)
                imageService.deleteAll();
            imageService.init();
            log.info("Successfully started");
        };
    }

}
