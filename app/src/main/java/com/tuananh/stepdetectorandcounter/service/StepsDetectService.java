package com.tuananh.stepdetectorandcounter.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by FRAMGIA\vu.tuan.anh on 21/08/2017.
 */
public class StepsDetectService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
