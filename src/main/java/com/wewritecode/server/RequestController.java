package com.wewritecode.server;

import com.google.gson.*;
import com.wewritecode.server.manager.ScheduleManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RestController
public class RequestController {

    private static final String DATA_DIR = System.getProperty("user.dir") + "/data/";

    @PostMapping(value = "/generateSchedules")
    public JsonObject scheduleRequest(@RequestBody JsonObject requestedClasses) {
        System.out.println(requestedClasses);

        JSONObject request = gsonToJson(requestedClasses);
        JSONObject response = ScheduleManager.getInstance().generateSchedules(request);

        return jsonToGson(response);
    }

    @PostMapping(value = "/getUpdated")
    public JsonObject updateRequest(@RequestBody JsonObject quartersRequest) {
        System.out.println("\nQuarters update request");

        JSONObject request = gsonToJson(quartersRequest);
        JSONObject response = new JSONObject();
        JSONObject general = parseFromDataDir("general.json");

        JSONArray serverQuarters;
        JSONArray clientQuarters;

        response.put("general", general);

        serverQuarters = general.getJSONArray("quarters");
        clientQuarters = request.getJSONArray("quarters");

        response = updateQuarters(serverQuarters, clientQuarters, response);

        return jsonToGson(response);
    }

    private JSONObject updateQuarters(JSONArray serverQuarters, JSONArray clientQuarters, JSONObject response) {
        JSONObject clientQuarter, serverQuarter;
        String clientQuarterStr, serverQuarterStr;

        for (int i = 0; i < serverQuarters.length(); i++) {
            serverQuarter = serverQuarters.getJSONObject(i);
            serverQuarterStr = serverQuarter.getString("quarter");
            for (int j = 0; j < clientQuarters.length(); j++) {
                clientQuarter = clientQuarters.getJSONObject(j);
                clientQuarterStr = clientQuarter.getString("quarter");
                boolean sameQuarter = clientQuarterStr.equals(serverQuarterStr);

                if (sameQuarter) {
                    if (isOutdated(serverQuarter, clientQuarter)) {
                        System.out.println(clientQuarterStr + " is outdated.");
                        response.put(serverQuarterStr, updateQuarter(serverQuarter));
                    } else {
                        System.out.println(clientQuarterStr + " is up to date");
                    }
                    break;
                } else if (j + 1 == clientQuarter.length()) {
                    System.out.println(serverQuarterStr + " not found on client.");
                    response.put(serverQuarterStr, updateQuarter(serverQuarter));
                }
            }
        }

        return response;
    }

    private JSONObject updateQuarter(JSONObject quarter) {
        System.out.println("Updating " + quarter.getString("quarter"));

        String quarterName = quarter.getString("quarter");
        String year = quarterName.substring(quarterName.length() - 4);
        quarterName = quarterName.replaceAll("\\s+", "").toLowerCase();

        String path = year + "/" + quarterName + ".json";

        JSONObject updatedQuarter = parseFromDataDir(path);

        return updatedQuarter;
    }

    private JSONObject parseFromDataDir(String path) {
        InputStream is;

        try {
            is = new FileInputStream(DATA_DIR + path);
        } catch (FileNotFoundException e) {
            System.out.printf("File \"%s\" not found.", path);
            return new JSONObject();
        }

        JSONTokener tokener = new JSONTokener(is);
        JSONObject output = new JSONObject(tokener);

        return output;
    }

    private boolean isOutdated(JSONObject serverQuarter, JSONObject clientQuarter) {
        return serverQuarter.getLong("lastUpdated") > clientQuarter.getLong("lastUpdated");
    }

    private static JsonObject jsonToGson(JSONObject json) {
        Gson gson = new Gson();
        return gson.fromJson(json.toString(), JsonObject.class);
    }

    private static JSONObject gsonToJson(JsonObject gson) {
        return new JSONObject(gson.toString());
    }
}