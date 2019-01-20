package org.afrikcode.pes.impl;

import com.google.firebase.messaging.FirebaseMessaging;

import org.afrikcode.pes.contracts.MessagingContract;
import org.afrikcode.pes.enums.Channel;

public class MessagingImpl implements MessagingContract {

    private FirebaseMessaging firebaseMessaging;

    public MessagingImpl() {
        firebaseMessaging = FirebaseMessaging.getInstance();
    }

    @Override
    public void subscribeTo(Channel channel) {
        firebaseMessaging.subscribeToTopic(getChannel(channel));
    }

    private String getChannel(Channel channel) {
        String ch = null;
        if (channel == Channel.TRANSACTIONS_CHANNEL) {
            ch = "Transactions";
        }
        return ch;
    }

}
