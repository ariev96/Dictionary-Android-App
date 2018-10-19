package com.example.arief.kamus;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by arief on 26/07/2017.
 */
public class AddData extends AppCompatActivity {
    private SQLiteDatabase db = null;
    private Cursor kamusCursor = null;
    private EditText txtInggris;
    private EditText txtIndonesia;
    private DataKamus datakamus = null;
    public static final String INGGRIS = "inggris";
    public static final String INDONESIA = "indonesia";

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        datakamus = new DataKamus(this);
        db = datakamus.getWritableDatabase();
        datakamus.createTable(db);
        datakamus.generateData(db);

        setContentView(R.layout.simpan);
        txtInggris = (EditText) findViewById(R.id.txteng);
        txtIndonesia = (EditText) findViewById(R.id.txtina);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    public void SimpanData(View view){

        db.execSQL("insert into kamus(inggris,indonesia) " +
                "values('"+txtInggris.getText().toString()+"'," +
                "'"+txtIndonesia.getText().toString()+"')");
        Toast.makeText(getBaseContext(),"Data sudah disimpan", Toast.LENGTH_LONG).show();
    }

    public void back(View view){
        Intent in = new Intent().setClass(this,MainActivity.class);
        startActivity(in);
    }


    /*public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }*/

}
