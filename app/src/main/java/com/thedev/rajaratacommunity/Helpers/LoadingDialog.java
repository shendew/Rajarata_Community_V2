package com.thedev.rajaratacommunity.Helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.thedev.rajaratacommunity.R;

public class LoadingDialog {

    Activity activity;

    Dialog dialog;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // layoutinflater object and use activity to get layout inflater
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_lay, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    public void hideDialog(){
        dialog.dismiss();
    }
}
