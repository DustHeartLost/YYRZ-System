package com.yyrz.doctor.ui.trainningUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;
import com.yyrz.doctor.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Training_home_page extends Fragment implements CardStackView.ItemExpendListener{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.training_home_page, container, false);
        CardStackView cardStackView = root.findViewById(R.id.training_stack_card_view);
        MyStackAdapter myStackAdapter= new MyStackAdapter(getContext());
        myStackAdapter.updateData(addData());
        cardStackView.setAdapter(myStackAdapter);
        cardStackView.setItemExpendListener(this);
        return root;
    }
    @Override
    public void onItemExpend(boolean expend) {

    }
    private List<String[][]> addData(){
        List<String[][]>data=new ArrayList<>();
        String[][]element=new String[3][];
        String[]temp=getResources().getStringArray(R.array.memoryTraining1);
        element[0]=temp;
        temp=getResources().getStringArray(R.array.memoryTraining2);
        element[1]=temp;
        temp=getResources().getStringArray(R.array.memoryTraining3);
        element[2]=temp;
        data.add(element);
        data.add(element);
        data.add(element);
        data.add(element);
        data.add(element);
        data.add(element);
        data.add(element);
        data.add(element);

        data.add(element);
        data.add(element);
        data.add(element);
        data.add(element);
        data.add(element);
        data.add(element);
        data.add(element);
        data.add(element);
        return data;
    }
}
