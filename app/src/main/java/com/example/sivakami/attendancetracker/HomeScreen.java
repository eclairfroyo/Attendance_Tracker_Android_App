package com.example.sivakami.attendancetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }
    public void GotoRegPage(View view) {

        //TAKES THE USER (EMPLOYEE) TO THE REGISTRATION PAGE.

        Intent intentToRegPage = new Intent(HomeScreen.this, RegPage.class);
        startActivity(intentToRegPage);
    }



}
