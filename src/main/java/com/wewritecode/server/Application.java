/**
 * @author Alan Roddick
 */
package com.wewritecode.server;

import com.wewritecode.miner.GoldMineCLI;
import com.wewritecode.miner.GoldMiner;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class Application {

    private static final Logger LOGGER = Logger.getLogger(GoldMineCLI.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

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

