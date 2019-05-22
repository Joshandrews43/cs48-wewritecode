/**
 * @author Grant Clark
 */

package com.wewritecode.miner;

import com.wewritecode.util.JSONUtils;
import com.wewritecode.util.MethodTimer;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class GoldMineCLI {
    private static final String DATA_DIR = System.getProperty("user.dir")+"/data/";
    private static final Logger LOGGER = Logger.getLogger(GoldMineCLI.class);

    public static void run(String quarter) throws ExecutionException, InterruptedException {
        LOGGER.info("Beginning scraping program.");

        GoldMineController controller = new GoldMineController();

        LOGGER.info("Beginning scraping " + quarter);
        JSONObject fullQuarter = controller.mine(quarter);
        LOGGER.info("Scraping " + quarter + " complete.");

        quarter = quarter.toLowerCase().replaceAll("\\s","");
        String year = quarter.substring(quarter.length() - 4);
        File quarterFile = new File(DATA_DIR + year + "/" + quarter +".json");
        File quarterFileFormatted = new File(DATA_DIR + quarter +"_formatted.json");

        JSONUtils.toJsonFile(fullQuarter, quarterFile, false);
        JSONUtils.toJsonFile(fullQuarter, quarterFileFormatted, true);

        LOGGER.info("Scraping complete.");

        MethodTimer.printStats();
    }
}
