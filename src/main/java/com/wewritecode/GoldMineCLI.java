/**
 * @author Grant Clark
 */

package com.wewritecode;

import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GoldMineCLI {
    private static final String JSON_RESOURCE_DIR = System.getProperty("user.dir")+"/src/test/resources/jsons/";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();

        CommandLineParser clp = new CommandLineParser();
        String quarter = clp.parse(args);
        GoldMineController controller = new GoldMineController();
        JSONObject fullQuarter = controller.mine(quarter);

        quarter = quarter.toLowerCase().replaceAll("\\s","");
        File quarterFile = new File(JSON_RESOURCE_DIR + quarter +".json");
        GoldMiner.toJsonFile(fullQuarter, quarterFile);

        long endTime = System.currentTimeMillis();
        long elapsed = endTime - startTime;

        System.out.println(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(elapsed),
                TimeUnit.MILLISECONDS.toSeconds(elapsed)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsed))
        ));
    }
}
