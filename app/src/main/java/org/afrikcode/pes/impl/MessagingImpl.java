package org.afrikcode.pes.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.messaging.FirebaseMessaging;

import org.afrikcode.pes.contracts.MessagingContract;
import org.afrikcode.pes.enums.Channel;
import org.afrikcode.pes.models.Admin;

import java.util.ArrayList;
import java.util.List;

public class MessagingImpl implements MessagingContract {

    private FirebaseMessaging firebaseMessaging;
    private DocumentReference documentReference;
    private FirebaseUser mFirebaseUser;

    public MessagingImpl() {
        firebaseMessaging = FirebaseMessaging.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        documentReference = new DatabaseImp().getAdministratorReference().document(mFirebaseUser.getUid());
    }

    @Override
    public void subscribeTo(Channel channel) {
        firebaseMessaging.subscribeToTopic(getChannel(channel));
        List<String> strings = new ArrayList<>();
        strings.add(getChannel(channel));
        Admin admin = new Admin();
        admin.setId(mFirebaseUser.getUid());
        admin.setChannelList(strings);

        documentReference.set(admin);
    }

    private String getChannel(Channel channel) {
        String ch = null;
        if (channel == Channel.TRANSACTIONS_CHANNEL) {
            ch = "Transactions";
        }
        return ch;
    }
}
