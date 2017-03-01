package com.example.nindyasaridu.lokataniapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PrediksiKeuntunganActivity extends AppCompatActivity {
    private Button btnPrediksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediksi_keuntungan);

        btnPrediksi     = (Button) findViewById(R.id.btnPrediksi);
        btnPrediksi.setOnClickListener(operasi);
    }
    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnPrediksi:Register(); break;
            }
        }
    };

    private void Register(){
        Intent intentTambah = new Intent(getBaseContext(), DetilPrediksiActivity.class);
        startActivityForResult(intentTambah, 0);
    }

}
