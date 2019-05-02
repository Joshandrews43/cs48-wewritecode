package com.wewritecode.server;

import com.google.gson.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * RequestController to handle POST requests for generating schedules and updating quarters
 *
 * @author  Alan Roddick
 * @version 1.1
 * @since   2019-04-24
 */

@RestController
public class RequestController {

    @RequestMapping(method = RequestMethod.POST, value = "/generateSchedules")
    public JsonObject generateSchedule(@RequestBody JsonObject requestedClasses) {
        System.out.println(requestedClasses);
//        JSONObject request = gsonToJson(requestedClasses);
//        JSONObject response = new JSONObject();
//        return jsonToGson(response);
        return requestedClasses;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getUpdated")
    public JsonObject updateRequest(@RequestBody JsonObject request) {
        System.out.println("\nQuarters update request");

        JSONObject clientGeneral = gsonToJson(request);
        return jsonToGson(UpdateController.updateJson(clientGeneral));
    }


    // Methods to convert JSON to Gson and vice versa
    private static JsonObject jsonToGson(JSONObject json) {
        Gson gson = new Gson();
        return gson.fromJson(json.toString(), JsonObject.class);
    }

    private static JSONObject gsonToJson(JsonObject gson) {
        return new JSONObject(gson.toString());
    }



}