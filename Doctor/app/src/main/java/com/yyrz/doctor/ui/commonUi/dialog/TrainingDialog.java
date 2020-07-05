package com.yyrz.doctor.ui.commonUi.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

public class TrainingDialog extends DialogFragment {
    private CommonViewModel commonViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("您确定要终止本次活动返回吗？")
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        commonViewModel=CommonViewModel.getInstance();
                        String alias=commonViewModel.getCurrentPaccount();
                        RequestRepository.getInstance().currentState(alias,"21",CommonViewModel.CON_TRAINING,CommonViewModel.TYPE_NAVCONTROLLER,"doctor");
                        commonViewModel.clear();
                        commonViewModel.getNavController().navigateUp();
                   }
                });
        return builder.create();
    }
}
