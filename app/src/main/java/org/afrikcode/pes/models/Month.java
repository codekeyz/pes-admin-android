package org.afrikcode.pes.models;

import org.afrikcode.pes.base.BaseTimeline;

import java.util.Map;

public class Month extends BaseTimeline<Month> {

    private String yearID;
    private double totalAmount, totalTransactions;

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
        return m;
    }

    public String getYearID() {
        return yearID;
    }

    public void setYearID(String yearID) {
        this.yearID = yearID;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(double totalTransactions) {
        this.totalTransactions = totalTransactions;
    }
}
