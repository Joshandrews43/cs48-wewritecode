/**
 * @author Grant Clark
 */

package com.wewritecode.application.server.controller;

import com.wewritecode.application.server.response.GeneralResponseHelper;
import com.wewritecode.application.server.request.GeneralRequest;
import com.wewritecode.application.server.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneralController {

    @Autowired
    private GeneralResponseHelper vault;

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
