/**
 * @author Alan Roddick
 */
package com.wewritecode.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wewritecode.miner.GoldMineCLI;
import com.wewritecode.miner.GoldMiner;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
@ComponentScan("com.wewritecode")
@EntityScan("com.wewritecode")
public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        // TODO: Refactor into some sort of Controller and/or function controlled by a Heroku script.
//         // Get most recent quarter.
//        GoldMiner miner = new GoldMiner();
//        String quarter = miner.getQuartersAsList().get(0);
//        miner.close();
//
//        try {
//            GoldMineCLI.run(quarter);
//        } catch (InterruptedException | ExecutionException e) {
//            LOGGER.error("Exception thrown in main GoldMineCLI Application");
//            LOGGER.error(e.getMessage());
//        }
    }
}

