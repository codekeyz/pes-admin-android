package org.afrikcode.pes.contracts;

import org.afrikcode.pes.enums.Channel;

public interface MessagingContract {
    void subscribeTo(Channel channel);
}
