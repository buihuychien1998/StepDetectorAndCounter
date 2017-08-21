package com.tuananh.stepdetectorandcounter;

import android.app.Application;

/**
 * Created by FRAMGIA\vu.tuan.anh on 21/08/2017.
 */
public class App extends Application {
    private static App mSelf;

    public static App self() {
        return mSelf;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSelf = this;
    }
}
