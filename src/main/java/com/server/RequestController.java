package com.server;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
public class RequestController {


//    @RequestMapping(method= RequestMethod.POST, value="/get")
//    public Map<String,Object> getQuarter(@RequestBody Map<String, Object> quarter) {
//        Map<String, Object> obj = new HashMap<String, Object>() {{
//            put("lastUpdated", "test");
//        }};
//        System.out.println(quarter.toString());
//        System.out.println(obj.toString());
////        Object num2 = obj.get("lastUpdated");
////        Object num1 = quarter.get("lastUpdated");
////        if (num2.equals(num1)) {
////            return new JSONObject();
////        }
//        return obj;
//    }

    @RequestMapping(method= RequestMethod.POST, value="/get")
    public JsonObject getQuarter(@RequestBody JsonObject quarter) {
        JsonObject obj = new JsonObject();
        obj.addProperty("lastUpdated", 100);
        System.out.println(quarter.toString());
        System.out.println(obj.toString());
        if (obj.get("lastUpdated").getAsInt() < quarter.get("lastUpdated").getAsInt()) {
            return obj;
        }
        return new JsonObject();
    }
}
