package org.afrikcode.pes.contracts;

import com.google.firebase.firestore.CollectionReference;

public interface DatabaseContract {

    void enableOfflinePersistence();

    void disableOfflinePersistence();

    CollectionReference getManagersReference();

    CollectionReference getAdministratorReference();

    CollectionReference getBranchesReference();

    CollectionReference getClientsReference();

    CollectionReference getTransactionsReference();

    CollectionReference getYearsReference();

    CollectionReference getMonthsReference();

    CollectionReference getWeeksReference();

    CollectionReference getDaysReference();

}
