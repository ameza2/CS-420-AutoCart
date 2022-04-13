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

import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

public class AC_AddIngredientPage extends AppCompatActivity {

    /* Variable Initialization */
    Button addButton;
    Button dateButton;
    String date = "";
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_addingredientpage);

        /* Page Activity */

        final EditText productEntry = findViewById(R.id.productName);
        addButton = (Button)findViewById(R.id.addIngredient);
        dateButton = (Button)findViewById(R.id.dateButton);

        /* Date Selection */

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

        /* Date Formatting */

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                //Log.d("valid","onDateSet: mm/dd/yyy: " + month + " / " + day + " / " + year);

                date = month + "/" + day + "/" + year;
                dateButton.setText(date);
            }
        };

        /* Add Button */

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean emptyProduct = productEntry.getText().toString().isEmpty();
                boolean emptyDate = date.toString().isEmpty();

                if (emptyProduct && emptyDate) { // if statement: if multiple empty fields, prompt error message
                    Toast.makeText(getApplicationContext(), "Invalid Form Submission: Missing multiple fields.", Toast.LENGTH_LONG).show(); // deactivation prompt
                    //Log.d("Error [1]: ", "Empty Text Field");
                }
                else if (emptyProduct) { // else if statement: else if empty product field, prompt error message
                    Toast.makeText(getApplicationContext(), "Invalid Form Submission: Missing Ingredient Name.", Toast.LENGTH_LONG).show(); // deactivation prompt
                    //Log.d("Error [2]: ", "Empty Text Field");
                }
                else if (emptyDate) { // else if statement: else if empty date field, prompt error message
                    Toast.makeText(getApplicationContext(), "Invalid Form Submission: Missing Expiration Date.", Toast.LENGTH_LONG).show(); // deactivation prompt
                    //Log.d("Error [3]: ", "Empty Text Field");
                }
                else { // else statement: else if valid fields, save new entry
                    //Log.d("Success:", "Valid Text Fields");

                    // Export New Entry to File //
                    File file = new File(AC_AddIngredientPage.this.getFilesDir(), "ingredient"); // open ingredient list directory
                    if (!file.exists()) { // if statement: if ingredient directory does not exist, create new directory
                        file.mkdir(); // create ingredient list directory
                    }
                    try {
                        File gpxfile = new File(file, "list"); // create ingredient list file
                        FileWriter writer = new FileWriter(gpxfile, true); // open ingredient list file in append mode (add)
                        writer.write(productEntry.getText().toString() + "," + date + "\n"); // add new ingredient entry
                        writer.close(); // close ingredient file

                        Toast.makeText(AC_AddIngredientPage.this, "Ingredient Entry Saved", Toast.LENGTH_LONG).show(); // prompt successful confirmation
                    } catch (Exception e) {
                    }

                    /* Redirect to Ingredient Page */

                    Intent intent = new Intent(AC_AddIngredientPage.this, AC_IngredientPage.class);
                    startActivity(intent);
                }
            }
        });
    }

    /* Back Button Navigation */

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AC_AddIngredientPage.this, AC_IngredientPage.class);
        startActivity(intent);
    }
}