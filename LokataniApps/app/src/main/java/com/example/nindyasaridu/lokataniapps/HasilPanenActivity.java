package com.example.nindyasaridu.lokataniapps;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class HasilPanenActivity extends AppCompatActivity {
    protected Connection conn = new Connection(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_panen);
        if (conn.connectionCheck()) {
            String stringUrl = "http://128.199.127.175/lokatani_db/getDetailGudangHasTanaman.php?id_gudang=1";
            String res, res2;
            try {
                res = new HttpGetTask(this).execute(stringUrl).get();
                JSONObject fullData = new JSONObject(res);
                JSONArray detail_gudang_has_tanaman  = fullData.getJSONArray("detail_gudang_has_tanaman");
                LinearLayout hasil_panen = (LinearLayout) findViewById(R.id.hasil_panen);

                for(int i=0; i<detail_gudang_has_tanaman.length(); i++){
                    LinearLayout kotak = new LinearLayout(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10,10,10,10);
                    kotak.setLayoutParams(params);
                    kotak.setBackgroundResource(R.drawable.card);
                    kotak.setOrientation(LinearLayout.HORIZONTAL);

                    JSONObject isigudang = (JSONObject) detail_gudang_has_tanaman.get(i);

                    LinearLayout kotak1 = new LinearLayout(this);
                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
                    params1.weight=1;
                    kotak1.setGravity(Gravity.CENTER);

                    ImageView gambar_tanaman = new ImageView(this);
                    gambar_tanaman.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    ViewGroup.LayoutParams imageParam = gambar_tanaman.getLayoutParams();
                    imageParam.width=160;
                    imageParam.height=160;
                    gambar_tanaman.setLayoutParams(imageParam);

                    if(Objects.equals(isigudang.getString("nama_tanaman"), "Padi")) {

                        gambar_tanaman.setImageResource(R.drawable.wheat);
                    }
                    else if(Objects.equals(isigudang.getString("nama_tanaman"), "Jagung")) {

                        gambar_tanaman.setImageResource(R.drawable.corn);
                    }
                    else if(Objects.equals(isigudang.getString("nama_tanaman"), "Wortel")) {

                        gambar_tanaman.setImageResource(R.drawable.carrot);
                    }
                    kotak1.addView(gambar_tanaman);

                    LinearLayout kotak2 = new LinearLayout(this);
                    kotak2.setLayoutParams(params1);
                    kotak2.setOrientation(LinearLayout.VERTICAL);
                    kotak2.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

                    TextView gudang = new TextView(this);
                    gudang.setText(isigudang.getString("nama_gudang"));
                    gudang.setTextSize(16);
                    kotak2.addView(gudang);

                    TextView tanaman = new TextView(this);
                    tanaman.setText(isigudang.getString("nama_tanaman"));
                    tanaman.setTextSize(24);
                    kotak2.addView(tanaman);

                    LinearLayout kotak3 = new LinearLayout(this);
                    kotak3.setLayoutParams(params1);
                    kotak3.setOrientation(LinearLayout.VERTICAL);
                    kotak3.setGravity(Gravity.CENTER);

                    TextView stok = new TextView(this);
                    stok.setText(isigudang.getString("jumlah_di_gudang")+" Kg");
                    stok.setTextSize(24);
                    kotak3.addView(stok);

                    kotak.addView(kotak1);
                    kotak.addView(kotak2);
                    kotak.addView(kotak3);

                    hasil_panen.addView(kotak);

                    FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
                    myFab.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intentTambah = new Intent(getBaseContext(), TambahStok.class);
                            startActivityForResult(intentTambah, 0);
                        }
                    });

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
