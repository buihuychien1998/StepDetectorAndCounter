package com.tuananh.stepdetectorandcounter.view.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import com.tuananh.stepdetectorandcounter.R;
import com.tuananh.stepdetectorandcounter.databinding.ActivityMainBinding;
import com.tuananh.stepdetectorandcounter.service.StepService;
import com.tuananh.stepdetectorandcounter.step.UpdateUiCallBack;
import com.tuananh.stepdetectorandcounter.utils.CommonUtils;

public class MainActivity extends AppCompatActivity {
    private boolean mIsBind;
    private ActivityMainBinding mBinding;
    private ServiceConnection mServiceConnection = new
        ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder service) {
                StepService stepService = ((StepService.StepBinder) service).getService();
                showStepCount(CommonUtils.getStepNumber(),
                    stepService.getStepCount());
                stepService.registerCallback(new UpdateUiCallBack() {
                    @Override
                    public void updateUi(int stepCount) {
                        showStepCount(CommonUtils.getStepNumber(), stepCount);
                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };

    public void showStepCount(int totalStepNum, int currentCounts) {
        if (currentCounts < totalStepNum) {
            currentCounts = totalStepNum;
        }
        mBinding.textStep.setText(String.valueOf(currentCounts));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initData();
    }

    private void initData() {
        showStepCount(CommonUtils.getStepNumber(), 0);
        setupService();
    }

    private void setupService() {
        Intent intent = new Intent(this, StepService.class);
        mIsBind = bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsBind) {
            unbindService(mServiceConnection);
        }
    }
}
