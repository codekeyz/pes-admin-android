package org.afrikcode.pes.contracts;

import org.afrikcode.pes.models.Client;

public interface ClientContract {

    void getClientsinBranch(String branchID);

    void getClient(String branchID, String clientID);

    void addClient(Client client);
}
