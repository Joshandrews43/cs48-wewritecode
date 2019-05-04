/**
 * @author Grant Clark
 */

package com.wewritecode.miner;

import com.wewritecode.util.MethodTimer;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class GoldMineCLI {
    private static final String DATA_DIR = System.getProperty("user.dir")+"/data/2019/";
    private static final Logger LOGGER = Logger.getLogger(GoldMineCLI.class);

    public static void run(String quarter) throws ExecutionException, InterruptedException {
        LOGGER.info("Beginning scraping program.");

        GoldMineController controller = new GoldMineController();

        LOGGER.info("Beginning scraping " + quarter);
        JSONObject fullQuarter = controller.mine(quarter);
        LOGGER.info("Scraping " + quarter + " complete.");

        quarter = quarter.toLowerCase().replaceAll("\\s","");
        File quarterFile = new File(DATA_DIR + quarter +".json");
        File quarterFileFormatted = new File(DATA_DIR + quarter +"_formatted.json");

        GoldMiner.toJsonFile(fullQuarter, quarterFile, false);
        GoldMiner.toJsonFile(fullQuarter, quarterFileFormatted, true);

        LOGGER.info("Scraping complete.");

        MethodTimer.printStats();
    }
}
