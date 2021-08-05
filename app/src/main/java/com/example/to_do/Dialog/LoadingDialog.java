package com.example.to_do.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Handler;
import android.view.LayoutInflater;

import com.example.to_do.R;

public class LoadingDialog {

    private Activity activity;
    private AlertDialog dialog;

/*
    private static LoadingDialog myLoadingDialog;
    public static LoadingDialog get(Activity activity){
        if (myLoadingDialog==null){
            myLoadingDialog=new LoadingDialog(activity);
        }
        return myLoadingDialog;
    }

    private LoadingDialog(Activity activity) {
        this.activity = activity;
    }
*/
    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog,null));
        builder.setCancelable(false);
        builder.setInverseBackgroundForced(true);
        dialog=builder.create();
        dialog.show();


    }
    public void dismissDialog(){
        dialog.dismiss();
    }
}
