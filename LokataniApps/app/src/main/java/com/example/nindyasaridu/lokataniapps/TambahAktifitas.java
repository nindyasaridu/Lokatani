package com.example.nindyasaridu.lokataniapps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TambahAktifitas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_aktifitas);

        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"Pemasukkan", "Pengeluaran"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, items);
        dropdown.setAdapter(adapter);
    }
}
