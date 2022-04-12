package com.example.autocart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;
import java.io.FileWriter;
import android.util.Log;

public class AC_AddShoppingPage extends AppCompatActivity {

    /* Variable Initialization */

    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_addshoppingpage);

        /* Fetch Page Activity */

        final EditText productEntry = findViewById(R.id.productName2);
        addButton = (Button)findViewById(R.id.addShopping);

        /* Add Shopping Button */

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean emptyProduct = productEntry.getText().toString().isEmpty(); // check for empty product name field

                if (emptyProduct) { // if statement: if product field is empty, prompt error message
                    Toast.makeText(getApplicationContext(), "Invalid Form Submission: Missing Product Name.", Toast.LENGTH_LONG).show(); // deactivation prompt
                    //Log.d("Error [1]: ", "Empty Text Field");
                }
                else { // else statement: else if product field is not empty, save new entry into shopping list file
                    //Log.d("Success:", "Valid Text Fields");

                    // Export New Entry to File //
                    File file = new File(AC_AddShoppingPage.this.getFilesDir(), "shopping"); // open shopping list directory
                    if (!file.exists()) { // if statement: if shopping directory does not exist, create new directory
                        file.mkdir(); // create shopping list directory
                    }
                    try {
                        File gpxfile = new File(file, "list"); // create shopping list file
                        FileWriter writer = new FileWriter(gpxfile, true); // open shopping list file in append mode (add)
                        writer.write(productEntry.getText().toString() + "\n"); // add new product entry
                        writer.close(); // close shopping list file
                        Toast.makeText(AC_AddShoppingPage.this, "Product Entry Saved", Toast.LENGTH_LONG).show(); // prompt successful confirmation
                    } catch (Exception e) {
                    }

                    /* Redirect to Shopping Page */

                    Intent intent = new Intent(AC_AddShoppingPage.this, AC_ShoppingPage.class);
                    startActivity(intent);
                }
            }
        });
    }

    /* Back Button Navigation */

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AC_AddShoppingPage.this, AC_ShoppingPage.class);
        startActivity(intent);
    }

}
