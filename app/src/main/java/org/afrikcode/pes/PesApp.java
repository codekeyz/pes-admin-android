package org.afrikcode.pes;

import android.app.Application;

import org.afrikcode.pes.impl.DatabaseImp;

public class PesApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseImp databaseImp = new DatabaseImp();
        databaseImp.enableOfflinePersistence();
    }
}
