package com.tuananh.stepdetectorandcounter.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Created by FRAMGIA\vu.tuan.anh on 21/08/2017.
 */
public class CommonUtils {
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isSupportStepCountSensor(Context context) {
        return context.getPackageManager()
            .hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER);
    }
}
