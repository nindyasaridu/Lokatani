package com.example.nindyasaridu.lokataniapps;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected Connection conn = new Connection(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (conn.connectionCheck()) {
            String stringUrl = "http://128.199.127.175/lokatani_db/getTransaksiLahanByJenis.php?id_lahan=1&&jenis_transaksi=1";
            String stringUrl2 = "http://128.199.127.175/lokatani_db/getTransaksiLahanByJenis.php?id_lahan=1&&jenis_transaksi=-1";
            String res, res2;
            try {
                res = new HttpTask(this).execute(stringUrl).get();
                res2 = new HttpTask(this).execute(stringUrl2).get();
                JSONObject fullData = new JSONObject(res);
                JSONObject fullData2 = new JSONObject(res2);
                JSONArray transaksi_lahan  = fullData.getJSONArray("transaksi_lahan");
                JSONArray transaksi_lahan2  = fullData2.getJSONArray("transaksi_lahan");
                LinearLayout daftar_pemasukan_bahan = (LinearLayout) findViewById(R.id.daftar_pemasukan_bahan);
                LinearLayout daftar_pemasukan_harga = (LinearLayout) findViewById(R.id.daftar_pemasukan_harga);
                LinearLayout daftar_pengeluaran_bahan = (LinearLayout) findViewById(R.id.daftar_pengeluaran_bahan);
                LinearLayout daftar_pengeluaran_harga = (LinearLayout) findViewById(R.id.daftar_pengeluaran_harga);
                Integer pemasukan = 0;
                Integer pengeluaran = 0;

                for(int i=0; i<transaksi_lahan.length(); i++){
                    JSONObject transaksi = (JSONObject) transaksi_lahan.get(i);
                    TextView bahan = new TextView(this);
                    bahan.setText(transaksi.getString("nama_transaksi"));
                    daftar_pemasukan_bahan.addView(bahan);

                    TextView harga = new TextView(this);
                    Integer jumlah_transaksi = Integer.parseInt(transaksi.getString("jumlah_transaksi"));
                    String harga_string = (String) NumberFormat.getNumberInstance(Locale.US).format(jumlah_transaksi);
                    harga.setText("Rp." + harga_string);
                    harga.setTextColor(Color.parseColor("#4286f4"));
                    harga.setGravity(Gravity.RIGHT);
                    daftar_pemasukan_harga.addView(harga);
                    pemasukan += jumlah_transaksi;
                }

                for(int i=0; i<transaksi_lahan2.length(); i++){
                    JSONObject transaksi2 = (JSONObject) transaksi_lahan2.get(i);
                    TextView bahan = new TextView(this);
                    bahan.setText(transaksi2.getString("nama_transaksi"));
                    daftar_pengeluaran_bahan.addView(bahan);

                    TextView harga = new TextView(this);
                    Integer jumlah_transaksi2 = Integer.parseInt(transaksi2.getString("jumlah_transaksi"));
                    String harga_string = (String) NumberFormat.getNumberInstance(Locale.US).format(jumlah_transaksi2);
                    harga.setText("Rp." + harga_string);
                    harga.setTextColor(Color.parseColor("#f44242"));
                    harga.setGravity(Gravity.RIGHT);
                    daftar_pengeluaran_harga.addView(harga);
                    pengeluaran += jumlah_transaksi2;
                }

                Integer hasil_akhir = pemasukan - pengeluaran;

                TextView uang_masuk = (TextView) findViewById(R.id.uang_masuk);
                TextView uang_keluar = (TextView) findViewById(R.id.uang_keluar);
                TextView total = (TextView) findViewById(R.id.total);

                String pemasukan_string = (String) NumberFormat.getNumberInstance(Locale.US).format(pemasukan);
                String pengeluaran_string = (String) NumberFormat.getNumberInstance(Locale.US).format(pengeluaran);
                String hasil_akhir_string = (String) NumberFormat.getNumberInstance(Locale.US).format(hasil_akhir);
                total.setText("Rp."+ hasil_akhir_string);
                uang_masuk.setText("Rp." + pemasukan_string);
                uang_keluar.setText("Rp." + pengeluaran_string);

                FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
                myFab.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intentTambah = new Intent(getBaseContext(), TambahAktifitas.class);
                        startActivityForResult(intentTambah, 0);
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
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_kelola) {
            Intent intentTambah = new Intent(getBaseContext(), MenuActivity.class);
            startActivityForResult(intentTambah, 0);
        } else if (id == R.id.nav_jadwal) {
            Intent intentTambah = new Intent(getBaseContext(), TambahJadwalActivity.class);
            startActivityForResult(intentTambah, 0);
        } else if (id == R.id.nav_hasil) {
            Intent intentTambah = new Intent(getBaseContext(), HasilPanenActivity.class);
            startActivityForResult(intentTambah, 0);
        } else if (id == R.id.nav_keuntungan) {
            Intent intentTambah = new Intent(getBaseContext(), PrediksiKeuntunganActivity.class);
            startActivityForResult(intentTambah, 0);
        } else if (id == R.id.nav_komoditi) {
            Intent intentTambah = new Intent(getBaseContext(), HargaKomoditiActivity.class);
            startActivityForResult(intentTambah, 0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
