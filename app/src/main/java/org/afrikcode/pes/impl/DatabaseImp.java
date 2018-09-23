package org.afrikcode.pes.impl;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import org.afrikcode.pes.Constants;
import org.afrikcode.pes.contracts.DatabaseContract;

public class DatabaseImp implements DatabaseContract {

    private FirebaseFirestore firestoreDB;

    public DatabaseImp() {
        firestoreDB = FirebaseFirestore.getInstance();
    }

    @Override
    public void enableOfflinePersistence() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        firestoreDB.setFirestoreSettings(settings);
    }

    @Override
    public void disableOfflinePersistence() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        firestoreDB.setFirestoreSettings(settings);
    }

    @Override
    public CollectionReference getManagersReference() {
        return firestoreDB.collection(Constants.ManagersRef);
    }

    @Override
    public CollectionReference getAdministratorReference() {
        return firestoreDB.collection(Constants.AdministratorsRef);
    }

    @Override
    public CollectionReference getBranchesReference() {
        return firestoreDB.collection(Constants.BranchesRef);
    }

    @Override
    public CollectionReference getClientsReference() {
        return firestoreDB.collection(Constants.ClientsRef);
    }

    @Override
    public CollectionReference getTransactionsReference() {
        return firestoreDB.collection(Constants.TransactionsRef);
    }

    @Override
    public CollectionReference getYearsReference() {
        return firestoreDB.collection(Constants.YearTimelineRef);
    }

    @Override
    public CollectionReference getMonthsReference() {
        return firestoreDB.collection(Constants.MonthTimelineRef);
    }

    @Override
    public CollectionReference getWeeksReference() {
        return firestoreDB.collection(Constants.WeekTimelineRef);
    }
}
