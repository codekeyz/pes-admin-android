package org.afrikcode.pes.impl;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.afrikcode.pes.base.BaseImp;
import org.afrikcode.pes.contracts.AuthContract;
import org.afrikcode.pes.views.AuthView;

public class AuthImp extends BaseImp<AuthView> implements AuthContract {

    private FirebaseAuth mAuth;

    public AuthImp() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean isAuthenticated() {
        return mAuth.getCurrentUser() != null;
    }

    @Override
    public String getUserID() {
        return mAuth.getCurrentUser().getUid();
    }

    @Override
    public void loginwithEmailandPassword(String email, String password) {
        if (email.isEmpty() || password.isEmpty())
            return;

        getView().showLoadingIndicator(); // show the loading indicator

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                getView().hideLoadingIndicator();
                if (task.isSuccessful()) {
                    //Process when login is successful
                    getView().onAuthSuccess(getUserID());
                } else {
                    //Handling Login exceptions
                    getView().onAuthError("An error occurred while trying to authenticate you");
                }
            }
        });
    }

    @Override
    public void sendPasswordResetLink(String email) {
        getView().showLoadingIndicator();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getView().hideLoadingIndicator();
                if (task.isSuccessful()) {
                    getView().onRequestResetPassword();
                } else {
                    getView().onAuthError("Request password request failed, try again later");
                }
            }
        });
    }

    @Override
    public void logout() {
        mAuth.signOut();
    }

}
