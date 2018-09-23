package org.afrikcode.pes.impl;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.afrikcode.pes.base.BaseImp;
import org.afrikcode.pes.contracts.BranchContract;
import org.afrikcode.pes.enums.BranchErrorType;
import org.afrikcode.pes.models.Branch;
import org.afrikcode.pes.views.BranchView;

import java.util.ArrayList;
import java.util.List;

public class BranchImpl extends BaseImp<BranchView> implements BranchContract {

    private CollectionReference branchsRef;

    public BranchImpl() {
        DatabaseImp databaseImp = new DatabaseImp();
        branchsRef = databaseImp.getBranchesReference();
    }

    @Override
    public void getAllBranches() {
        getView().showLoadingIndicator();
        branchsRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                getView().hideLoadingIndicator();
                List<Branch> branchList = new ArrayList<>();
                for (DocumentSnapshot snapshot : documentSnapshots) {
                    Branch b = new Branch().maptoData(snapshot.getData());
                    b.setBranchID(snapshot.getId());
                    branchList.add(b);
                }

                if (!branchList.isEmpty()) {
                    getView().ongetBranches(branchList);
                } else {
                    getView().onBranchesEmpty();
                }
            }
        });
    }

    @Override
    public void deleteBranch(String branchID) {
        getView().showLoadingIndicator();
        branchsRef.document(branchID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getView().hideLoadingIndicator();
                if (task.isSuccessful()) {
                    getView().ondeleteBranch();
                } else {
                    getView().onError(BranchErrorType.BRANCH_DELETE_ERROR);
                }
            }
        });
    }

    @Override
    public void addBranch(Branch branch) {
        getView().showLoadingIndicator();
        branchsRef.add(branch.datatoMap()).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                getView().hideLoadingIndicator();
                if (task.isSuccessful()) {
                    getView().onaddBranchSuccess();
                } else {
                    getView().onError(BranchErrorType.ADD_BRANCH_ERROR);
                }
            }
        });
    }

    @Override
    public void getBranch(String branchID) {
        getView().showLoadingIndicator();
        branchsRef.document(branchID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                getView().hideLoadingIndicator();
                Branch b = new Branch().maptoData(documentSnapshot.getData());
                if (b != null) {
                    getView().ongetBranch(b);
                } else {
                    getView().onError(BranchErrorType.GET_BRANCH_ERROR);
                }
            }
        });
    }

    @Override
    public void activateBranch(String branchID) {
        getView().showLoadingIndicator();
        Branch branch = new Branch();
        branch.setBranchActive(true);
        branchsRef.document(branchID).update(branch.datatoMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getView().hideLoadingIndicator();
                if (task.isSuccessful()) {
                    getView().onActivateBranch();
                } else {
                    getView().onError(BranchErrorType.BRANCH_ACTIVATE_ERROR);
                }
            }
        });
    }

    @Override
    public void deactivateBranch(String branchID) {
        getView().showLoadingIndicator();
        Branch branch = new Branch();
        branch.setBranchActive(false);
        branchsRef.document(branchID).update(branch.datatoMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getView().hideLoadingIndicator();
                if (task.isSuccessful()) {
                    getView().onDeactivateBranch();
                } else {
                    getView().onError(BranchErrorType.BRANCH_DEACTIVATE_ERROR);
                }
            }
        });
    }

    @Override
    public void setManagerforBranch(String branchID, String managerID) {
        Branch b = new Branch();
        b.setBranchManagerID(managerID);
        branchsRef.document(branchID).update(b.datatoMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    getView().onManagerSetForBranch();
                } else {
                    getView().onError(BranchErrorType.BRANCH_MANAGER_SET_ERROR);
                }
            }
        });
    }
}
