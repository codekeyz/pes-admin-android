package org.afrikcode.pes.contracts;

public interface TransactionContract {
    void getTransactions(String branchID, String yearID, String monthID, String weekID, String dayID);
}
