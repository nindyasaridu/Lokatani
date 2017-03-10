package com.example.nindyasaridu.lokataniapps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TambahStok extends AppCompatActivity {

    @Override
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
    }
}
