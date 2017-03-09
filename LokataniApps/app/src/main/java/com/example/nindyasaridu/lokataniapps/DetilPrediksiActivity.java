package com.example.nindyasaridu.lokataniapps;

import android.content.Intent;
import android.renderscript.Double2;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class DetilPrediksiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_prediksi);

        LinearLayout detilPrediksiWrapper = (LinearLayout) findViewById(R.id.detilPrediksiWrapper);

        Intent intent = getIntent();
        Double totalPengeluaran = intent.getDoubleExtra("totalPengeluaran", 0.0);
        Double jumlahHasilPanen = intent.getDoubleExtra("jumlahHasilPanen", 0.0);
        Double rencanaHarga = intent.getDoubleExtra("rencanaHarga", 0.0);

        Double hasilStart = jumlahHasilPanen - (jumlahHasilPanen / 2);
        Double hasilEnd = jumlahHasilPanen + (jumlahHasilPanen / 2);
        Double intervalHasil = ( jumlahHasilPanen - hasilStart ) / 2;

        Double currentHasil = hasilStart;
        while (currentHasil <= hasilEnd ) {
            Double hargaJual = currentHasil * rencanaHarga;
            Double keuntungan = hargaJual - totalPengeluaran;

            LinearLayout itemPrediksiWrapper = new LinearLayout(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.weight = 1;
            lp.bottomMargin = 20;
            itemPrediksiWrapper.setBackground(getDrawable(R.drawable.card));
            itemPrediksiWrapper.setPadding(50, 80, 50, 80);
            itemPrediksiWrapper.setLayoutParams(lp);

            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp2.weight = 1;
            lp2.gravity = Gravity.CENTER_VERTICAL;
            TextView terjual = new TextView(this);
            terjual.setText("Terjual " + currentHasil + " Kg");
            terjual.setLayoutParams(lp2);
            terjual.setTextSize(16);
            TextView untung = new TextView(this);
            String keuntunganString = (String) NumberFormat.getNumberInstance(Locale.US).format(keuntungan.intValue());
            untung.setText("Rp." + keuntunganString);
            untung.setLayoutParams(lp2);
            untung.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            untung.setTextSize(20);
            if (keuntungan.intValue() > 0) {
                untung.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            } else if (keuntungan.intValue() < 0) {
                untung.setTextColor(ContextCompat.getColor(this, R.color.colorRedText));
            } else {
                untung.setTextColor(ContextCompat.getColor(this, R.color.colorSecondaryText));
            }

            itemPrediksiWrapper.addView(terjual);
            itemPrediksiWrapper.addView(untung);
            detilPrediksiWrapper.addView(itemPrediksiWrapper);

            currentHasil += intervalHasil;
        }
    }
}
