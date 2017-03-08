package com.example.nindyasaridu.lokataniapps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Created by wicak on 3/8/2017.
 */

public class HttpTask extends AsyncTask<String, Void, String> {
    private Context context;

    public HttpTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page";
        }
    }

    public String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();
            int response = conn.getResponseCode();
            Log.d("Response from server:", String.valueOf(response));
            is = conn.getInputStream();

            return readIt(is, len);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[len];
        int length;
        while ((length = stream.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }
        return out.toString();
    }

}
