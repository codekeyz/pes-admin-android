package org.afrikcode.pes.models;

import com.google.firebase.firestore.PropertyName;

import java.util.List;

public class Admin {

    @PropertyName("id")
    private String id;

    @PropertyName("channels")
    private List<String> channelList;

    public Admin() {
    }

    @PropertyName("id")
    public String getId() {
        return id;
    }

    @PropertyName("id")
    public void setId(String id) {
        this.id = id;
    }


    @PropertyName("channels")
    public List<String> getChannelList() {
        return channelList;
    }

    @PropertyName("channels")
    public void setChannelList(List<String> channelList) {
        this.channelList = channelList;
    }
}
