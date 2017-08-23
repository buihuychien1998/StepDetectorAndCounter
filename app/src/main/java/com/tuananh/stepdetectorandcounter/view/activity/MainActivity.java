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
import com.tuananh.stepdetectorandcounter.model.Constant;
import com.tuananh.stepdetectorandcounter.service.StepService;
import com.tuananh.stepdetectorandcounter.step.UpdateUiCallBack;
import com.tuananh.stepdetectorandcounter.utils.SharedPreferencesUtils;

public class MainActivity extends AppCompatActivity {
    private boolean mIsBind;
    private ActivityMainBinding mBinding;
    private ServiceConnection mServiceConnection = new
        ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder service) {
                StepService stepService = ((StepService.StepBinder) service).getService();
                String planWalkQTY = SharedPreferencesUtils.getInstance()
                    .get(Constant.KEY_PLAN_WALK_QTY, String.class, Constant.PLAN_WALK_QTY_DEFAULT);
                mBinding.textStep.setText(String.valueOf(stepService.getStepCount()));
                // TODO: 21/08/2017
                stepService.registerCallback(new UpdateUiCallBack() {
                    @Override
                    public void updateUi(int stepCount) {
                        // TODO: 21/08/2017
//                        String planWalkQTY = SharedPreferencesUtils.getInstance()
//                            .get(Constant.KEY_PLAN_WALK_QTY, String.class,
//                                Constant.PLAN_WALK_QTY_DEFAULT);
                        mBinding.textStep.setText(String.valueOf(stepCount));
                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initData();
    }

    private void initData() {
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
