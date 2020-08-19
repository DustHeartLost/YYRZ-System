package com.yyrz.doctor;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.yyrz.doctor.Util.DataRestored.Repository;
import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;
import com.yyrz.doctor.ui.assessmentUI.ShowViewPager;
import com.yyrz.doctor.ui.commonUi.dialog.AssessmentDialog;
import com.yyrz.doctor.ui.commonUi.dialog.TrainingDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener{
    private AppBarConfiguration mAppBarConfiguration;
    public static HashMap<String,Integer> record;
    public static final int REQUEST_TAKE_PHOTO = 1;
    private Map<String,String>map;
    private Toolbar toolbar;
    private RapidFloatingActionHelper rfabHelper;
    private RapidFloatingActionButton rfaBtn;
    private long time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //系统初始化
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_information, R.id.nav_patient,
                R.id.nav_message, R.id.nav_setting, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavigationView navigationView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //用户自定义初始化
        ShowViewPager.assessment_labels=getResources().getStringArray(R.array.assessment);
        record=new HashMap<>();
        creatInstructionMap();
        CommonViewModel commonViewModel = CommonViewModel.getInstance(this,getApplicationContext());
        JPushInterface.init(CommonViewModel.getInstance().getContext());
        CommonViewModel.getInstance().setToolbar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        CommonViewModel.getInstance().setActionBar(actionBar);
        commonViewModel.getIsImageBackgroundExist().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==1){
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    toolbar.setVisibility(View.GONE);
                }else{
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    toolbar.setVisibility(View.VISIBLE);
                }
            }
        });
        Repository.getInstance().queryMoca();
        initFloatButtom();
        CommonViewModel.getInstance().setFloatBottom(rfaBtn);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.addPatient).setVisible(false);
        menu.findItem(R.id.search).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.addPatient){
            Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.action_nav_patient_to_addPatient);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController=CommonViewModel.getInstance().getNavController();
        int id= Objects.requireNonNull(navController.getCurrentDestination()).getId();
        if(id==R.id.showViewPager){
            DialogFragment newFragment = new AssessmentDialog();
            newFragment.show(this.getSupportFragmentManager(), "missiles");
            return true;
        }
        if(id==R.id.training_home_page){
            DialogFragment newFragment = new TrainingDialog();
            newFragment.show(this.getSupportFragmentManager(), "missile");
            return true;
        }
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || navController.navigateUp();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            NavController navController=CommonViewModel.getInstance().getNavController();
            int id = Objects.requireNonNull(navController.getCurrentDestination()).getId();
            if (id == R.id.showViewPager) {
                DialogFragment newFragment = new AssessmentDialog();
                newFragment.show(this.getSupportFragmentManager(), "missiles");
                return true;
            }
            if (id == R.id.training_home_page) {
                DialogFragment newFragment = new TrainingDialog();
                newFragment.show(this.getSupportFragmentManager(), "missile");
                return true;
            }
            if(id==R.id.nav_home){
                if((System.currentTimeMillis()-time)>2000){
                    Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
                    time=System.currentTimeMillis();
                }else {
                    finishActivity(0);
                    System.exit(0);
                }
                return true;
            }
            if(id==R.id.login){
                CommonViewModel.getInstance().getNavController().navigateUp();
                System.exit(0);
                finishActivity(0);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void trainingClick(View view) {
        TextView textView=(TextView)view;
        //TODO:将来是要删除这部分的
        if(textView.getText().equals("人物类图片")||textView.getText().equals("亲人图像记忆")||textView.getText().equals("往事回忆")){
            Toast.makeText(CommonViewModel.getInstance().getContext(),"功能暂未开放，敬请期待",Toast.LENGTH_SHORT).show();
            return;
        }
        CommonViewModel commonViewModel=CommonViewModel.getInstance();
        String alias=commonViewModel.getCurrentPaccount();
        RequestRepository.getInstance().currentState(alias,map.get(textView.getText()),CommonViewModel.CON_TRAINING,CommonViewModel.TYPE_INSTRUCTIONS,null);
    }

    private void creatInstructionMap(){
        map=new HashMap<>();
        String[] data=getResources().getStringArray(R.array.memoryTraining1);
        for(int i=1;i<data.length;i++){
            map.put(data[i],"01_speech_"+i);
        }
        data=getResources().getStringArray(R.array.memoryTraining2);
        for(int i=1;i<data.length;i++){
            map.put(data[i],"02_speech_"+i);
        }
        data=getResources().getStringArray(R.array.memoryTraining3);
        for(int i=1;i<data.length;i++){
            map.put(data[i],"03_speech_"+i);
        }
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        rfabHelper.toggleContent();
        switch (position){
            case 0:
                if(CommonViewModel.getInstance().getNavController().getCurrentDestination().getId()==R.id.pieChartView)return;
                CommonViewModel.getInstance().getNavController().navigateUp();
                CommonViewModel.getInstance().getNavController().navigate(R.id.action_nav_patient_to_pieChartView);break;
            case 1:
                if(CommonViewModel.getInstance().getNavController().getCurrentDestination().getId()==R.id.historyRecordView)return;
                CommonViewModel.getInstance().getNavController().navigateUp();
                CommonViewModel.getInstance().getNavController().navigate(R.id.action_nav_patient_to_historyRecordView);break;
            case 2:
                if(CommonViewModel.getInstance().getNavController().getCurrentDestination().getId()==R.id.estimate)return;
                CommonViewModel.getInstance().getNavController().navigateUp();
                CommonViewModel.getInstance().getNavController().navigate(R.id.action_nav_patient_to_estimate);
                break;
        }
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        rfabHelper.toggleContent();
        switch (position) {
            case 0:
                if(CommonViewModel.getInstance().getNavController().getCurrentDestination().getId()==R.id.pieChartView)return;
                CommonViewModel.getInstance().getNavController().navigateUp();
                CommonViewModel.getInstance().getNavController().navigate(R.id.action_nav_patient_to_pieChartView);
                break;
            case 1:
                if(CommonViewModel.getInstance().getNavController().getCurrentDestination().getId()==R.id.historyRecordView)return;
                CommonViewModel.getInstance().getNavController().navigateUp();
                CommonViewModel.getInstance().getNavController().navigate(R.id.action_nav_patient_to_historyRecordView);
                break;
            case 2:
                if(CommonViewModel.getInstance().getNavController().getCurrentDestination().getId()==R.id.estimate)return;
                CommonViewModel.getInstance().getNavController().navigateUp();
                CommonViewModel.getInstance().getNavController().navigate(R.id.action_nav_patient_to_estimate);
                break;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initFloatButtom(){
        RapidFloatingActionLayout rfaLayout = findViewById(R.id.rapidFloatingActionButtonLayout);
        rfaBtn = findViewById(R.id.floatButtom);
        rfaBtn.setVisibility(View.GONE);
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(getApplicationContext());
            rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
            List<RFACLabelItem> items = new ArrayList<>();
            items.add(new RFACLabelItem<Integer>()
                    .setLabel("单项统计")
                    .setResId(R.drawable.statics)
                    .setIconNormalColor(Color.WHITE)
                    .setIconPressedColor(Color.GRAY)
                    .setWrapper(0)
            );
            items.add(new RFACLabelItem<Integer>()
                    .setLabel("历史记录")
                    .setResId(R.drawable.record)
                    .setIconNormalColor(Color.WHITE)
                    .setIconPressedColor(Color.GRAY)
                    //.setLabelColor(Color.WHITE)
                    .setLabelSizeSp(14)
                    .setWrapper(1)
            );
            items.add(new RFACLabelItem<Integer>()
                    .setLabel("评语")
                    .setResId(R.drawable.comments)
                    .setIconNormalColor(Color.WHITE)
                    .setIconPressedColor(Color.GRAY)
                    //.setLabelColor(0xff056f00)
                    .setWrapper(2)
            );
            rfaContent
                    .setItems(items)
            //.setIconShadowColor(Color.RED)
            ;

            //为按钮设置拖动事件
            rfaBtn.setOnTouchListener(new View.OnTouchListener() {
                int lastX, lastY,X,Y; // 记录移动的最后的位置
                private int btnHeight;
                private Date oldDate;
                private int screenWidth,screenHeight;
                @SuppressLint("CutPasteId")
                public boolean onTouch(View v, MotionEvent event) {
                    int ea = event.getAction();
                    switch (ea) {
                        case MotionEvent.ACTION_DOWN: // 按下
                            oldDate=new Date(System.currentTimeMillis());
                            lastX = (int) event.getRawX();
                            lastY = (int) event.getRawY();
                            X=lastX;Y=lastY;
                            screenWidth = findViewById(R.id.nav_host_fragment).getWidth();
                            screenHeight = findViewById(R.id.nav_host_fragment).getHeight();
                            btnHeight = rfaBtn.getHeight();
                            break;
                        case MotionEvent.ACTION_MOVE: // 移动
                            // 移动中动态设置位置
                            int dx = (int) event.getRawX() - lastX;
                            int dy = (int) event.getRawY() - lastY;
                            int left = v.getLeft() + dx;
                            int top = v.getTop() + dy;
                            int right = v.getRight() + dx;
                            int bottom = v.getBottom() + dy;
                            if (left < 0) {
                                left = 0;
                                right = left + v.getWidth();
                            }
                            if (right > screenWidth) {
                                right = screenWidth;
                                left = right - v.getWidth();
                            }
                            if (top < 0) {
                                top = 0;
                                bottom = top + v.getHeight();
                            }
                            if (bottom > screenHeight) {
                                bottom = screenHeight;
                                top = bottom - v.getHeight();
                            }
                            v.layout(left, top, right, bottom);
                            lastX = (int) event.getRawX();
                            lastY = (int) event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP: // 抬起,向四周吸附
                            if(new Date(System.currentTimeMillis()).getTime()-oldDate.getTime()<200){
                                v.performClick();
                                return false;
                            }
                            int dx1 = (int) event.getRawX() - lastX;
                            int dy1 = (int) event.getRawY() - lastY;
                            int left1 = v.getLeft() + dx1;
                            int top1 = v.getTop() + dy1;
                            int right1 = v.getRight() + dx1;
                            int bottom1 = v.getBottom() + dy1;
                            if (left1 < (screenWidth / 2)) {
                                if (top1 < 100) {
                                    v.layout(left1, 0, right1, btnHeight);
                                } else if (bottom1 > (screenHeight - 200)) {
                                    v.layout(left1, (screenHeight - btnHeight), right1, screenHeight);
                                } else {
                                    v.layout(0, top1, btnHeight, bottom1);
                                }
                            } else {
                                if (top1 < 100) {
                                    v.layout(left1, 0, right1, btnHeight);
                                } else if (bottom1 > (screenHeight - 100)) {
                                    v.layout(left1, (screenHeight - btnHeight), right1, screenHeight);
                                } else {
                                    v.layout((screenWidth - btnHeight), top1, screenWidth, bottom1);
                                }
                            }
                            break;
                    }
                    return true;
                }
            });
            rfabHelper=new RapidFloatingActionHelper(getApplicationContext(), rfaLayout, rfaBtn, rfaContent).build();
    }
}
