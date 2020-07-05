package com.yyrz.patient.common.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.yyrz.patient.common.http.RequestRepository;
import com.yyrz.patient.common.viewModel.CommonViewModel;

public class BindFragment extends DialogFragment {
    private String tips;
    private String daccount;
    private String paccount;

    public BindFragment(String tips,String daccount,String paccount){
        this.tips=tips;
        this.daccount=daccount;
        this.paccount=paccount;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(tips)
                .setPositiveButton("拒绝", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RequestRepository.getInstance().bind(false,daccount,paccount);
                        CommonViewModel.getInstance().getPatientInfo().postValue(null);
                    }
                })
                .setNegativeButton("同意", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RequestRepository.getInstance().bind(true,daccount,paccount);
                        CommonViewModel.getInstance().getPatientInfo().postValue(null);
                    }
                });
        return builder.create();
    }
}
