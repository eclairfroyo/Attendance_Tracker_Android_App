package com.example.sivakami.attendancetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class BridgetoService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridgeto_service);


        Toast.makeText(getApplicationContext(),"you have enabled attendance tracking service !", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(BridgetoService.this, GpsService.class);
        startService(intent);
    }
}
