package com.yyrz.doctor.ui.commonUi.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.yyrz.doctor.Util.DataRestored.Repository;
import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

public class AssessmentDialog extends DialogFragment {
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
                        commonViewModel.getRecord().setState(commonViewModel.getCurrentState().getValue());
                        RequestRepository.getInstance().currentState(commonViewModel.getCurrentPaccount(),"10",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_NAVCONTROLLER,commonViewModel.getAccount());
                        Repository.getInstance().insertRecord(CommonViewModel.getInstance().getRecord());
                        commonViewModel.clear();
                        CommonViewModel.getInstance().getToolbar().setTitle("主页");
                        commonViewModel.getNavController().navigateUp();
                    }
                });
        return builder.create();
    }
}
