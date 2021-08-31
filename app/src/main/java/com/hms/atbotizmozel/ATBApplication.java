package com.hms.atbotizmozel;

import android.app.Application;

//import com.crashlytics.android.Crashlytics;
//import io.fabric.sdk.android.Fabric;

/**
 * Created by Batuhan on 24.11.2017.
 */

public class ATBApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
  //      Fabric.with(this, new Crashlytics());
    }
}
