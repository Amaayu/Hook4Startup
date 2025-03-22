package com.hook4startup.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProjectConfig {

    @Bean
    public Cloudinary getCloudinary(){

        Map config = new HashMap();
        config.put("cloud_name","dijzsv2tt");
        config.put("api_key","412351391672618");
        config.put("api_secret","R5PG5KnZQw1ntlpvHeJuYbspXzI");
        config.put("secure",true);
       return  new Cloudinary(config);
    }


}
