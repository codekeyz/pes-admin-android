package org.afrikcode.pes.models;

import java.util.HashMap;
import java.util.Map;

public class Branch {

    private String branchID;
    private String branchName;
    private String telephone;
    private String address;
    private int numberOfClients;
    private String branchManagerID;
    private long branchTimeStamp;
    private int totalTransactions;
    private double totalTransValue;
    private boolean isBranchActive;

    public Branch() {
    }

    public Map<String, Object> datatoMap() {
        Map<String, Object> data = new HashMap<>();

        // Ensuring that we don't pass null values which can override existing database data
        if (branchID != null) {
            data.put("branchID", branchID);
        }
        if (branchName != null) {
            data.put("branchName", branchName);
        }
        if (address != null) {
            data.put("address", address);
        }
        data.put("numberOfClients", numberOfClients);

        if (branchManagerID != null) {
            data.put("branchManagerID", branchManagerID);
        }
        if (telephone != null) {
            data.put("telephone", telephone);
        }
        data.put("branchTimeStamp", branchTimeStamp);
        data.put("totalTransactions", totalTransactions);
        data.put("totalTransValue", totalTransValue);
        data.put("isBranchActive", isBranchActive);
        return data;
    }

    public Branch maptoData(Map<String, Object> data) {
        Branch b = new Branch();
        b.setBranchName(data.get("branchName").toString());
        b.setLocation(data.get("address").toString());
        b.setTelephone(data.get("telephone").toString());
        b.setBranchTimeStamp(Long.valueOf(data.get("branchTimeStamp").toString()));
        b.setNumberOfClients(Integer.valueOf(data.get("numberOfClients").toString()));
        if (data.get("branchManagerID") != null) {
            b.setBranchManagerID(data.get("branchManagerID").toString());
        }
        b.setTotalTransactions(Integer.valueOf(data.get("totalTransactions").toString()));
        b.setTotalTransValue(Double.valueOf(data.get("totalTransValue").toString()));
        b.setBranchActive(Boolean.valueOf(data.get("isBranchActive").toString()));
        return b;
    }

    public long getBranchTimeStamp() {
        return branchTimeStamp;
    }

    public void setBranchTimeStamp(long branchTimeStamp) {
        this.branchTimeStamp = branchTimeStamp;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getLocation() {
        return address;
    }

    public void setLocation(String address) {
        this.address = address;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public String getBranchManagerID() {
        return branchManagerID;
    }

    public void setBranchManagerID(String branchManagerID) {
        this.branchManagerID = branchManagerID;
    }

    public int getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(int totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public double getTotalTransValue() {
        return totalTransValue;
    }

    public void setTotalTransValue(double totalTransValue) {
        this.totalTransValue = totalTransValue;
    }

    public boolean isBranchActive() {
        return isBranchActive;
    }

    public void setBranchActive(boolean branchActive) {
        isBranchActive = branchActive;
    }
}
