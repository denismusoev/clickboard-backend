package com.coursework.clickboardbackend.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dionvmulc", // Замените на имя вашего аккаунта
                "api_key", "968638818284817",       // Замените на ваш API Key
                "api_secret", "jVnIGXlU2FP0RWyQtcl9kjX4r6c"  // Замените на ваш API Secret
        ));
    }
}

