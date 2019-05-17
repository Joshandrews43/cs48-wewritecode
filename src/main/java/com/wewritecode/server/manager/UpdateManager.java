/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.server.manager;

import com.wewritecode.util.JSONUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class UpdateManager implements RequestManager {
    private static final Logger LOGGER = Logger.getLogger(UpdateManager.class);
    private JSONObject general;
    private JSONArray serverQuarters;
    private JSONArray clientQuarters;

    // Construction

    private UpdateManager() {}

    private static class UpdateManagerHolder {
        private static final UpdateManager INSTANCE = new UpdateManager();
    }

    public static UpdateManager getInstance() { return UpdateManagerHolder.INSTANCE; }

    // Request Handlers

    @Override
    public void handleGet() {}

    @Override
    public void handlePost(JSONObject request) {
        general = JSONUtils.getFromDataDir("general.json");
        serverQuarters = general.getJSONArray("quarters");
        clientQuarters = request.getJSONArray("quarters");
    }

    @Override
    public void handlePut(JSONObject request) {}

    @Override
    public void handleDelete() {}

    // Core Methods

    public JSONObject updateQuarters(JSONObject request) {
        LOGGER.info("Update GeneralQuarter Requested");

        JSONObject clientQuarter, serverQuarter;
        String clientQuarterStr, serverQuarterStr;
        handlePost(request);

        JSONObject response = new JSONObject();
        response.put("general", general);

        for (int i = 0; i < serverQuarters.length(); i++) {
            serverQuarter = serverQuarters.getJSONObject(i);
            serverQuarterStr = serverQuarter.getString("quarter");

            for (int j = 0; j < clientQuarters.length(); j++) {
                clientQuarter = clientQuarters.getJSONObject(j);
                clientQuarterStr = clientQuarter.getString("quarter");
                boolean sameQuarter = clientQuarterStr.equals(serverQuarterStr);

                if (sameQuarter) {
                    if (isOutdated(serverQuarter, clientQuarter)) {
                        LOGGER.info(clientQuarterStr + " is out of date.\nUpdating " + clientQuarterStr);
                        response.put(serverQuarterStr, updateQuarter(serverQuarter));
                        LOGGER.info(clientQuarterStr + " finished updating.");
                    } else {
                        LOGGER.info(clientQuarterStr + " is up to date.");
                    }
                    break;
                } else if (j + 1 == clientQuarters.length()) {
                    LOGGER.info(serverQuarterStr + " not found on client.");
                    response.put(serverQuarterStr, updateQuarter(serverQuarter));
                }
            }
        }
        return response;
    }

    private JSONObject updateQuarter(JSONObject quarter) {
        String quarterName = quarter.getString("quarter").toLowerCase();
        quarterName = quarterName.replaceAll("\\s", "");
        String year = quarterName.substring(quarterName.length() - 4);
        String path = year + "/" + quarterName + ".json";

        return JSONUtils.getFromDataDir(path);
    }

    private boolean isOutdated(JSONObject serverQuarter, JSONObject clientQuarter) {
        return serverQuarter.getLong("lastUpdated") > clientQuarter.getLong("lastUpdated");
    }

}
