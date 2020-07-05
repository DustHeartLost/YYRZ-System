package com.yyrz.patient.UI.mainUI;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.yyrz.patient.R;
import com.yyrz.patient.common.http.RequestRepository;
import com.yyrz.patient.common.viewModel.CommonViewModel;

import java.util.HashMap;

public class MainUI extends Fragment {
    private RecyclerView recyclerView;
    private TextView textView;
    private ImageView imageView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!CommonViewModel.getInstance().getIsFirstEnter()) {
            CommonViewModel.getInstance().getNavController().navigate(R.id.action_mainUI_to_login);
            CommonViewModel.getInstance().setIsFirstEnter(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.main_ui, container, false);
        recyclerView=root.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        textView=root.findViewById(R.id.textView0);
        imageView=root.findViewById(R.id.imageView);
        BottomNavigationBar bottomNavigationBar=root.findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.home,"首页"))
                           .addItem(new BottomNavigationItem(R.drawable.me,"我"))
                           .setFirstSelectedPosition(0)
                           .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if(position==0){
                    imageView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                }
                if(position==1){
                    CommonViewModel commonViewModel=CommonViewModel.getInstance(null,null);
                    if(commonViewModel.getPatientInfo().getValue()==null){
                        imageView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        RequestRepository.getInstance().getInformation(commonViewModel.getPaccount());
                    }else {
                        imageView.setVisibility(View.GONE);
                        textView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(new InfoAdapter(getResources().getStringArray(R.array.patient_info)));
                    }
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonViewModel.getInstance(null,null).getPatientInfo().observe(getViewLifecycleOwner(), new Observer<HashMap>() {
            @Override
            public void onChanged(HashMap map) {
                if(map!=null){
                    imageView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    recyclerView.setAdapter(new InfoAdapter(getResources().getStringArray(R.array.patient_info)));
                }
            }
        });
    }
}
