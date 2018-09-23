package org.afrikcode.pes.models;

import java.util.HashMap;
import java.util.Map;

public class Transaction {

    private String uid;
    private String clientID;
    private String branchID;
    private String managerID;
    private String amount;

    private String yearID;
    private String monthID;
    private String weekID;
    private String createdAt;

    private Transaction() {
    }

    public Transaction(String clientID, String branchID, String managerID, String amount, String yearID, String monthID, String weekID) {
        this.clientID = clientID;
        this.branchID = branchID;
        this.managerID = managerID;
        this.amount = amount;
        this.yearID = yearID;
        this.monthID = monthID;
        this.weekID = weekID;
    }

    public Map<String, Object> datatoMap() {
        Map<String, Object> data = new HashMap<>();

        // Ensuring that we don't pass null values which can override existing database data

        if (clientID != null) {
            data.put("clientID", clientID);
        }
        if (branchID != null) {
            data.put("branchID", branchID);
        }
        if (managerID != null) {
            data.put("managerID", managerID);
        }
        if (amount != null) {
            data.put("amount", amount);
        }
        if (yearID != null) {
            data.put("year", yearID);
        }
        if (monthID != null) {
            data.put("month", monthID);
        }
        if (weekID != null) {
            data.put("week", weekID);
        }
        if (uid != null) {
            data.put("uid", uid);
        }
        return data;
    }

    public Transaction maptoData(Map<String, Object> data) {
        Transaction m = new Transaction();
        m.setClientID(data.get("clientID").toString());
        m.setBranchID(data.get("branchID").toString());
        m.setManagerID(data.get("managerID").toString());
        m.setAmount(data.get("amount").toString());
        m.setYearID(data.get("yearID").toString());
        m.setMonthID(data.get("monthID").toString());
        m.setWeekID(data.get("weekID").toString());
        if (data.get("createdAt") != null) {
            m.setCreatedAt(data.get("createdAt").toString());
        }
        return m;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String time) {
        this.createdAt = time;
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
