package org.afrikcode.pes.contracts;

public interface AuthContract {
    void loginwithEmailandPassword(String email, String password);

    void sendPasswordResetLink(String email);

    String getUserID();

    boolean isAuthenticated();

    void logout();
}
