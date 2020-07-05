package com.yyrz.doctor.ui.trainningUI;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;
import com.ruffian.library.RTextView;
import com.yyrz.doctor.R;

public class MyStackAdapter extends StackAdapter<String[][]> {
    private int[] color;
    private String[]title;
    MyStackAdapter(Context context) {
        super(context);
        color=context.getResources().getIntArray(R.array.trainingColor);
        title=context.getResources().getStringArray(R.array.training);
    }

    @Override
    public void bindView(String[][] data, int position, CardStackView.ViewHolder holder) {
        ColorItemViewHolder h=(ColorItemViewHolder)holder;
        h.rTextView.setBackgroundColorNormal(color[position]);
        h.rTextView.setText(title[position]);
        h.recyclerView.setAdapter(new StackRecyclerViewAdapter(data));
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view =getLayoutInflater().inflate(R.layout.stack_view_item,parent,false);
        return new ColorItemViewHolder(view);
    }
    static class ColorItemViewHolder extends CardStackView.ViewHolder{
        private RTextView  rTextView;
        private RecyclerView recyclerView;
        ColorItemViewHolder(View view) {
            super(view);
            recyclerView=view.findViewById(R.id.stack_recyclerView);
            rTextView=view.findViewById(R.id.stack_RTextView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        @Override
        public void onItemExpand(boolean b) {
            recyclerView.setVisibility(b ? View.VISIBLE : View.GONE);
        }
    }
}


