package org.afrikcode.pes.contracts;

import org.afrikcode.pes.models.Branch;

public interface BranchContract {

    void getAllBranches();

    void deleteBranch(String branchID);

    void addBranch(Branch branch);

    void getBranch(String branchID);

    void activateBranch(String branchID);

    void deactivateBranch(String branchID);

    void setManagerforBranch(String branchID, String managerID);

}
