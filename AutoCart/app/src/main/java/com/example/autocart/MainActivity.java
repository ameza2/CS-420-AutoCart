package com.example.autocart;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    Button IngredientButton;
    Button ShoppingButton;
    ImageButton SettingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_homepage);

        IngredientButton = (Button)findViewById(R.id.ingredientList);
        ShoppingButton = (Button)findViewById(R.id.outputList2);
        SettingsButton = (ImageButton)findViewById(R.id.settingsIcon);

        IngredientButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AC_IngredientPage.class);
                startActivity(intent);
            }
        });

        ShoppingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AC_ShoppingPage.class);
                startActivity(intent);
            }
        });

        SettingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AC_SettingsPage.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
    }
}