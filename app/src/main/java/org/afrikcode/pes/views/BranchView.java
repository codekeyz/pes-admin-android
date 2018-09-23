package org.afrikcode.pes.views;

import org.afrikcode.pes.base.BaseView;
import org.afrikcode.pes.enums.BranchErrorType;
import org.afrikcode.pes.models.Branch;

import java.util.List;

public interface BranchView extends BaseView {

    void ongetBranches(List<Branch> branchList);

    void onBranchesEmpty();

    void onaddBranchSuccess();

    void ondeleteBranch();

    void ongetBranch(Branch branch);

    void onActivateBranch();

    void onDeactivateBranch();

    void onManagerSetForBranch();

    void onError(BranchErrorType errorType);

}
