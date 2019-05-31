/**
 * @author Grant Clark
 */

package com.wewritecode.server.controller;

import com.wewritecode.miner.GoldMineCLI;
import com.wewritecode.miner.GoldMiner;
import com.wewritecode.server.request.ScrapeRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class MinerController {

    @PutMapping(path = "/api/v1/scrape", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void scrape(@RequestBody ScrapeRequest request) {
        try {
            GoldMineCLI.run(request.getQuarter());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @PutMapping(path = "/api/v1/scrape/demo")
    public void scrapeDemo(@RequestBody ScrapeRequest request) {
        GoldMiner miner = new GoldMiner();
        miner.getAllData(request.getQuarter());
    }

}
