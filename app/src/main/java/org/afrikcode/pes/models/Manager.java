package org.afrikcode.pes.models;

import java.util.HashMap;
import java.util.Map;

public class Manager {

    private String userID;
    private boolean isAccountConfirmed;
    private String username;
    private String email;
    private String branchID;
    private String branchName;
    private String avatarURL;
    private String location;
    private String telephone;
    private String password;
    private String myToken;

    public Manager() {
    }

    public Manager(String username) {
        this.username = username;
        this.isAccountConfirmed = false;
    }

    public void setMyToken(String myToken) {
        this.myToken = myToken;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public boolean isAccountConfirmed() {
        return isAccountConfirmed;
    }

    public void setAccountConfirmed(boolean accountConfirmed) {
        isAccountConfirmed = accountConfirmed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Map<String, Object> datatoMap() {
        Map<String, Object> data = new HashMap<>();

        // Ensuring that we don't pass null values which can override existing database data
        if (username != null) {
            data.put("username", username);
        }
        if (avatarURL != null) {
            data.put("avatarURL", avatarURL);
        }
        if (location != null) {
            data.put("location", location);
        }
        if (telephone != null) {
            data.put("telephone", telephone);
        }
        if (myToken != null) {
            data.put("myToken", myToken);
        }
        if (branchID != null) {
            data.put("branchID", branchID);
        }
        if (branchName != null) {
            data.put("branchName", branchName);
        }
        data.put("isAccountConfirmed", isAccountConfirmed);
        return data;
    }

    public Manager maptoData(Map<String, Object> data) {
        Manager m = new Manager();
        if (data.get("userID") != null) {
            m.setUserID(data.get("userID").toString());
        }
        m.setAccountConfirmed(Boolean.valueOf(data.get("isAccountConfirmed").toString()));
        m.setUsername(data.get("username").toString());

        if (data.get("avatarURL") != null) {
            m.setAvatarURL(data.get("avatarURL").toString());
        }

        if (data.get("location") != null) {
            m.setLocation(data.get("location").toString());
        }

        if (data.get("telephone") != null) {
            m.setTelephone(data.get("telephone").toString());
        }

        if (data.get("myToken") != null) {
            m.setMyToken(data.get("myToken").toString());
        }

        if (data.get("branchName") != null) {
            m.setBranchName(data.get("branchName").toString());
        }

        if (data.get("branchID") != null) {
            m.setBranchID(data.get("branchID").toString());
        }
        return m;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMyToken() {
        return myToken;
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
