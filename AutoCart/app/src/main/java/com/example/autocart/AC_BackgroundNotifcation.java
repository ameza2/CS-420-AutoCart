package com.example.autocart;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent ;
import android.os.Bundle ;
import android.view.View ;

public class AC_BackgroundNotifcation extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_pushnotif);
    }

    @Override
    protected void onStop() {
        super.onStop();
        startService(new Intent(this, AC_NotificationService.class));
    }

    public void closeApp(View view) {
        finish();
    }
}