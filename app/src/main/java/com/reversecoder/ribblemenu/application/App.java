package com.reversecoder.ribblemenu.application;

import android.app.Application;

import com.luseen.logger.LogType;
import com.luseen.logger.Logger;
import com.reversecoder.ribblemenu.BuildConfig;

/**
 * Created by Rashed on 03-Oct-17.
 */

public class App extends Application {

    public static App instance=null;

    @Override
    public void onCreate(){
        super.onCreate();

        instance = this;

        initLogger();
    }

    private void initLogger() {
        new Logger.Builder()
                .isLoggable(BuildConfig.DEBUG)
                .logType(LogType.ERROR)
                .tag("Ribble")
                .setIsKotlin(true)
                .build();
    }
}
