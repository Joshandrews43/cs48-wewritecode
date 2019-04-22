package com.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

@RestController
public class RequestController {

    private static final String DATA_DIR = System.getProperty("user.dir") + "/data/";
    @RequestMapping(method= RequestMethod.POST, value="/get")
    public JsonObject getQuarter(@RequestBody JsonObject quarter) {
        JsonObject obj = new JsonObject();
        JsonReader read;
        try {
            JsonReader reader = new JsonReader(new FileReader(DATA_DIR + "general.json"));
            Gson gson = new Gson();
            reader.
            gson.fromJson(reader, obj);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        Gson gson = new Gson();
        read = reader;
        obj = reader;
        obj.addProperty("lastUpdated", 100);
        System.out.println(quarter.toString());
        System.out.println(obj.toString());
        if (obj.get("lastUpdated").getAsInt() < quarter.get("lastUpdated").getAsInt()) {
            return obj;
        }
        return new JsonObject();
    }
}
