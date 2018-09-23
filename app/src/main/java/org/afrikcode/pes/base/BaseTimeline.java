package org.afrikcode.pes.base;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseTimeline<T> {

    private String id;
    private String name;
    private boolean isActive;

    public BaseTimeline() {
    }

    public BaseTimeline(String name) {
        this.name = name;
        this.isActive = false;
    }

    public Map<String, Object> datatoMap() {
        Map<String, Object> data = new HashMap<>();

        // Ensuring that we don't pass null values which can override existing database data
        if (getId() != null) {
            data.put("id", id);
        }
        if (getName() != null) {
            data.put("name", name);
        }
        data.put("isActive", isActive());
        return data;
    }

    public T maptoData(Map<String, Object> data) {
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
