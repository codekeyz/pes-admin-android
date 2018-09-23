package org.afrikcode.pes.contracts;

public interface ManagerContract {

    void getManagers();

    void updateManagerStatus(String managerID, boolean isActive);

    void setBranch(String managerID, String branchName, String branchID);

}
