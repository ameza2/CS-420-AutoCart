package com.example.autocart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import android.util.Log;

public class AC_AddIngredientPage extends AppCompatActivity {

    Button addButton;
    Button dateButton;
    String date = "";
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_addingredientpage);

        final EditText productEntry = findViewById(R.id.productName);
        addButton = (Button)findViewById(R.id.addIngredient);
        dateButton = (Button)findViewById(R.id.dateButton);

        //Date button Selection
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AC_AddIngredientPage.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                //Log.d("valid","onDateSet: mm/dd/yyy: " + month + " / " + day + " / " + year);

                date = month + "/" + day + "/" + year;
                dateButton.setText(date);
            }
        };

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                boolean emptyProduct = productEntry.getText().toString().isEmpty();
                boolean emptyDate = date.toString().isEmpty();

//                Log.d("Empty Product? ", "" + emptyProduct);
//                Log.d("Empty Date? ", "" + emptyDate);
//                Log.d("Product Name: ", "" + productEntry.getText().toString());
//                Log.d("Expiration Date: ", "" + date.toString());
//                Log.d("Product Characters: ", "" + productEntry.getText().toString().length());
//                Log.d("Date Characters: ", "" + date.toString().length());

                if (emptyProduct && emptyDate){
                    Toast.makeText(getApplicationContext(), "Invalid Form Submission: Missing multiple fields.", Toast.LENGTH_LONG).show(); // deactivation prompt
                    Log.d("Error [1]: ", "Empty Text Field");
                }
                else if (emptyProduct) { // Input Text Validation: Required Fields
                    Toast.makeText(getApplicationContext(), "Invalid Form Submission: Missing Ingredient Name.", Toast.LENGTH_LONG).show(); // deactivation prompt
                    Log.d("Error [2]: ", "Empty Text Field");
                }
                else if (emptyDate){
                    Toast.makeText(getApplicationContext(), "Invalid Form Submission: Missing Expiration Date.", Toast.LENGTH_LONG).show(); // deactivation prompt
                    Log.d("Error [3]: ", "Empty Text Field");
                }
                else {
                    Log.d("Success:", "Valid Text Fields");

                    if (!productEntry.getText().toString().isEmpty() || !date.isEmpty()) {
                        File file = new File(AC_AddIngredientPage.this.getFilesDir(), "ingredient");
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        try {
                            File gpxfile = new File(file, "list");
                            FileWriter writer = new FileWriter(gpxfile, true);
                            writer.write(productEntry.getText().toString() + "," + date + "\n");
                            writer.close();

                            Toast.makeText(AC_AddIngredientPage.this, "Ingredient Entry Saved", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                        }
                    }
                    Intent intent = new Intent(AC_AddIngredientPage.this, AC_IngredientPage.class);
                    startActivity(intent);
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