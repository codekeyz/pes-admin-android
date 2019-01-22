package org.afrikcode.pes.models;


import org.afrikcode.pes.base.BaseTimeline;

import java.util.Map;

public class Service extends BaseTimeline<Service> {

    private String branchID, branchName;

    public Service() {}

    public Service(String name) {
        setName(name);
    }

    @Override
    public Map<String, Object> datatoMap() {
        Map<String, Object> data = super.datatoMap();
        data.put("branchID", branchID);
        data.put("branchName", branchName);
        return data;
    }

    @Override
    public Service maptoData(Map<String, Object> data) {
        Service service = new Service(data.get("name").toString());
        service.setBranchName(data.get("branchID").toString());
        service.setBranchID(data.get("branchID").toString());
        if (data.get("totalAmount") != null) {
            service.setTotalAmount(Double.valueOf(data.get("totalAmount").toString()));
        }else {
            service.setTotalAmount(0.0);
        }
        service.setActive(Boolean.valueOf(data.get("isActive").toString()));
        return service;
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
