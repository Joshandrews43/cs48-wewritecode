/**
 * @author Grant Clark
 */

package com.wewritecode.util;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A simple timer class to display the length of a given method execution call.
 */
public class MethodTimer {
    private long startTime;
    private String method, object;

    private static final Logger LOGGER = Logger.getLogger(MethodTimer.class);

    public MethodTimer(String method) {
        this.method = method;
        this.object = null;
        start();
    }

    public MethodTimer(String method, String object) {
        this.method = method;
        this.object = object;
        start();
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void end() {
        long endTime = System.currentTimeMillis();
        long elapsed = endTime - startTime;

        String runTime = String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(elapsed),
                TimeUnit.MILLISECONDS.toSeconds(elapsed)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsed))
        );

        LOGGER.info("Method: " + method + " took " + runTime + " for: " + object);

        add(method, elapsed);
    }


    private static synchronized void add(String method, long elapsed) {
        Long runtime = methodRuntimeMap.get(method);
        Integer executions = methodExecMap.get(method);

        if (runtime != null && executions != null) {
            methodRuntimeMap.put(method, runtime + elapsed);
            methodExecMap.put(method, ++executions);
        } else {
            methodRuntimeMap.put(method, elapsed);
            methodExecMap.put(method, 1);
        }
    }

    public static void printStats() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n**************************************************\n");
        sb.append("Printing Method Stats for GoldMiner Utility\n");
        sb.append("**************************************************\n");
        String menu = String.format("%-15s%-15s%-15s\n", "Method", "Executions", "AvgRuntime");
        sb.append(menu);
        for (String method : methodRuntimeMap.keySet()) {
            Long runtime = methodRuntimeMap.get(method);
            Integer executions = methodExecMap.get(method);
            Long avgRuntime = (runtime / executions);

            String runTime = String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(avgRuntime),
                    TimeUnit.MILLISECONDS.toSeconds(avgRuntime)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(avgRuntime))
            );

            String data = String.format("%-15s%-15s%-15s\n", method, executions, runTime);
            sb.append(data);
        }
        sb.append("**************************************************\n");
        String print = sb.toString();
        LOGGER.debug(print);
    }


    private static Map<String, Integer> methodExecMap = new HashMap<>();
    private static Map<String, Long> methodRuntimeMap = new HashMap<>();
}
