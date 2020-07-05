package com.yyrz.doctor.ui.commonUi.showimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.bm.library.PhotoView;
import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

import java.util.Objects;

public class Image extends Fragment {
    private PhotoView photoView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CommonViewModel commonViewModel=CommonViewModel.getInstance();
        View root=inflater.inflate(R.layout.image_layout,container,false);
        CommonViewModel.getInstance().getFloatBottom().setVisibility(View.GONE);
        photoView =root.findViewById(R.id.image_imageview);
        photoView.enable();
        commonViewModel.getIsImageBackgroundExist().setValue(1);
        switch(Objects.requireNonNull(getArguments().getString("flag"))){
            case "1":{
                Bitmap bitmap=BitmapFactory.decodeFile(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)+"/"+commonViewModel.getRecord().getPaccount()+".jpg");
                photoView.setImageBitmap(bitmap);}break;
            case "2":
                photoView.setImageBitmap(CommonViewModel.getInstance().getBitmap().getValue());
                CommonViewModel.getInstance().getBitmap().setValue(null);break;
        }
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonViewModel.getInstance().getBitmap().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                if(bitmap==null)return;
                photoView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void onDestroy() {
        CommonViewModel.getInstance().getIsImageBackgroundExist().setValue(0);
        super.onDestroy();
    }
}
