package com.example.nindyasaridu.lokataniapps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class TambahStok extends AppCompatActivity {

    protected String tambahAktifitasUrl = "http://128.199.127.175/lokatani_db/insertGudangHasTanaman.php";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_stok);

        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"Gudang A", "Gudang B", "Gudang C"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, items);
        dropdown.setAdapter(adapter);

        Spinner dropdown2 = (Spinner)findViewById(R.id.spinner2);
        String[] items2 = new String[]{"Padi", "Wortel", "Jagung"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_layout, items2);
        dropdown2.setAdapter(adapter2);

        Button btnTambahAktifitas = (Button) findViewById(R.id.tambah_hasil_panen);
        btnTambahAktifitas.setOnClickListener(this);
    }

    protected void clearInputFields() {
        Spinner gudangSpinner = (Spinner) findViewById(R.id.spinner1);
        Spinner tanamanSpinner = (Spinner) findViewById(R.id.spinner2);
        EditText jumlahTransaksiEditText = (EditText) findViewById(R.id.jumlah_tanaman);
        gudangSpinner.setSelection(0);
        tanamanSpinner.setSelection(0);
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
        Spinner gudangSpinner = (Spinner) findViewById(R.id.spinner1);
        Spinner tanamanSpinner = (Spinner) findViewById(R.id.spinner2);
        EditText jumlahTanaman = (EditText) findViewById(R.id.jumlah_tanaman);
        String gudangSpinnerString = gudangSpinner.getSelectedItem().toString().toLowerCase();
        Integer gudang = 0;
        String tanamanSpinnerString = tanamanSpinner.getSelectedItem().toString().toLowerCase();
        Integer tanaman = 0;
        Integer stokTanaman = Integer.parseInt(jumlahTanaman.getText().toString());
        switch (gudangSpinnerString) {
            case "gudang a":
                gudang = 1;
                break;
            case "gudang b":
                gudang = 2;
                break;
            case "gudang c":
                gudang = 3;
                break;
            default:
                break;
        }

        switch (tanamanSpinnerString) {
            case "padi":
                tanaman = 1;
                break;
            case "jagung":
                tanaman = 2;
                break;
            case "wortel":
                tanaman = 3;
                break;
            default:
                break;
        }

        HttpPostTask httpTask = new HttpPostTask(this);
        String parameters = "gudang_id_gudang="+ gudang +"&tanaman_id_tanaman=" + tanaman + "&jumlah=" + stokTanaman;
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
