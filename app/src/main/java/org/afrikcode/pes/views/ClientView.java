package org.afrikcode.pes.views;

import org.afrikcode.pes.base.BaseView;
import org.afrikcode.pes.models.Client;

import java.util.List;

public interface ClientView extends BaseView {

    void ongetClients(List<Client> clientList);

    void onclientListEmpty();

    void ongetClient(Client client);

    void onAddClient();

    void onError(String error);
}
