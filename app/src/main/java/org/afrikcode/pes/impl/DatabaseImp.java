package org.afrikcode.pes.impl;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import org.afrikcode.pes.Constants;

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

    @Override
    public CollectionReference getDaysReference() {
        return firestoreDB.collection(Constants.DayTimelineRef);
    }

    @Override
    public CollectionReference getServicesReference() {
        return firestoreDB.collection(Constants.serviceRef);
    }
}

interface DatabaseContract {

    void enableOfflinePersistence();

    void disableOfflinePersistence();

    CollectionReference getManagersReference();

    CollectionReference getBranchesReference();

    CollectionReference getClientsReference();

    CollectionReference getTransactionsReference();

    CollectionReference getYearsReference();

    CollectionReference getMonthsReference();

    CollectionReference getWeeksReference();

    CollectionReference getDaysReference();

    CollectionReference getServicesReference();

}
