package com.yyrz.doctor.ui.trainningUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yyrz.doctor.R;

public class StackRecyclerViewAdapter extends RecyclerView.Adapter<StackRecyclerViewAdapter.ViewHolder> {

    private String[][]strings;

    StackRecyclerViewAdapter(String[][] temp){
        strings=temp;
    }
    @NonNull
    @Override
    public StackRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.stack_item_recycleview_adapter, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull StackRecyclerViewAdapter.ViewHolder holder, int position) {
        if(getItemCount()-1==position){
            holder.cardView.setVisibility(View.INVISIBLE);
            return;
        }
        for (int i=0;i<strings[position].length;i++){
            holder.textViews[i].setText(strings[position][i]);
        }
    }

    @Override
    public int getItemCount() {
        return strings.length+1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView[]textViews;
        CardView cardView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViews=new TextView[6];
            textViews[0]=itemView.findViewById(R.id.textView0);
            textViews[1]=itemView.findViewById(R.id.textView1);
            textViews[2]=itemView.findViewById(R.id.textView2);
            textViews[3]=itemView.findViewById(R.id.textView3);
            textViews[4]=itemView.findViewById(R.id.textView4);
            textViews[5]=itemView.findViewById(R.id.textView5);
            cardView=itemView.findViewById(R.id.stack_cardview);
        }
    }
}
