/**
 * @author Grant Clark
 */

package com.wewritecode.server.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wewritecode.common.full.FullQuarter;
import com.wewritecode.common.general.General;
import com.wewritecode.common.general.GeneralQuarter;
import com.wewritecode.server.request.GeneralRequest;
import com.wewritecode.server.response.GeneralResponse;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GeneralResponseHelper {
    private static final String DATA_DIR = System.getProperty("user.dir")+"/data/";

    private ObjectMapper mapper = new ObjectMapper();

    public GeneralResponse getResponse() {
        return getResponse(new GeneralRequest());
    }

    public GeneralResponse getResponse(GeneralRequest request) {
        GeneralResponse response = new GeneralResponse();
        File generalJson = new File(DATA_DIR + "general.json");

        General serverGeneral;
        try {
            serverGeneral = mapper.readValue(generalJson, General.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new GeneralResponse();
        }

        response.setGeneral(serverGeneral);
        response.setQuarters(findQuartersToUpdate(serverGeneral, request.getGeneral()));

        return response;
    }

    private Map<String, FullQuarter> findQuartersToUpdate(General server, General client) {
        Map<String, FullQuarter> updatedQuarters = new HashMap<>();

        List<GeneralQuarter> serverQuarters = server.getQuarters();
        List<GeneralQuarter> clientQuarters = client.getQuarters();

        for (GeneralQuarter serverQuarter : serverQuarters) {
            String serverQuarterName = serverQuarter.getQuarter();
            boolean addedQuarter = false;
            for (GeneralQuarter clientQuarter : clientQuarters) {
                String clientQuarterName = clientQuarter.getQuarter();
                boolean sameQuarter = serverQuarterName.equals(clientQuarterName);

                if (sameQuarter) {
                    if (isOutdated(serverQuarter, clientQuarter)) {
                        updatedQuarters.put(serverQuarterName, updateQuarter(serverQuarter));
                        addedQuarter = true;
                    }
                    break;
                } else if (clientQuarters.indexOf(clientQuarter) + 1 == clientQuarters.size()) {
                    updatedQuarters.put(serverQuarterName, updateQuarter(serverQuarter));
                    addedQuarter = true;
                }
            }
            if (!addedQuarter)
                updatedQuarters.put(serverQuarterName, updateQuarter(serverQuarter));
        }
        return updatedQuarters;
    }

    private FullQuarter updateQuarter(GeneralQuarter server) {
        String quarterName = server.getQuarter().toLowerCase();
        quarterName = quarterName.replaceAll("\\s", "");
        String year = quarterName.substring(quarterName.length() - 4);
        String path = String.format("%s/%s.json", year, quarterName);

        File quarterJson = new File(DATA_DIR + path);

        FullQuarter quarter = null;
        try {
            quarter = mapper.readValue(quarterJson, FullQuarter.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return quarter;
    }

    private boolean isOutdated(GeneralQuarter server, GeneralQuarter client) {
        return server.compareTo(client) > 0;
    }
}
