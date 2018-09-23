package org.afrikcode.pes.contracts;

public interface AuthContract {
    void loginwithEmailandPassword(String email, String password);

    void signupwithEmailandPassword(String email, String password);

    String getUserID();

    boolean isAuthenticated();

    void logout();
}
