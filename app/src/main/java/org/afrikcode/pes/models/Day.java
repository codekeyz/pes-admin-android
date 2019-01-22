package org.afrikcode.pes.models;

import org.afrikcode.pes.base.BaseTimeline;

import java.util.Map;

public class Day extends BaseTimeline<Day> {

    private String yearID;
    private String monthID;
    private String weekID;

    public Day() {
    }

    public Day(String name, String yearID, String monthID, String weekID) {
        super.setName(name);
        this.monthID = monthID;
        this.yearID = yearID;
        this.weekID = weekID;
    }

    @Override
    public Map<String, Object> datatoMap() {
        Map<String, Object> map = super.datatoMap();
        map.put("weekID", getWeekID());
        map.put("yearID", getYearID());
        map.put("monthID", getMonthID());
        return map;
    }

    @Override
    public Day maptoData(Map<String, Object> data) {
        Day d = new Day(data.get("name").toString(), data.get("yearID").toString(), data.get("monthID").toString(), data.get("weekID").toString());
        d.setActive(Boolean.valueOf(data.get("isActive").toString()));
        if (data.get("totalAmount") != null) {
            d.setTotalAmount(Double.valueOf(data.get("totalAmount").toString()));
        }else {
            d.setTotalAmount(0.0);
        }
        return d;
    }

    public String getYearID() {
        return yearID;
    }

    public void setYearID(String yearID) {
        this.yearID = yearID;
    }

    public String getMonthID() {
        return monthID;
    }

    public void setMonthID(String monthID) {
        this.monthID = monthID;
    }

    public String getWeekID() {
        return weekID;
    }

    public void setWeekID(String weekID) {
        this.weekID = weekID;
    }

}
