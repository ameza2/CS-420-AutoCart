package com.example.autocart;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.util.Log;

public class AC_SettingsPage extends AppCompatActivity {

    /* Variable Initialization */

    Button settingsButton;
    Button feedbackButton;
    Button supportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_settingspage);

        /* Page Activity */

        settingsButton = (Button)findViewById(R.id.autocartSettings);
        feedbackButton = (Button)findViewById(R.id.autocartFeedback);
        supportButton = (Button)findViewById(R.id.autocartSupport);

        /* Settings Button */

        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AC_SettingsPage.this, AC_ConfigPage.class);
                startActivity(intent);
            }
        });

        /* Feedback Button */

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AC_SettingsPage.this, AC_FeedbackPage.class);
                startActivity(intent);
            }
        });

        /* Support Button */

        supportButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AC_SettingsPage.this, AC_SupportPage.class);
                startActivity(intent);
            }
        });
    }

    /* Back Button Navigation */

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AC_SettingsPage.this, MainActivity.class);
        startActivity(intent);
    }
}