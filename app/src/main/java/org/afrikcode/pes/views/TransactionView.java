package org.afrikcode.pes.views;

import org.afrikcode.pes.base.BaseView;
import org.afrikcode.pes.models.Transaction;

import java.util.List;

public interface TransactionView extends BaseView {

    void onAddSuccess();

    void onAddError();

    void ongetTransactions(List<Transaction> transactionList);
}
