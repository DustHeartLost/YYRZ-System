package com.yyrz.patient.UI.mainUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.yyrz.patient.R;
import com.yyrz.patient.common.viewModel.CommonViewModel;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoViewHolder> {
    private String[] label;
    private String[] temp;
    InfoAdapter(String[]temp){
        this.label=temp;
        this.temp= new String[]{"paccount", "name","age", "birthday", "gender", "department", "bednumber", "hosiptal","faccount","daccount"};
    }

    @NonNull
    @Override
    public InfoAdapter.InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_info_itm, parent, false);
        return new InfoViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoAdapter.InfoViewHolder holder, int position) {
        HashMap<String,String> map=CommonViewModel.getInstance(null,null).getPatientInfo().getValue();
        if(position==2){
            String birthday = map.get("birthday");
            LocalDate date1 = LocalDate.of(Integer.valueOf(birthday.substring(0,4)),Integer.valueOf(birthday.substring(5,7)), Integer.valueOf(birthday.substring(8,10)));
            String now= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
            LocalDate date2 = LocalDate.of(Integer.valueOf(now.substring(0,4)),Integer.valueOf(now.substring(5,7)), Integer.valueOf(now.substring(8,10)));
            holder.textView1.setText("年龄");
            holder.textView2.setText(String.valueOf(date1.until(date2).getYears()));
            return;
        }
        holder.textView1.setText(label[position]);
        holder.textView2.setText( map.get(temp[position]));

    }

    @Override
    public int getItemCount() {
        return label.length;
    }

    public class InfoViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1;
        private TextView textView2;
        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.textView1);
            textView2=itemView.findViewById(R.id.textView2);
        }
    }
}
