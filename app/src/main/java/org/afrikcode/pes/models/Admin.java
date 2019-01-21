package org.afrikcode.pes.models;

import java.util.HashMap;
import java.util.Map;

public class Admin {

    private String id;

    public Admin() {
    }

    public Map<String, Object> datatoMap() {
        Map<String, Object> data = new HashMap<>();
        if (id != null) {
            data.put("id", getId());
        }
        return data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
