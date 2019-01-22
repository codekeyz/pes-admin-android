package org.afrikcode.pes.models;

import org.afrikcode.pes.base.BaseTimeline;

import java.util.Map;

public class Month extends BaseTimeline<Month> {

    private String yearID;

    public Month() {
    }

    public Month(String name, String yearID) {
        super.setName(name);
        this.yearID = yearID;
    }

    @Override
    public Map<String, Object> datatoMap() {
        Map<String, Object> map = super.datatoMap();
        map.put("yearID", getYearID());
        return map;
    }

    @Override
    public Month maptoData(Map<String, Object> data) {
        Month m = new Month(data.get("name").toString(), data.get("yearID").toString());
        m.setActive(Boolean.valueOf(data.get("isActive").toString()));
        if (data.get("totalAmount") != null) {
            m.setTotalAmount(Double.valueOf(data.get("totalAmount").toString()));
        }else {
            m.setTotalAmount(0.0);
        }
        return m;
    }

    public String getYearID() {
        return yearID;
    }

    public void setYearID(String yearID) {
        this.yearID = yearID;
    }
}
