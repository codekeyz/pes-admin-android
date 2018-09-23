package org.afrikcode.pes.views;

import org.afrikcode.pes.base.BaseView;
import org.afrikcode.pes.models.Manager;

import java.util.List;

public interface ManagerView extends BaseView {

    void ongetManagers(List<Manager> managersList);

    void onManagerStatusChange(boolean isActive);

    void onError(String error);

    void onBranchSet(String managerID, String branchID);

}
