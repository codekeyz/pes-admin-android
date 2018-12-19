package org.afrikcode.pes.models;


import org.afrikcode.pes.base.BaseTimeline;

import java.util.Map;

public class Service extends BaseTimeline<Service> {

    private String branchID;
    private String branchName;

    public Service() {
    }

    public Service(String name) {
        setName(name);
    }

    @Override
    public Map<String, Object> datatoMap() {
        Map<String, Object> result = super.datatoMap();
        result.put("branchID", branchID);
        result.put("branchName", branchName);
        return result;
    }

    @Override
    public Service maptoData(Map<String, Object> data) {
        Service m = new Service(data.get("name").toString());
        m.setBranchID(data.get("branchID").toString());
        m.setBranchName(data.get("branchName").toString());
        m.setActive(Boolean.valueOf(data.get("isActive").toString()));
        return m;
    }

    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
