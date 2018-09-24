package org.afrikcode.pes.views;

import org.afrikcode.pes.base.BaseView;

public interface AuthView extends BaseView {
    void onAuthSuccess(String userID);

    void onAuthError(String error);

    void onRequestResetPassword();
}
