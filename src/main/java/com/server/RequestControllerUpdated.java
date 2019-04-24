package com.server;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

@RestController
public class RequestControllerUpdated {

    private static final String DATA_DIR = System.getProperty("user.dir") + "/data/";



//    @RequestMapping(method= RequestMethod.POST, value="/getUpdated")
//    public JsonObject updateQuarters(@RequestBody JsonObject quartersRequest) {
//        System.out.println("Quarters update request:\n");
//        JsonObject response = new JsonObject();
//        try {
//            JsonReader reader = new JsonReader(new FileReader(DATA_DIR + "general.json"));
//            JsonParser parser = new JsonParser();
//            Object object = parser.parse(reader);
//            JsonObject general = (JsonObject) object;
//            response.add("general", (JsonElement) general);
//            JsonArray serverQuarters = (JsonArray) general.get("quarters");
//            JsonArray clientQuarters = (JsonArray) quartersRequest.get("quarters");
//            JsonObject clientQuarter;
//            JsonObject serverQuarter;
//
//
//            for (int i = 0; i < clientQuarters.size(); i++) {
//                clientQuarter = (JsonObject) clientQuarters.get(i);
//                serverQuarter = (JsonObject) serverQuarters.get(i);
//                System.out.println("Update request for: " + clientQuarter.get("quarter").getAsString());
//                if (serverQuarter.get("lastUpdated").getAsInt() > clientQuarter.get("lastUpdated").getAsInt()) {
//                    System.out.println(clientQuarter.get("quarter").getAsString() + " is outdated.");
//                    response.add(serverQuarter.get("quarter").getAsString(), (JsonElement) getQuarter(serverQuarter));
//                } else {
//                    System.out.println(clientQuarter.get("quarter").getAsString() + " is up to date");
//                }
//            }
//
//            if (serverQuarters.size() != clientQuarters.size()) {
//                for (int i = clientQuarters.size(); i < serverQuarters.size(); i++) {
//                    serverQuarter = (JsonObject) serverQuarters.get(i);
//                    System.out.println(serverQuarter.get("quarter").getAsString() + " not found on client.");
//                    response.add(serverQuarter.get("quarter").getAsString(), (JsonElement) getQuarter(serverQuarter));
//                }
//            }
//
//        } catch (FileNotFoundException e) {
//            System.out.println("general.json file not found");
//            return new JsonObject();
//        }
//        return response;
//
//    }

    @RequestMapping(method= RequestMethod.POST, value="/getUpdated")
    public JsonObject updateQuarters(@RequestBody JsonObject quartersRequest) {
        System.out.println("Quarters update request:\n");
        JsonObject response = new JsonObject();
        try {
            JsonReader reader = new JsonReader(new FileReader(DATA_DIR + "general.json"));
            JsonParser parser = new JsonParser();
            Object object = parser.parse(reader);
            JsonObject general = (JsonObject) object;
            response.add("general", (JsonElement) general);
            JsonArray serverQuarters = (JsonArray) general.get("quarters");
            JsonArray clientQuarters = (JsonArray) quartersRequest.get("quarters");
            JsonObject clientQuarter;
            JsonObject serverQuarter;


            for (int i = 0; i < serverQuarters.size(); i++) {
                serverQuarter = (JsonObject) serverQuarters.get(i);
                for (int j = 0; j < clientQuarters.size(); j++) {
                    clientQuarter = (JsonObject) clientQuarters.get(j);
                    if (serverQuarter.get("quarter").equals(clientQuarter.get("quarter")) &&
                        serverQuarter.get("lastUpdated").getAsInt() > clientQuarter.get("lastUpdated").getAsInt()) {
                        System.out.println(clientQuarter.get("quarter").getAsString() + " is outdated.");
                        response.add(serverQuarter.get("quarter").getAsString(),
                                (JsonElement) getQuarter(serverQuarter));
                        break;
                    } else if (serverQuarter.get("quarter").equals(clientQuarter.get("quarter")) &&
                            !(serverQuarter.get("lastUpdated").getAsInt() > clientQuarter.get("lastUpdated").getAsInt())) {
                        System.out.println(clientQuarter.get("quarter").getAsString() + " is up to date");
                        break;
                    } else if (j+1 == clientQuarters.size()) {
                        System.out.println(serverQuarter.get("quarter").getAsString() + " not found on client.");
                        response.add(serverQuarter.get("quarter").getAsString(), (JsonElement) getQuarter(serverQuarter));
                    }
                }


            }

        } catch (FileNotFoundException e) {
            System.out.println("general.json file not found");
            return new JsonObject();
        }
        return response;

    }


    public JsonObject getQuarter(JsonObject quarter) {
        try {
            String quarterName = quarter.get("quarter").getAsString();
            quarterName = quarterName.replaceAll("\\s+", "");
            quarterName = quarterName.toLowerCase();
            JsonReader reader = new JsonReader(new FileReader(DATA_DIR + quarterName + ".json"));
            JsonParser parser = new JsonParser();
            Object object = parser.parse(reader);
            JsonObject obj = (JsonObject) object;
            System.out.println("Updating " + quarter.get("quarter").getAsString());
            return obj;


        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return new JsonObject();
        }
    }
}