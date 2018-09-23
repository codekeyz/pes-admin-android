package org.afrikcode.pes.models;

import java.util.HashMap;
import java.util.Map;

public class Client {

    private String id;
    private String branchID;
    private String name;
    private String timeStamp;
    private String telephone;
    private String location;

    public Client() {
    }

    public Client(String name, String telephone, String location, String branchID) {
        this.name = name;
        this.telephone = telephone;
        this.location = location;
        this.branchID = branchID;
    }

    public Map<String, Object> datatoMap() {
        Map<String, Object> data = new HashMap<>();

        // Ensuring that we don't pass null values which can override existing database data

        if (name != null) {
            data.put("name", name);
        }
        if (timeStamp != null) {
            data.put("timeStamp", timeStamp);
        }
        if (telephone != null) {
            data.put("telephone", telephone);
        }
        if (location != null) {
            data.put("location", location);
        }
        if (branchID != null) {
            data.put("branchID", branchID);
        }
        return data;
    }

    public Client maptoData(Map<String, Object> data) {
        Client m = new Client();
        if (data.get("id") != null) {
            m.setId(data.get("id").toString());
        }
        if (data.get("timeStamp") != null) {
            m.setTimeStamp(data.get("timeStamp").toString());
        }
        m.setName(data.get("name").toString());
        m.setTelephone(data.get("telephone").toString());
        m.setLocation(data.get("location").toString());
        return m;
    }

    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
