
/**
 * @author Alan Roddick
 */

package com.wewritecode.server;

        import com.google.gson.Gson;
        import com.google.gson.JsonObject;
        import org.json.JSONArray;
        import org.json.JSONObject;
        import org.json.JSONTokener;

        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.InputStream;

public class UpdateController {

    private static final String DATA_DIR = System.getProperty("user.dir") + "/data/";

    public static JSONObject updateJson(JSONObject clientGeneral) {

        // Get general.json
        JSONObject response = new JSONObject();
        JSONObject serverGeneral = parseFromDataDir("general.json");
        response.put("general", serverGeneral);

        // Add new quarters if outdated
        updateQuarters(serverGeneral, clientGeneral, response);

        return response;
    }

    public static void updateQuarters(JSONObject serverGeneral, JSONObject clientGeneral, JSONObject response) {

        JSONArray serverQuarters = serverGeneral.getJSONArray("quarters");
        JSONArray clientQuarters = clientGeneral.getJSONArray("quarters");

        JSONObject clientQuarter, serverQuarter;
        String clientQuarterStr, serverQuarterStr;

        // Check if quarters are updated, and add if not
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
                        response.put(serverQuarterStr, getQuarter(serverQuarter));
                    } else {
                        System.out.println(clientQuarterStr + " is up to date");
                    }
                    break;
                } else if (j + 1 == clientQuarters.length()) {
                    System.out.println(serverQuarterStr + " not found on client.");
                    response.put(serverQuarterStr, getQuarter(serverQuarter));
                }
            }
        }
    }

    public static JSONObject getQuarter(JSONObject quarter) {
        System.out.println("Updating " + quarter.getString("quarter"));

        String quarterName = quarter.getString("quarter");
        String year = quarterName.substring(quarterName.length() - 4);
        quarterName = quarterName.replaceAll("\\s+", "").toLowerCase();

        JSONObject updatedQuarter = parseFromDataDir(year + "/" + quarterName + ".json");

        return updatedQuarter;
    }

    public static JSONObject parseFromDataDir(String fileName) {
        InputStream is;

        try {
            is = new FileInputStream(DATA_DIR + fileName);
        } catch (FileNotFoundException e) {
            System.out.printf("File \"%s\" not found.", fileName);
            return new JSONObject();
        }

        JSONTokener tokener = new JSONTokener(is);
        JSONObject output = new JSONObject(tokener);

        return output;
    }

    public static boolean isOutdated(JSONObject serverQuarter, JSONObject clientQuarter) {
        return serverQuarter.getLong("lastUpdated") > clientQuarter.getLong("lastUpdated");
    }

    public static JsonObject jsonToGson(JSONObject json) {
        Gson gson = new Gson();
        return gson.fromJson(json.toString(), JsonObject.class);
    }

    public static JSONObject gsonToJson(JsonObject gson) {
        return new JSONObject(gson.toString());
    }
}
