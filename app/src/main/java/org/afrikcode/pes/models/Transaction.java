package org.afrikcode.pes.models;

import java.util.HashMap;
import java.util.Map;

public class Transaction {

    private String uid;
    private String clientID;
    private String clientName;
    private String clientTelephone;
    private String branchID;
    private String branchName;
    private String managerID;
    private double amount;

    private String remarks;

    private String serviceID;
    private String yearID;
    private String monthID;
    private String weekID;
    private String dayID;
    private String createdAt;

    public Transaction() {
    }

    public Map<String, Object> datatoMap() {
        Map<String, Object> data = new HashMap<>();

        // Ensuring that we don't pass null values which can override existing database data

        if (clientID != null) {
            data.put("clientID", clientID);
        }
        if (clientName != null) {
            data.put("clientName", clientName);
        }
        if (clientTelephone != null) {
            data.put("clientTelephone", clientTelephone);
        }
        if (branchID != null) {
            data.put("branchID", branchID);
        }
        if (managerID != null) {
            data.put("managerID", managerID);
        }
        if (remarks != null) {
            data.put("remarks", remarks);
        }

        data.put("amount", amount);

        if (serviceID != null) {
            data.put("serviceID", serviceID);
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
        if (dayID != null) {
            data.put("day", dayID);
        }
        if (uid != null) {
            data.put("uid", uid);
        }
        if (branchName != null) {
            data.put("branchName", branchName);
        }
        return data;
    }

    public Transaction maptoData(Map<String, Object> data) {
        Transaction m = new Transaction();
        if (data.get("clientID") != null) {
            m.setClientID(data.get("clientID").toString());
        }
        if (data.get("clientName") != null) {
            m.setClientName(data.get("clientName").toString());
        }
        if (data.get("clientTelephone") != null) {
            m.setClientTelephone(data.get("clientTelephone").toString());
        }
        if (data.get("branchID") != null) {
            m.setBranchID(data.get("branchID").toString());
        }
        if (data.get("remarks") != null) {
            m.setRemarks(data.get("remarks").toString());
        }
        if (data.get("managerID") != null) {
            m.setManagerID(data.get("managerID").toString());
        }
        if (data.get("service") != null) {
            m.setServiceID(data.get("service").toString());
        }
        if (data.get("year") != null) {
            m.setYearID(data.get("year").toString());
        }
        if (data.get("month") != null) {
            m.setMonthID(data.get("month").toString());
        }
        if (data.get("week") != null) {
            m.setWeekID(data.get("week").toString());
        }
        if (data.get("day") != null) {
            m.setDayID(data.get("day").toString());
        }
        if (data.get("branchName") != null) {
            m.setBranchName(data.get("branchName").toString());
        }
        if (data.get("uid") != null) {
            m.setUid(data.get("uid").toString());
        }
        m.setAmount(Double.valueOf(data.get("amount").toString()));

        if (data.get("createdAt") != null) {
            m.setCreatedAt(data.get("createdAt").toString());
        }
        return m;
    }

    public String getClientTelephone() {
        return clientTelephone;
    }

    public void setClientTelephone(String clientTelephone) {
        this.clientTelephone = clientTelephone;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDayID() {
        return dayID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public void setDayID(String dayID) {
        this.dayID = dayID;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
