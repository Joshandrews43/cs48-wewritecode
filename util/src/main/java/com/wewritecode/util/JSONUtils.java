/**
 * @author Grant Clark
 */

package com.wewritecode.util;

import com.google.gson.*;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

public final class JSONUtils {
    private static final Logger LOGGER = Logger.getLogger(JSONUtils.class);
    private static final String DATA_DIR = System.getProperty("user.dir") + "/vault/";

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

    /**
     * Formats a JSON object nicely into a file output.
     *
     * @param jsonObject Object to convert to string and output into file (formatted nicely).
     * @param filename Target file name (existing or new) for the output.
     * @return True if it was able to write to the file, false if an IOException occurred.
     */
    public static boolean toJsonFile(JSONObject jsonObject, File filename, boolean doFormatting) {
        String writeString = jsonObject.toString();

        // Creates a new instance of Gson with "pretty printing" enabled.
        if (doFormatting) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(writeString);
            writeString = gson.toJson(je);
        }

        // Write to output file
        try {
            FileWriter file = new FileWriter(filename);
            file.write(writeString);
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
