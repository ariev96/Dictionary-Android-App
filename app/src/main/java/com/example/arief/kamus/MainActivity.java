package com.example.arief.kamus;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db = null;
    private Cursor kamusCursor = null;
    private EditText txtInggris;
    private EditText txtIndonesia;
    private DataKamus datakamus = null;
    public static final String INGGRIS = "inggris";
    public static final String INDONESIA = "indonesia";
    //mendefinisikan variabel drawer
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datakamus = new DataKamus(this);
        db = datakamus.getWritableDatabase();
        datakamus.createTable(db);
        datakamus.generateData(db);
        txtInggris = (EditText) findViewById(R.id.txtinggris);
        txtIndonesia = (EditText) findViewById(R.id.txtindonesia);
        //Menginisiasi toolbar dan mensetting sebagai actionbar
        toolbar = (Toolbar)  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //menginisiasi NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //Mengatur navigation view item yang akan dipanggil untuk menangani item klik menu navigasi

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            //This item will trigger on item click of navigation menu

            public boolean onNavigationItemSelected(MenuItem menuItem){
                //Memeriksa apakah item tersebut dalam keadaan dicek  atau tidak,
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                //Menutup  drawer item klik
                drawerLayout.closeDrawers();
                //Memeriksa untuk melihat item yang akan dilklik dan melalukan aksi
                switch(menuItem.getItemId()){
                    // pilihan menu item navigasi akan menampilkan pesan toast klik kalian bisa menggantinya
                    //dengan intent activity
                    case R.id.navigation1:
                        Intent i = new Intent(getApplicationContext(),DaftarKata_Activity.class);
                        startActivity(i);
                        return true;
                    case R.id.navigation2:
                        Intent in =new Intent(getApplicationContext(),Penerjemah_Activity.class);
                        startActivity(in);
                        return true;
                    case R.id.navigation3:
                        Toast.makeText(getApplicationContext(),"Tenses Telah Dipilih",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation4:
                        Toast.makeText(getApplicationContext(),"Irreguler telah dipilih",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation5:
                        Toast.makeText(getApplicationContext(),"Bagikan telah dipilih",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation6:
                        Toast.makeText(getApplicationContext(),"Pengaturan telah dipilih",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation7:
                        Toast.makeText(getApplicationContext(),"Bantuan & Masukan telah dipilih",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation8:
                        Toast.makeText(getApplicationContext(),"Tentang telah dipilih",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation9:
                        System.exit(0);
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Kesalahan Terjadi ",Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
        // Menginisasi Drawer Layout dan ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                // Kode di sini akan merespons setelah drawer menutup disini kita biarkan kosong
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                //  Kode di sini akan merespons setelah drawer terbuka disini kita biarkan kosong
                super.onDrawerOpened(drawerView);
            }
        };
        //Mensetting actionbarToggle untuk drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //memanggil synstate
        actionBarDrawerToggle.syncState();
    }

    public void Terjemahkan(View view){         String result = "";
        String englishword = txtInggris.getText().toString();
        kamusCursor = db.rawQuery("SELECT id, INGGRIS, INDONESIA "
                + "FROM kamus where INGGRIS='" + englishword
                + "' ORDER BY INGGRIS", null);

        if(kamusCursor.moveToFirst()){
            result = kamusCursor.getString(2);
            for (; !kamusCursor.isAfterLast(); kamusCursor.moveToNext()){
                result = kamusCursor.getString(2);
            }
        }

        if(result.equals("")){
            result = "Terjemahan Not Found!";
        }
        txtIndonesia.setText(result);
    }

    public void AddData(View view){
        Intent intent = new Intent().setClass(this,AddData.class);
        startActivity(intent);
    }

    public void riwayat(View view){
        Intent intent = new Intent().setClass(this,Riwayat_Activity.class);
        startActivity(intent);
    }

    public void daftarkata(View view){
        Intent intent = new Intent().setClass(this,DaftarKata_Activity.class);
        startActivity(intent);
    }

    public void penerjemah(View view){
        Intent intent = new Intent().setClass(this,Penerjemah_Activity.class);
        startActivity(intent);
    }


    public void onDestroy(){
        super.onDestroy();
        kamusCursor.close();
        db.close();
    }
}

