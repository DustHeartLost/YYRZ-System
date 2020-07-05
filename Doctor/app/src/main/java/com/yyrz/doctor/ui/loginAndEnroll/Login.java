package com.yyrz.doctor.ui.loginAndEnroll;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

import cn.jpush.android.api.JPushInterface;

public class Login extends Fragment {

    private EditText account;
    private EditText password;
    private ConstraintLayout progressBar;
    private ConstraintLayout exist;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.login, container, false);
        account=root.findViewById(R.id.editText1);
        password=root.findViewById(R.id.editText2);
        Button button = root.findViewById(R.id.button);
        progressBar=root.findViewById(R.id.progressBar);
        exist=root.findViewById(R.id.exist);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(account.getText().length()==0||password.getText().length()==0) {
                    Toast.makeText(getContext(),"请输入密码或账号",Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                exist.setVisibility(View.GONE);
                CommonViewModel.getInstance().getLogin().setValue(null);
                RequestRepository.getInstance().login(account.getText().toString(),password.getText().toString());
            }
        });
        progressBar.setVisibility(View.GONE);
        exist.setVisibility(View.VISIBLE);
        CommonViewModel.getInstance().getIsImageBackgroundExist().setValue(1);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonViewModel.getInstance().getLogin().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s==null)return;
                if(s.substring(0,3).equals("200")){
                    CommonViewModel.getInstance().setAccount(s.substring(3));
                    JPushInterface.init(CommonViewModel.getInstance().getContext());
                    JPushInterface.setAlias(CommonViewModel.getInstance().getContext(),1, CommonViewModel.getInstance().getAccount());
                }
                else if(s.substring(0,3).equals("201")){
                    progressBar.setVisibility(View.GONE);
                    CommonViewModel.getInstance().getIsImageBackgroundExist().setValue(0);
                    CommonViewModel.getInstance().getLogin().setValue(null);
                    CommonViewModel.getInstance().getNavController().navigateUp();
                }else{
                    Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    exist.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CommonViewModel.getInstance().getIsImageBackgroundExist().setValue(0);
    }
}