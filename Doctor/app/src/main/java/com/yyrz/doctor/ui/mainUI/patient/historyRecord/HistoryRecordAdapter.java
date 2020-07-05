package com.yyrz.doctor.ui.mainUI.patient.historyRecord;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.model.Moca;
import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Base64;

public class HistoryRecordAdapter extends RecyclerView.Adapter<HistoryRecordAdapter.MyViewHolder> {

    private String[] strings;
    private LinearLayoutManager linearLayoutManager;
    static boolean isLast=false;
    HistoryRecordAdapter(LinearLayoutManager linearLayoutManager){
        strings=new String[8];
        String []temp= CommonViewModel.getInstance().getContext().getResources().getStringArray(R.array.assessment);
        System.arraycopy(temp, 0, strings, 0, 3);
        System.arraycopy(temp, 4, strings, 3, 5);
        this.linearLayoutManager=linearLayoutManager;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root;
        if(viewType==0) root=LayoutInflater.from(parent.getContext()).inflate(R.layout.history_record_view_item, parent, false);
        else root=LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more,parent,false);
        return new MyViewHolder(root,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Log.d("你", String.valueOf(position));
        if (position==getItemCount()-1){
            if(isLast){
                holder.textView0.setText("加载完成");
                holder.progressBar.setVisibility(View.GONE);
            }
            return;}
        ArrayList<Moca>list=CommonViewModel.getInstance().getHistoryRecord().getValue();
        Gson gson=new Gson();
        Moca moca=gson.fromJson(gson.toJson(list.get(position)),Moca.class);
        String[] moca_value=moca.getValues();
        holder.date.setText(moca.getDate());
        holder.imageView.setContentDescription(String.valueOf(moca.getMoca_id()));
        for(int i=0;i<holder.label.length;i++){
            holder.label[i].setText(strings[i]);
            holder.value[i].setText(moca_value[i]);
        }
        if(!moca.getAssessmentRecords().equals("null")) {
            try {
                String photo=moca.getAssessmentRecords();
                photo = URLDecoder.decode(photo, "utf-8");
                java.util.Base64.Decoder decoder = Base64.getDecoder();
                byte[] imageByte = decoder.decode(photo);
                holder.imageView.setContentDescription((moca.getPaccount()+holder.date.getText()).replace(':', '-').replace(' ', '_'));
                holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageByte,0,imageByte.length));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            holder.imageView.setContentDescription("null");
        }

        holder.recordLabel.setText("病人语言记录");
        if(!moca.getFluentLanguage_record().equals("null"))holder.recordValue.setText(moca.getFluentLanguage_record());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!v.getContentDescription().equals("null")){
                    BitmapDrawable bd = (BitmapDrawable) ((ImageView)v).getDrawable();
                    Bitmap bm= bd.getBitmap();
                    CommonViewModel.getInstance().getBitmap().setValue(bm);
                    Bundle bundle=new Bundle();
                    bundle.putString("flag","2");
                    RequestRepository.getInstance().getPhotoFromNet(String.valueOf(v.getContentDescription()));
                    HistoryRecordView.position=linearLayoutManager.getPosition(holder.itemView);
                    HistoryRecordView.alreadyToImage=true;
                    CommonViewModel.getInstance().getNavController().navigate(R.id.action_historyRecordView_to_image,bundle);
                } else {
                    Toast.makeText(CommonViewModel.getInstance().getContext(),"本次记录没有照片记录",Toast.LENGTH_SHORT).show();
                }
                     }
        });
    }

    @Override
    public int getItemCount() {
        return CommonViewModel.getInstance().getHistoryRecord().getValue().size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemCount()-1==position?1:0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView[] label;
        TextView[] value;
        TextView date;
        TextView recordLabel;
        TextView recordValue;
        ImageView imageView;
        TextView textView0;
        ProgressBar progressBar;
        MyViewHolder(@NonNull View itemView,int viewType) {
            super(itemView);
            if (viewType==1){
                textView0=itemView.findViewById(R.id.textView0);
                progressBar=itemView.findViewById(R.id.progressBar);
                return;
            }
            label=new TextView[8];
            value=new TextView[8];
            value[0]=itemView.findViewById(R.id.record_history_textView3);
            value[1]=itemView.findViewById(R.id.record_history_textView5);
            value[2]=itemView.findViewById(R.id.record_history_textView7);
            value[3]=itemView.findViewById(R.id.record_history_textView9);
            value[4]=itemView.findViewById(R.id.record_history_textView11);
            value[5]=itemView.findViewById(R.id.record_history_textView13);
            value[6]=itemView.findViewById(R.id.record_history_textView15);
            value[7]=itemView.findViewById(R.id.record_history_textView19);
            label[0]=itemView.findViewById(R.id.record_history_textView2);
            label[1]=itemView.findViewById(R.id.record_history_textView4);
            label[2]=itemView.findViewById(R.id.record_history_textView6);
            label[3]=itemView.findViewById(R.id.record_history_textView8);
            label[4]=itemView.findViewById(R.id.record_history_textView10);
            label[5]=itemView.findViewById(R.id.record_history_textView12);
            label[6]=itemView.findViewById(R.id.record_history_textView14);
            label[7]=itemView.findViewById(R.id.record_history_textView18);
            recordLabel=itemView.findViewById(R.id.record_history_textView16);
            recordValue=itemView.findViewById(R.id.record_history_textView17);
            date=itemView.findViewById(R.id.record_history_textView1);
            imageView=itemView.findViewById(R.id.record_history_imageView1);
        }
    }
}
