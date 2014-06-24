package com.idv2.HearthMaster;

import android.app.Application;

/**
 * Created by charlee on 2014-06-23.
 */
public class HearthMasterApp extends Application {

    private static HearthMasterApp instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static HearthMasterApp getInstance() {
        return instance;
    }
}
