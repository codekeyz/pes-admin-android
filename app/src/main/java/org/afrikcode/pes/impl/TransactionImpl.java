package org.afrikcode.pes.impl;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.afrikcode.pes.base.BaseImp;
import org.afrikcode.pes.contracts.TransactionContract;
import org.afrikcode.pes.models.Transaction;
import org.afrikcode.pes.views.TransactionView;

import java.util.ArrayList;
import java.util.List;

public class TransactionImpl extends BaseImp<TransactionView> implements TransactionContract {

    private CollectionReference transactionsRef;

    public TransactionImpl() {
        DatabaseImp databaseImp = new DatabaseImp();
        transactionsRef = databaseImp.getTransactionsReference();
    }

    @Override
    public void getTransactions(String branchID, String yearID, String monthID, String weekID, String dayID) {
        getView().showLoadingIndicator();
        transactionsRef
                .whereEqualTo("branchID", branchID)
                .whereEqualTo("year", yearID)
                .whereEqualTo("month", monthID)
                .whereEqualTo("week", weekID)
                .whereEqualTo("day", dayID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        getView().hideLoadingIndicator();
                        List<Transaction> transactionList = new ArrayList<>();
                        for (DocumentSnapshot snapshot : documentSnapshots.getDocuments()) {
                            Transaction transaction = new Transaction().maptoData(snapshot.getData());
                            transaction.setUid(snapshot.getId());
                            transactionList.add(transaction);
                        }

                        if (!transactionList.isEmpty()) {
                            getView().ongetTransactions(transactionList);
                        } else {
                            getView().onTransactionsEmpty();
                        }
                    }
                });
    }

}
