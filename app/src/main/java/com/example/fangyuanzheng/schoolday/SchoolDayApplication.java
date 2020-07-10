package com.example.fangyuanzheng.schoolday;

import android.app.Application;

/**
 * Created by Fangyuan Zheng on 4/11/2017.
 */

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
public class SchoolDayApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if(FirebaseApp.getApps(this).isEmpty()){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

    }
}
