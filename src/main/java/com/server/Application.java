package com.server;

import com.wewritecode.GoldMineCLI;
import com.wewritecode.GoldMiner;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class Application {

    private static final Logger LOGGER = Logger.getLogger(GoldMineCLI.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        // Get most recent quarter.
        GoldMiner miner = new GoldMiner();
        String quarter = miner.getQuartersAsList().get(0);
        miner.close();

        try {
            GoldMineCLI.run(quarter);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Exception thrown in main GoldMineCLI Application");
            LOGGER.error(e.getMessage());
        }
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }
}

