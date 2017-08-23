package com.tuananh.stepdetectorandcounter.utils;

import com.tuananh.stepdetectorandcounter.model.Constant;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by FRAMGIA\vu.tuan.anh on 21/08/2017.
 */
public class CommonUtils {
    public static String getKeyToday() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.DATE_FORMAT);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static int getStepNumber() {
        return SharedPreferencesUtils.getInstance().get(getKeyToday(), Integer.class);
    }
}
