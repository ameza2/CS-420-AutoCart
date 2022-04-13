package com.example.autocart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AC_ConfigPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_configpage);
    }

    /* Back Button Navigation */

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AC_ConfigPage.this, AC_SettingsPage.class);
        startActivity(intent);
    }
}