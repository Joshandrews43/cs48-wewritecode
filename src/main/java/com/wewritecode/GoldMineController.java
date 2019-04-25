/**
 * @author Grant Clark
 */

package com.wewritecode;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class GoldMineController {
    public JSONObject mine(String quarter) throws InterruptedException, ExecutionException {
        JSONObject fullQuarterScrape = new JSONObject();

        GoldMiner basicMiner = new GoldMiner();
//        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        CompletionService<JSONObject> ecs = new ExecutorCompletionService<>(executorService);

        // Get all the available subject symbols
        List<String> symbols = basicMiner.getSubjectSymbols();

//        List<Callable<JSONObject>> callables = new ArrayList<>();
        List<Future<JSONObject>> futures = new ArrayList<>();
        for (String symbol : symbols) {

            Callable<JSONObject> task = () -> {
                GoldMiner miner = new GoldMiner();
                JSONObject extSubject = new JSONObject();

                JSONObject subject = miner.getAllCoursesFromSubject(symbol, quarter);
                extSubject.put(symbol, subject);

                miner.close();

                return extSubject;
            };

            ecs.submit(task);
//            callables.add(task);
        }

        for (int i = 0; i < symbols.size(); ++i) {
            JSONObject extSubject = ecs.take().get();
            String symbol = extSubject.names().getString(0);
            JSONObject subject = extSubject.getJSONObject(symbol);

            fullQuarterScrape.put(symbol, subject);
        }

//        try {
//            futures = executorService.invokeAll(callables);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        for (Future<JSONObject> future : futures) {
//            try {
//                JSONObject extSubject = future.get();
//
//                String symbol = extSubject.names().getString(0);
//                JSONObject subject = extSubject.getJSONObject(symbol);
//
//                fullQuarterScrape.put(symbol, subject);
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//        }

        // Shut down executor service.
        // Both methods recommended by Oracle.
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }

        

        return fullQuarterScrape;
    }
}
