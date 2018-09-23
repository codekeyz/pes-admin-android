package org.afrikcode.pes.impl;

import com.google.firebase.firestore.CollectionReference;

import org.afrikcode.pes.base.BaseImp;
import org.afrikcode.pes.contracts.TransactionContract;
import org.afrikcode.pes.views.TransactionView;

public class TransactionImpl extends BaseImp<TransactionView> implements TransactionContract {

    CollectionReference transactionsRef;

    public TransactionImpl() {
        DatabaseImp databaseImp = new DatabaseImp();
        transactionsRef = databaseImp.getTransactionsReference();
    }
}
