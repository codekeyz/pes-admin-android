package org.afrikcode.pes.models;

import org.afrikcode.pes.base.BaseTimeline;

import java.util.Map;

public class Year extends BaseTimeline<Year> {

    private String serviceID;

    public Year(String name, String serviceID) {
        super.setName(name);
        this.serviceID = serviceID;
    }

    public Year() {
    }

    @Override
    public Year maptoData(Map<String, Object> data) {
        Year m = new Year(data.get("name").toString(), data.get("serviceID").toString());
        m.setActive(Boolean.valueOf(data.get("isActive").toString()));
        if (data.get("totalAmount") != null) {
            m.setTotalAmount(Double.valueOf(data.get("totalAmount").toString()));
        }else {
            m.setTotalAmount(0.0);
        }
        return m;
    }

    @Override
    public Map<String, Object> datatoMap() {
        Map<String, Object> result = super.datatoMap();
        result.put("serviceID", serviceID);
        return result;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }
}
