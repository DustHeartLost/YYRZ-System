package com.yyrz.doctor.ui.mainUI.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.loopeer.cardstack.CardStackView;
import com.victor.loading.book.BookLoading;
import com.yyrz.doctor.R;
import com.yyrz.doctor.ui.trainningUI.MyStackAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment  {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.message, container, false);
        BookLoading bookLoading=root.findViewById(R.id.bookLoading);
        bookLoading.start();
        return root;
    }


}