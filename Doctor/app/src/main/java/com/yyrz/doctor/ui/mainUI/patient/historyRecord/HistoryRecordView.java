package com.yyrz.doctor.ui.mainUI.patient.historyRecord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;

import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.model.Moca;
import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class HistoryRecordView extends Fragment {
    private ConstraintLayout progressBar;
    private RecyclerView recyclerView;
    static boolean alreadyToImage;
    private int requestCount;
    private  boolean allowRequest =true;
    private OnScrollListener scrollListener;
    public static int[] dataLength;//数据的长度,第一位是起始位置，第二位是长度
    static int position;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alreadyToImage=false;
        requestCount=0;
        dataLength=new int[2];
        CommonViewModel.getInstance().getToolbar().getMenu().findItem(R.id.search).setVisible(false);
        CommonViewModel.getInstance().getToolbar().getMenu().findItem(R.id.addPatient).setVisible(false);
        RequestRepository.getInstance().getHistoryRecordFromNet(CommonViewModel.getInstance().getCurrentPaccount(),requestCount++);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {// Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.history_record_view, container, false);
        progressBar=root.findViewById(R.id.progressBar);
        recyclerView=root.findViewById(R.id.recyclerView);
        CommonViewModel.getInstance().getFloatBottom().setVisibility(View.VISIBLE);
        scrollListener=new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();
                int position= Objects.requireNonNull(layoutManager).findLastVisibleItemPosition();
                if(position==recyclerView.getAdapter().getItemCount()-1){
                    if(allowRequest){
                        RequestRepository.getInstance().getHistoryRecordFromNet(CommonViewModel.getInstance().getCurrentPaccount(),requestCount++);
                        allowRequest =false;
                    }
                }
            }
        };
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonViewModel.getInstance().getHistoryRecord().observe(this, new Observer<ArrayList<Moca>>() {
            @Override
            public void onChanged(ArrayList<Moca> mocas) {
                if(mocas==null)return;
                if(alreadyToImage){
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(new HistoryRecordAdapter((LinearLayoutManager) recyclerView.getLayoutManager()));
                    recyclerView.setOnScrollListener(scrollListener);
                    alreadyToImage=false;
                    if(requestCount > 1&&position==CommonViewModel.getInstance().getHistoryRecord().getValue().size()-1){
                        HistoryRecordAdapter.isLast=true;
                        recyclerView.scrollToPosition(position+1);
                    }else {
                        recyclerView.scrollToPosition(position);
                    }
                    return;
                }
                if(requestCount==1){
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(new HistoryRecordAdapter((LinearLayoutManager) recyclerView.getLayoutManager()));
                    recyclerView.setOnScrollListener(scrollListener);
                }else if(requestCount>1&&dataLength[1]!=0){
                    recyclerView.getAdapter().notifyItemRangeInserted(dataLength[0],dataLength[0]+dataLength[1]-1);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }else if(requestCount > 1){
                    LinearLayoutManager linearLayoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();
                    View view=linearLayoutManager.findViewByPosition(linearLayoutManager.findLastVisibleItemPosition());
                    view.findViewById(R.id.progressBar).setVisibility(View.GONE);
                    ((TextView)view.findViewById(R.id.textView0)).setText("加载完成");
                    allowRequest=false;
                    return;
                }
                allowRequest=true;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CommonViewModel.getInstance().getHistoryRecord().setValue(null);
    }
}
