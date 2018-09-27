package org.afrikcode.pes.impl;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.afrikcode.pes.base.BaseImp;
import org.afrikcode.pes.contracts.ClientContract;
import org.afrikcode.pes.models.Client;
import org.afrikcode.pes.views.ClientView;

import java.util.ArrayList;
import java.util.List;

public class ClientImpl extends BaseImp<ClientView> implements ClientContract {

    CollectionReference clientsRef;

    public ClientImpl() {
        DatabaseImp databaseImp = new DatabaseImp();
        clientsRef = databaseImp.getClientsReference();
    }

    @Override
    public void getClientsinBranch(String branchID) {
        getView().showLoadingIndicator();
        clientsRef.whereEqualTo("branchID", branchID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                getView().hideLoadingIndicator();
                List<Client> clientList = new ArrayList<>();
                for (DocumentSnapshot snapshot : documentSnapshots) {
                    Client client = new Client().maptoData(snapshot.getData());
                    client.setId(snapshot.getId());
                    clientList.add(client);
                }

                if (!clientList.isEmpty()) {
                    getView().ongetClients(clientList);
                } else {
                    getView().onclientListEmpty();
                }
            }
        });
    }

    @Override
    public void getClient(String branchID, String clientID) {
        getView().showLoadingIndicator();
        clientsRef.whereEqualTo("branchID", branchID).whereEqualTo("id", clientID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                getView().hideLoadingIndicator();
                Client client = new Client().maptoData(documentSnapshots.getDocuments().get(0).getData());
                if (client != null) {
                    getView().ongetClient(client);
                } else {
                    getView().onError("Client cannot be found, or does not belong to your branch");
                }
            }
        });
    }

    @Override
    public void addClient(Client client) {
        getView().showLoadingIndicator();
        clientsRef.add(client.datatoMap()).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                getView().hideLoadingIndicator();
                if (task.isSuccessful()) {
                    getView().onAddClient();
                } else {
                    getView().onError("There was an error adding / updating manager info");
                }
            }
        });
    }


}
