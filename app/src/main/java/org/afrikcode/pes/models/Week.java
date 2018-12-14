package org.afrikcode.pes.models;

import org.afrikcode.pes.base.BaseTimeline;

import java.util.Map;

public class Week extends BaseTimeline<Week> {

    private String yearID;
    private String monthID;

    public Week() {
    }

    public Week(String name, String yearID, String monthID) {
        super.setName(name);
        this.monthID = monthID;
        this.yearID = yearID;
    }

    @Override
    public Map<String, Object> datatoMap() {
        Map<String, Object> map = super.datatoMap();
        map.put("yearID", getYearID());
        map.put("monthID", getMonthID());
        return map;
    }

    @Override
    public Week maptoData(Map<String, Object> data) {
        Week w = new Week(data.get("name").toString(), data.get("yearID").toString(), data.get("monthID").toString());
        w.setActive(Boolean.valueOf(data.get("isActive").toString()));
        return w;
    }

    public String getMonthID() {
        return monthID;
    }

    public void setMonthID(String monthID) {
        this.monthID = monthID;
    }

    public String getYearID() {
        return yearID;
    }

    public void setYearID(String yearID) {
        this.yearID = yearID;
    }

}
