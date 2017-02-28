package com.example.nindyasaridu.lokataniapps;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class KelolaPanenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_panen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listHasilPanenView();
    }

    private void listHasilPanenView() {
        // Create list hasil panen
        String[] hasilPanen = {"Padi", "Jagung", "Kangkung"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.list_hasil_panen,
                hasilPanen);

        ListView list = (ListView) findViewById(R.id.lvHasilPanen);
        list.setAdapter(adapter);
    }

}
