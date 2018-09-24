package org.afrikcode.pes.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Admin {

    private String id;

    private List<String> channelList;

    public Admin() {
    }

    public Map<String, Object> datatoMap() {
        Map<String, Object> data = new HashMap<>();
        if (id != null) {
            data.put("id", getId());
        }
        if (channelList != null) {
            data.put("channelList", getChannelList());
        }
        return data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<String> channelList) {
        this.channelList = channelList;
    }
}
