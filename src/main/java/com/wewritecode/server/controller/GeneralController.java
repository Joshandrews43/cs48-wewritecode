/**
 * @author Grant Clark
 */

package com.wewritecode.server.controller;

import com.wewritecode.miner.GoldVault;
import com.wewritecode.server.request.GeneralRequest;
import com.wewritecode.server.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneralController {

    @Autowired
    private GoldVault vault;

    @GetMapping(path = "/api/v1/general", produces = MediaType.APPLICATION_JSON_VALUE)
    public GeneralResponse getGeneralInfo() {
        return vault.getResponse();
    }

    @PostMapping(path = "/api/v1/general/update",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GeneralResponse updateGeneralInfo(GeneralRequest request) {
        return vault.getResponse(request);
    }
}
