package org.afrikcode.pes.models;

import org.afrikcode.pes.base.BaseTimeline;

import java.util.Map;

public class Year extends BaseTimeline<Year> {

    private double totalAmount, totalTransactions;

    public Year(String name) {
        super.setName(name);
    }

    public Year() {
    }

    @Override
    public Year maptoData(Map<String, Object> data) {
        Year m = new Year(data.get("name").toString());
        m.setActive(Boolean.valueOf(data.get("isActive").toString()));
        return m;
    }

    @Override
    public Map<String, Object> datatoMap() {
        return super.datatoMap();
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
