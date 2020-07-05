package com.yyrz.doctor.ui.assessmentUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.victor.loading.book.BookLoading;
import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.DataRestored.Repository;
import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

public class FinishProcess extends Fragment {
    private BookLoading progressBar;
    private TextView textView;
    private Button tryAgain;
    private int count;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.finish_process, container, false);
        count=0;
        progressBar=root.findViewById(R.id.bookLoading);
        textView=root.findViewById(R.id.assessment_finish_textView1);
        progressBar.start();
        textView.setText(R.string.assessment_finish_label1);
        CommonViewModel.getInstance().getActionBar().setDisplayHomeAsUpEnabled(false);
        tryAgain = root.findViewById(R.id.assessment_finish_tryagain);
        tryAgain.setVisibility(View.INVISIBLE);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestRepository.getInstance().postDataAgain();
                tryAgain.setVisibility(View.INVISIBLE);
                textView.setText(R.string.assessment_finish_label1);
                progressBar.start();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonViewModel.getInstance().getCodeAndMsg().setValue(null);
        RequestRepository.getInstance().postMoca();
        CommonViewModel.getInstance().getCodeAndMsg().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s==null)return;
                progressBar.stop();
                if(s.substring(0,3).equals("200")){
                    textView.setText(R.string.assessment_finish_label2);
                    CommonViewModel.getInstance().getActionBar().setDisplayHomeAsUpEnabled(true);
                }else {
                    count++;
                    if(count!=4){
                        textView.setText(R.string.assessment_finish_label3);
                        tryAgain.setVisibility(View.VISIBLE);
                    }else {
                        tryAgain.setVisibility(View.GONE);
                        textView.setText(R.string.assessment_finish_label4);
                        Repository.getInstance().insertMoca(CommonViewModel.getInstance().getMoca());
                        Repository.getInstance().deleteRecord(CommonViewModel.getInstance().getRecord().getPaccount());
                        CommonViewModel.getInstance().getActionBar().setDisplayHomeAsUpEnabled(true);
                    }
                }
                CommonViewModel.getInstance().clear();
            }
        });

        RequestRepository.getInstance().postMoca();
    }

}
