package com.example.autocart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;
import java.io.FileWriter;
import android.util.Log;

public class AC_AddShoppingPage extends AppCompatActivity {

    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_addshoppingpage);

        final EditText productEntry = findViewById(R.id.productName2);
        addButton = (Button)findViewById(R.id.addShopping);

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                boolean emptyProduct = productEntry.getText().toString().isEmpty();

//                Log.d("Empty Product? ", "" + emptyProduct);
//                Log.d("Product Name: ", "" + productEntry.getText().toString());
//                Log.d("Product Characters: ", "" + productEntry.getText().toString().length());

                if (emptyProduct) {
                    Toast.makeText(getApplicationContext(), "Invalid Form Submission: Missing Product Name.", Toast.LENGTH_LONG).show(); // deactivation prompt
                    Log.d("Error [1]: ", "Empty Text Field");
                }
                else {
                    Log.d("Success:", "Valid Text Fields");

                    if (!productEntry.getText().toString().isEmpty()) {
                        File file = new File(AC_AddShoppingPage.this.getFilesDir(), "shopping");
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        try {
                            File gpxfile = new File(file, "list");
                            FileWriter writer = new FileWriter(gpxfile, true);
                            writer.write(productEntry.getText().toString() + "\n");
                            writer.close();

                            Toast.makeText(AC_AddShoppingPage.this, "Product Entry Saved", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                        }
                    }
                    Intent intent = new Intent(AC_AddShoppingPage.this, AC_ShoppingPage.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AC_AddShoppingPage.this, AC_ShoppingPage.class);
        startActivity(intent);
    }

}
