package com.example.demo;
import com.example.demo.infrastructure.adapters.KafkaProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.cloud.stream.annotation.EnableBinding;


@SpringBootApplication
@EnableBinding(KafkaProcessor.class)
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    public static ApplicationContext applicationContext;
    public static void main(String[] args) {
        logger.info("##### Application Start #####");
        applicationContext = SpringApplication.run(Application.class, args);
    }
}



