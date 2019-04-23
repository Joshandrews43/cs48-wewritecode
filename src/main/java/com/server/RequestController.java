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
    public JsonObject updateQuarters(@RequestBody JsonObject quarters) {

        try {
            System.out.println("Update request for: Spring 2019");
            JsonObject obj = new JsonObject();
            JsonReader reader = new JsonReader(new FileReader(DATA_DIR + "general.json"));
            JsonParser parser = new JsonParser();
            Object object = parser.parse(reader);
            obj = (JsonObject) object;
            obj = (JsonObject) obj.get("quarters");


            if (obj.get("Spring 2019").getAsInt() > quarters.get("Spring 2019").getAsInt()) {
                    reader = new JsonReader(new FileReader(DATA_DIR + "spring2019.json"));
                    parser = new JsonParser();
                    object = parser.parse(reader);
                    obj = (JsonObject) object;
                    System.out.println("Outdated. Sending updated data");
                    return obj;

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return new JsonObject();
        }
        System.out.println("Quarter is up to date");
        return new JsonObject();
    }

}
