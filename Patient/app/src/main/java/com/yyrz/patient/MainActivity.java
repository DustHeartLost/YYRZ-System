package com.yyrz.patient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.yyrz.patient.common.viewModel.CommonViewModel;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    private long time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getApplicationContext();
        setContentView(R.layout.activity_main);
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5ee8302b");
        //推送初始化和各种公用变量的初始化
        CommonViewModel commonViewModel=CommonViewModel.getInstance(this, getApplicationContext());
        JPushInterface.init(MainActivity.context);
        commonViewModel.getIsNoActionBar().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    getSupportActionBar().hide();
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                } else {
                    getSupportActionBar().show();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            }
        });
        commonViewModel.setFragmentManager(getSupportFragmentManager());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            NavController navController = CommonViewModel.getInstance(null, null).getNavController();
            int id = navController.getCurrentDestination().getId();
            if (id == R.id.mainUI) {
                if ((System.currentTimeMillis() - time) > 2000) {
                    Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                    time = System.currentTimeMillis();
                } else {
                    JPushInterface.deleteAlias(MainActivity.context,3);
                    System.exit(0);
                    finishActivity(0);
                }
                return true;
            }
            if(id==R.id.login){
                JPushInterface.deleteAlias(MainActivity.context,3);
                CommonViewModel.getInstance().getNavController().navigateUp();
                System.exit(0);
                finishActivity(0);
            }
            return false;
    }
}
