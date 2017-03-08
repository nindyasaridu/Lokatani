package com.example.nindyasaridu.lokataniapps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * Created by wicak on 3/8/2017.
 */

public class Connection {
    protected Context context;
    public Connection(Context context) {
        this.context = context;
    }

    public void failureAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Gagal terhubung ke internet");

        AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean connectionCheck() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
