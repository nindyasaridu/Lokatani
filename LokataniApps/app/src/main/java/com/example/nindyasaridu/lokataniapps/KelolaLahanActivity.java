package com.example.nindyasaridu.lokataniapps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.concurrent.ExecutionException;

/**
 * Created by Setiyo on 3/1/2017.
 */

public class KelolaLahanActivity extends AppCompatActivity {

    protected Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_lahan);
        if (conn.connectionCheck()) {
            String stringUrl = "http://128.199.127.175/lokatani_db/getTransaksiLahanByJenis.php?id_lahan=1&&jenis_transaksi=1";
            String res;
            try {
                res = new HttpTask(this).execute(stringUrl).get();
                JSONObject fullData = new JSONObject(res);
                Integer sukses = fullData.getInt("sukses");
                JSONArray transaksi_lahan  = fullData.getJSONArray("transaksi_lahan");

                for(int i=0; i<transaksi_lahan.length(); i++){
                    System.out.println(transaksi_lahan.get(i));
                }



            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            conn.failureAlert();
        }

    }
}