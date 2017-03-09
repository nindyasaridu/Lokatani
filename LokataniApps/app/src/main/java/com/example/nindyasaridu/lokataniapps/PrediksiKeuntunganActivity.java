package com.example.nindyasaridu.lokataniapps;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpRetryException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class PrediksiKeuntunganActivity extends AppCompatActivity {
    private Button btnPrediksi;

    private ScrollView scrollWrapper = null;
    private LinearLayout detailPengeluaranLayoutWrapper = null;
    private LinearLayout bahanWrapper = null;
    private LinearLayout hargaWrapper = null;
    private AlertDialog detailPengeluaranDialog = null;
    private Integer pengeluaran = 0;

    protected String pengeluaranUrl = "http://128.199.127.175/lokatani_db/getTransaksiLahanByJenis.php?id_lahan=1&&jenis_transaksi=-1";
    protected Connection conn = new Connection(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediksi_keuntungan);

        if (conn.connectionCheck()) {
            String res;
            try {
                res = new HttpTask(this).execute(this.pengeluaranUrl).get();
                JSONObject response = new JSONObject(res);
                JSONArray transaksi_lahan  = response.getJSONArray("transaksi_lahan");

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.weight = 1;

                scrollWrapper = new ScrollView(this);
                scrollWrapper.setPadding(0, 80, 0, 80);
                scrollWrapper.setOverScrollMode(View.OVER_SCROLL_NEVER);
                detailPengeluaranLayoutWrapper = new LinearLayout(this);
                detailPengeluaranLayoutWrapper.setPadding(50, 20, 50, 20);
                scrollWrapper.addView(detailPengeluaranLayoutWrapper);
                bahanWrapper = new LinearLayout(this);
                hargaWrapper = new LinearLayout(this);

                bahanWrapper.setOrientation(LinearLayout.VERTICAL);
                bahanWrapper.setLayoutParams(lp);
                hargaWrapper.setOrientation(LinearLayout.VERTICAL);
                hargaWrapper.setLayoutParams(lp);
                detailPengeluaranLayoutWrapper.addView(bahanWrapper);
                detailPengeluaranLayoutWrapper.addView(hargaWrapper);

                for(int i=0; i<transaksi_lahan.length(); i++){
                    JSONObject transaksi = (JSONObject) transaksi_lahan.get(i);
                    Integer jumlah_transaksi = Integer.parseInt(transaksi.getString("jumlah_transaksi"));
                    pengeluaran += jumlah_transaksi;

                        TextView bahan = new TextView(this);
                        bahan.setText(transaksi.getString("nama_transaksi"));
                        bahanWrapper.addView(bahan);

                        TextView harga = new TextView(this);
                        String harga_string = (String) NumberFormat.getNumberInstance(Locale.US).format(jumlah_transaksi);
                        harga.setText("Rp." + harga_string);
                        harga.setTextColor(getResources().getInteger(R.color.colorAccent));
                        harga.setGravity(Gravity.RIGHT);
                        hargaWrapper.addView(harga);

                }

                TextView totalPengeluaranTv = (TextView) findViewById(R.id.tvTotalPengeluaran);
                String totalPengeluaran = (String) NumberFormat.getNumberInstance(Locale.US).format(pengeluaran);
                totalPengeluaranTv.setText("Rp." + totalPengeluaran);

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setView(this.scrollWrapper);
                alertBuilder.setNegativeButton("Tutup", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                detailPengeluaranDialog = alertBuilder.create();
                detailPengeluaranDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        Button negativeButton = detailPengeluaranDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        lp.weight = 2;
                        lp.bottomMargin = 10;
                        lp.topMargin = 10;
                        negativeButton.setBackground(getDrawable(R.drawable.rounded_button));
                        negativeButton.setTextColor(Color.parseColor("#FEFEFE"));
                        negativeButton.setLayoutParams(lp);
                    }
                });
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

        TextView judulDetailPengeluaranTv = (TextView) findViewById(R.id.tvJudulDetailPengeluaran);
        judulDetailPengeluaranTv.setOnClickListener(operasi);

        btnPrediksi     = (Button) findViewById(R.id.btnPrediksi);
        btnPrediksi.setOnClickListener(operasi);
    }

    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnPrediksi:
                    openTambahIntent();
                    break;
                case R.id.tvJudulDetailPengeluaran:
                    openDetailPengeluaranWrapper();
                    break;
            }
        }
    };

    private void openTambahIntent(){
        Intent intentDetilPrediksi = new Intent(getBaseContext(), DetilPrediksiActivity.class);
        EditText textJumlahHasilPanen = (EditText) findViewById(R.id.textJumlahHasilPanen);
        EditText textRencanaHarga = (EditText) findViewById(R.id.textRencanaHarga); 
        Double jumlahHasilPanen = Double.parseDouble(textJumlahHasilPanen.getText().toString());
        Double rencanaHarga = Double.parseDouble(textRencanaHarga.getText().toString());
        intentDetilPrediksi.putExtra("totalPengeluaran", Double.parseDouble(this.pengeluaran.toString()));
        intentDetilPrediksi.putExtra("jumlahHasilPanen", jumlahHasilPanen);
        intentDetilPrediksi.putExtra("rencanaHarga", rencanaHarga);
        startActivityForResult(intentDetilPrediksi, 0);
    }

    private void openDetailPengeluaranWrapper() {
        detailPengeluaranDialog.show();
    }

}
