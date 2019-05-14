/**
 * @author Grant Clark
 */

package com.wewritecode.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public final class JSONUtils {
    private static final Logger LOGGER = Logger.getLogger(JSONUtils.class);
    private static final String DATA_DIR = System.getProperty("user.dir") + "/data/";

    public static JSONObject getFromDataDir(String path) {
        InputStream is;

        try {
            is = new FileInputStream(DATA_DIR + path);
        } catch (FileNotFoundException e) {
            LOGGER.error(String.format("File \"%s\" not found.", path), e);
            return new JSONObject();
        }

        JSONTokener tokener = new JSONTokener(is);
        JSONObject output = new JSONObject(tokener);

        return output;
    }

    public static JsonObject jsonToGson(JSONObject json) {
        Gson gson = new Gson();
        return gson.fromJson(json.toString(), JsonObject.class);
    }

    public static JSONObject gsonToJson(JsonObject gson) {
        return new JSONObject(gson.toString());
    }
}
