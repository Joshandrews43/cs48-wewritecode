/**
 * @author Grant Clark
 */

package com.wewritecode.miner;

import com.wewritecode.util.MethodTimer;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class GoldMineController {

    private static final Logger LOGGER = Logger.getLogger(GoldMineController.class);

    public JSONObject mine(String quarter) throws InterruptedException, ExecutionException {
        MethodTimer timer = new MethodTimer("mine");

        LOGGER.info("Starting multi-threaded scrape.");

        JSONObject fullQuarterScrape = new JSONObject();

        GoldMiner basicMiner = new GoldMiner();
        int threads = Runtime.getRuntime().availableProcessors();
        LOGGER.info("Creating " + threads + " available threads.");
        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        // Get all the available subject symbols
        List<String> subjects = basicMiner.getSubjectsAsList();

        // Close the basic miner (it's completed it's purpose)
        basicMiner.close();

        List<Future<JSONObject>> futures = new ArrayList<>();
        for (String subject : subjects) {
            LOGGER.debug("Creating new task for: " + subject + ".");

            Callable<JSONObject> task = () -> {
                GoldMiner miner = new GoldMiner();
                JSONObject extSubject = new JSONObject();

                String symbol = subject.substring(subject.indexOf("-")+2);

                JSONObject subjectObj = miner.getAllCoursesFromSubject(subject, quarter);
                extSubject.put(symbol, subjectObj);

                miner.close();

                return extSubject;
            };

            futures.add(executorService.submit(task));
        }

        for (Future<JSONObject> future : futures) {
            JSONObject extSubject = future.get();
            String symbol = extSubject.names().getString(0);
            JSONObject subject = extSubject.getJSONObject(symbol);
            LOGGER.debug("Scraped " + symbol + " courses.");

            fullQuarterScrape.put(symbol, subject);
        }

        // Shut down executor service.
        // Both methods recommended by Oracle.
        LOGGER.info("Shutting down the executor service.");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }

        timer.end();

        return fullQuarterScrape;
    }
}
