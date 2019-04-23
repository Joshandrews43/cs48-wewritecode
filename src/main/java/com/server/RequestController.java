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
public class RequestController {

    private static final String DATA_DIR = System.getProperty("user.dir") + "/data/";



    @RequestMapping(method= RequestMethod.POST, value="/get")
    public JsonObject getQuarter(@RequestBody JsonObject quarter) {
        JsonObject obj = new JsonObject();
        try {
            JsonReader reader = new JsonReader(new FileReader(DATA_DIR + "general.json"));
            JsonParser parser = new JsonParser();
            Object object = parser.parse(reader);
            obj = (JsonObject) object;
            obj = (JsonObject) obj.get("quarters");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        if (obj.get("Spring 2019").getAsInt() > quarter.get("lastUpdated").getAsInt()) {
            try {
                JsonReader reader = new JsonReader(new FileReader(DATA_DIR + "Spring2019.json"));
                JsonParser parser = new JsonParser();
                Object object = parser.parse(reader);
                obj = (JsonObject) object;
                return obj;
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        }
        return new JsonObject();
    }
}
