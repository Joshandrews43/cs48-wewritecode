/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.server.controller;

import com.google.gson.JsonObject;
import com.wewritecode.server.manager.UpdateManager;
import com.wewritecode.util.JSONUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateController {

    // TODO: Switch the RequestBody to an object wrapping List<Quarter>
    // TODO: Switch the Response to an object wrapping List<Quarter> and n - Quarter objects.
    @PostMapping("/getUpdated")
    public JsonObject updateRequest(@RequestBody JsonObject requestedQuarters) {
        JSONObject request = JSONUtils.gsonToJson(requestedQuarters);
        JSONObject response = UpdateManager.getInstance().updateQuarters(request);
        return JSONUtils.jsonToGson(response);
    }
}
