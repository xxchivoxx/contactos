package com.example.l_z0k.recl;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.l_z0k.recl.ui.adapter.Datos;
import com.example.l_z0k.recl.ui.adapter.DiseaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DiseaseAdapter mAdapter;
    private List<Datos> datos;
    Button button;
    Button llamadas;
    int MY_PERMISSIONS_REQUEST_READ_CONTACTS=0;
    int MY_PERMISSIONS_REQUEST_READ_CALL_LOG=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        llamadas = (Button) findViewById(R.id.llamadas);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObtenerDatos();
            }
        });
        llamadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObtenerDatosLlamadas();
            }
        });
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS); }
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CALL_LOG)) {
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, MY_PERMISSIONS_REQUEST_READ_CALL_LOG); }
        }
        mRecyclerView = findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter= new DiseaseAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        datos = new ArrayList<>();
        
  }
    public void ObtenerDatos(){
        String[] projeccion = new String[] { ContactsContract.Data._ID, ContactsContract.Data.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE };
        String selectionClause = ContactsContract.Data.MIMETYPE + "='" +
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "' AND "
                + ContactsContract.CommonDataKinds.Phone.NUMBER + " IS NOT NULL";
        String sortOrder = ContactsContract.Data.DISPLAY_NAME + " ASC";
        datos.clear();

        Cursor c = getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                projeccion,
                selectionClause,
                null,
                sortOrder);
        while(c.moveToNext()){
            datos.add(new Datos("Identificador: " + c.getString(0) , "Nombre: " + c.getString(1) , "Número: " + c.getString(2),  "Tipo: " + c.getString(3)));
        }
        c.close();
        mAdapter.setmDataSet(datos);
    }
    public void ObtenerDatosLlamadas() {
        Uri uri;
        uri = Uri.parse("content://call_log/calls");
        String[] projeccion = new String[]{CallLog.Calls.TYPE, CallLog.Calls.NUMBER, CallLog.Calls.DURATION};
        datos.clear();
        Cursor c = getContentResolver().query(
                uri,
                projeccion,
                null,
                null,
                null);
        while(c.moveToNext()){
            datos.add(new Datos("Tipo: " + c.getString(0) , " Número: " + c.getString(1) , " Duración: " + c.getString(2) , ""));
        }
        c.close();
        mAdapter.setmDataSet(datos);
    }
}
