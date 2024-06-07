package com.jayuroun.jasosocket;

import com.jayuroun.jasosocket.config.MessagingConfig;
import com.jayuroun.jasosocket.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({
        CorsProperties.class,
        MessagingConfig.class
})
@SpringBootApplication
public class SpringSocketRestApi {

    public static void main(String[] args) {
        SpringApplication.run(SpringSocketRestApi.class, args);
    }

}
