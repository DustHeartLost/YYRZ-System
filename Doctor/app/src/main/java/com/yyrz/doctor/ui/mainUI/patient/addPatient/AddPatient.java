package com.yyrz.doctor.ui.mainUI.patient.addPatient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AddPatient extends Fragment {
    private TextView[] textViews;
    private TextView textView;
    private TableLayout tableLayout;
    private String[] strings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.add_patient, container, false);
        SearchView searchView = (SearchView) CommonViewModel.getInstance().getToolbar().getMenu().findItem(R.id.search).getActionView();
        textView=root.findViewById(R.id.textView0);
        textViews=new TextView[8];
        textViews[0]=root.findViewById(R.id.textView2);
        textViews[1]=root.findViewById(R.id.textView4);
        textViews[2]=root.findViewById(R.id.textView6);
        textViews[3]=root.findViewById(R.id.textView8);
        textViews[4]=root.findViewById(R.id.textView10);
        textViews[5]=root.findViewById(R.id.textView12);
        textViews[6]=root.findViewById(R.id.textView14);
        textViews[7]=root.findViewById(R.id.textView16);
        Button button = root.findViewById(R.id.button);
        tableLayout=root.findViewById(R.id.tableLayout);
        tableLayout.setVisibility(View.GONE);
        strings=new String[]{"paccount","name","age","gender","birthday","department","bednumber","hosiptal"};
        searchView.setQueryHint("请输入患者的账号");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                CommonViewModel.getInstance().getCodeAndMsg().postValue(null);
                CommonViewModel.getInstance().setBindPatient(null);
                RequestRepository.getInstance().getBindPatient(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableLayout.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                textView.setText("正在等待对方同意绑定,请稍后...");
                RequestRepository.getInstance().bind(CommonViewModel.getInstance().getAccount(), textViews[0].getText().toString());
            }
        });
        CommonViewModel.getInstance().getToolbar().getMenu().findItem(R.id.search).setVisible(true);
        CommonViewModel.getInstance().getToolbar().getMenu().findItem(R.id.addPatient).setVisible(false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonViewModel.getInstance().getCodeAndMsg().setValue(null);
        CommonViewModel.getInstance().getCodeAndMsg().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s==null)return;
                if(s.substring(0,3).equals("100")){
                    HashMap<String,String>map=CommonViewModel.getInstance().getBindPatient();
                    String birthday = map.get("birthday");
                    LocalDate date1 = LocalDate.of(Integer.valueOf(birthday.substring(0,4)),Integer.valueOf(birthday.substring(5,7)), Integer.valueOf(birthday.substring(8,10)));
                    String now= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
                    LocalDate date2 = LocalDate.of(Integer.valueOf(now.substring(0,4)),Integer.valueOf(now.substring(5,7)), Integer.valueOf(now.substring(8,10)));
                    map.put("age",String.valueOf(date1.until(date2).getYears()));
                    for(int i=0;i<strings.length;i++){
                        textViews[i].setText(map.get(strings[i]));
                    }
                    textView.setVisibility(View.GONE);
                    tableLayout.setVisibility(View.VISIBLE);
                }else if(s.substring(0,3).equals("200")){
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(s.substring(4));
                    tableLayout.setVisibility(View.GONE);
                    Toast.makeText(getContext(),s.substring(4),Toast.LENGTH_SHORT).show();
                }else {
                    tableLayout.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(s);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        CommonViewModel.getInstance().getToolbar().getMenu().findItem(R.id.search).setVisible(false);
        CommonViewModel.getInstance().getToolbar().getMenu().findItem(R.id.addPatient).setVisible(true);
        CommonViewModel.getInstance().getCodeAndMsg().setValue(null);
        super.onDestroy();
    }
}
