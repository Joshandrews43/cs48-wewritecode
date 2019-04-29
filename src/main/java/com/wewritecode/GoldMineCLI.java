/**
 * @author Grant Clark
 */

package com.wewritecode;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GoldMineCLI {
    private static final String JSON_RESOURCE_DIR = System.getProperty("user.dir")+"/data/2019/";
    private static final Logger LOGGER = Logger.getLogger(GoldMineCLI.class);

    public static void run(String[] args) throws ExecutionException, InterruptedException {
        LOGGER.info("Beginning scraping program.");

        CommandLineParser clp = new CommandLineParser();
        String quarter = clp.parse(args);
        GoldMineController controller = new GoldMineController();

        LOGGER.info("Beginning scraping " + quarter);
        JSONObject fullQuarter = controller.mine(quarter);
        LOGGER.info("Scraping " + quarter + " complete.");

        quarter = quarter.toLowerCase().replaceAll("\\s","");
        File quarterFile = new File(JSON_RESOURCE_DIR + quarter +".json");
        GoldMiner.toJsonFile(fullQuarter, quarterFile);

        LOGGER.info("Scraping complete.");

        MethodTimer.printStats();
    }
}
