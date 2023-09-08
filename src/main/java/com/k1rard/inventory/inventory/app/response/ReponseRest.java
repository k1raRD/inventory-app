package com.k1rard.inventory.inventory.app.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReponseRest {
    private List<Map<String, String>> metadata = new ArrayList<>();

    public List<Map<String, String>> getMetadata() {
        return metadata;
    }

    public void setMetadata(String type, String code, String date) {
        Map<String, String> map = new HashMap<>();

        map.put("type", type);
        map.put("code",code);
        map.put("date", date);
        metadata.add(map);
    }
}
