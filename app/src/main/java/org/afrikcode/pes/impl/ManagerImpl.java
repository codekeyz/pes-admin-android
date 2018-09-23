package org.afrikcode.pes.impl;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.afrikcode.pes.base.BaseImp;
import org.afrikcode.pes.contracts.ManagerContract;
import org.afrikcode.pes.models.Manager;
import org.afrikcode.pes.views.ManagerView;

import java.util.ArrayList;
import java.util.List;

public class ManagerImpl extends BaseImp<ManagerView> implements ManagerContract {

    private CollectionReference managerRef;

    public ManagerImpl() {
        DatabaseImp databaseImp = new DatabaseImp();
        this.managerRef = databaseImp.getManagersReference();
    }

    @Override
    public void getManagers() {
        getView().showLoadingIndicator();
        managerRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                getView().hideLoadingIndicator();
                List<Manager> managerList = new ArrayList<>();
                for (DocumentSnapshot snapshot : documentSnapshots.getDocuments()) {
                    Manager m = new Manager().maptoData(snapshot.getData());
                    m.setUserID(snapshot.getId());
                    managerList.add(m);
                }

                getView().ongetManagers(managerList);
            }
        });
    }

    @Override
    public void updateManagerStatus(String managerID, final boolean isActive) {
        Manager manager = new Manager();
        manager.setAccountConfirmed(isActive);
        getView().showLoadingIndicator();
        managerRef.document(managerID).update(manager.datatoMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getView().hideLoadingIndicator();
                if (task.isSuccessful()) {
                    getView().onManagerStatusChange(isActive);
                } else {
                    getView().onError("Manager status could not be updated");
                }
            }
        });
    }

    @Override
    public void setBranch(String managerID, String branchName, String branchID) {
        final String manid = managerID;
        final String branid = branchID;
        Manager manager = new Manager();
        manager.setBranchID(branchID);
        manager.setBranchName(branchName);
        getView().showLoadingIndicator();
        managerRef.document(managerID).update(manager.datatoMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getView().hideLoadingIndicator();
                if (task.isSuccessful()) {
                    getView().onBranchSet(manid, branid);
                } else {
                    getView().onError("Branch couldn't be set for this manager");
                }
            }
        });
    }
}
