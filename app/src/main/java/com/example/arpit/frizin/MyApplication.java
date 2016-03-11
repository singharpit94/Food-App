package com.example.arpit.frizin;

import android.app.Application;
import android.content.Context;

/**
 * Created by arpit on 11/3/16.
 */
public class MyApplication extends Application {
    private static MyApplication myApplication=null;
    @Override
    public void onCreate(){
        super.onCreate();
        myApplication=this;
    }
    public static  MyApplication getInstance(){
        return myApplication;
    }
    public static Context getAppContext(){
        return myApplication.getApplicationContext();
    }
}
