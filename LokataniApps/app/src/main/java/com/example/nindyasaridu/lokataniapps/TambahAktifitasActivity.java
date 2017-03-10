package com.example.nindyasaridu.lokataniapps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class TambahAktifitasActivity extends AppCompatActivity implements View.OnClickListener {
    protected String tambahAktifitasUrl = "http://128.199.127.175/lokatani_db/insertTransaksiLahanData.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_aktifitas);

        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"Pemasukan", "Pengeluaran"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, items);
        dropdown.setAdapter(adapter);

        Button btnTambahAktifitas = (Button) findViewById(R.id.tambah_aktivitas);
        btnTambahAktifitas.setOnClickListener(this);
    }

    protected void clearInputFields() {
        Spinner jenisTransaksiSpinner = (Spinner) findViewById(R.id.spinner1);
        EditText namaTransaksiEditText = (EditText) findViewById(R.id.aktifitas);
        EditText jumlahTransaksiEditText = (EditText) findViewById(R.id.jumlah_uang);
        jenisTransaksiSpinner.setSelection(0);
        namaTransaksiEditText.setText("");
        jumlahTransaksiEditText.setText("");
    }

    protected void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setNegativeButton("Tutup", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onClick(View v) {
        Spinner jenisTransaksiSpinner = (Spinner) findViewById(R.id.spinner1);
        EditText namaTransaksiEditText = (EditText) findViewById(R.id.aktifitas);
        EditText jumlahTransaksiEditText = (EditText) findViewById(R.id.jumlah_uang);
        String jenisTransaksiString = jenisTransaksiSpinner.getSelectedItem().toString().toLowerCase();
        Integer jenisTransaksi = 0;
        String namaTransaksi = namaTransaksiEditText.getText().toString();
        Integer jumlahTransaksi = Integer.parseInt(jumlahTransaksiEditText.getText().toString());
        switch (jenisTransaksiString) {
            case "pemasukan":
                jenisTransaksi = 1;
                break;
            case "pengeluaran":
                jenisTransaksi = -1;
                break;
            default:
                break;
        }

        HttpPostTask httpTask = new HttpPostTask(this);
        String parameters = "id_lahan=1&nama_transaksi=" + namaTransaksi + "&jenis_transaksi=" + jenisTransaksi + "&jumlah_transaksi=" + jumlahTransaksi;
        httpTask.setStringParameters(parameters);
        String res = null;
        JSONObject responseObject = null;
        String pesan = null;
        try {
            res = httpTask.execute(tambahAktifitasUrl).get();
            responseObject = new JSONObject(res);
            pesan = responseObject.getString("pesan");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        clearInputFields();
        showDialog(pesan);
    }
}
