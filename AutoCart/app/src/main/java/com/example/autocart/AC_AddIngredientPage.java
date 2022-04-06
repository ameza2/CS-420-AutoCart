package com.example.autocart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;
import java.io.FileWriter;

public class AC_AddIngredientPage extends AppCompatActivity {

    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_addingredientpage);

        final EditText productEntry = findViewById(R.id.productName);
        final EditText dateEntry = findViewById(R.id.expDate);
        addButton = (Button)findViewById(R.id.addIngredient);

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!productEntry.getText().toString().isEmpty() || !dateEntry.getText().toString().isEmpty()) {
                    File file = new File(AC_AddIngredientPage.this.getFilesDir(), "ingredient");
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    try {
                        File gpxfile = new File(file, "list");
                        FileWriter writer = new FileWriter(gpxfile, true);
                        writer.write(productEntry.getText().toString() + "\t\t\t" + dateEntry.getText().toString() + "\n");
                        writer.close();

                        Toast.makeText(AC_AddIngredientPage.this, "Ingredient Entry Saved", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AC_AddIngredientPage.this, AC_IngredientPage.class);
        startActivity(intent);
    }
}