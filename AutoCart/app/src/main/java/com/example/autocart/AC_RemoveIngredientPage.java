package com.example.autocart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AC_RemoveIngredientPage extends AppCompatActivity {

    /* Variable Initialization */

    ArrayList<String> ingredientList;
    Button removeIngredient;
    ListView output;
    ArrayAdapter<String> adapter;
    ArrayList<Boolean> ingredientPressedList = new ArrayList<>(); // array consisting of buttons pressed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_removeingredientpage);

        /* Device Local Date */

        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        TextView currDate = (TextView)findViewById(R.id.currentDate);
        currDate.setText(date_n);

        /* Page Activity */

        removeIngredient = (Button)findViewById(R.id.ingredientRemove);
        output = (ListView)findViewById(R.id.outputList);

        ingredientList = new ArrayList<>();

        /* Display Ingredient File to ListView */

        readFile();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ingredientList);
        output.setAdapter(adapter);

        /* Remove Ingredient Button */

        removeIngredient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < adapter.getCount(); i++) { // for loop: for each element in the list, check for selection
                    if(ingredientPressedList.get(i)) { // if statement: if ingredient is selected, replace contents with parsable string
                        //Set to  "\t\t\t\t\t\t\t\t\t\t\t\t\t" so parser will ignore
                        ingredientList.set(i, "\t\t\t\t\t\t\t\t\t\t\t\t\t");
                        adapter.notifyDataSetChanged();
                    }

                    // Parse Ingredient List //
                    ArrayList<String> temp = new ArrayList<>();
                    for(int j = 0; j < ingredientList.size(); j++) { // for loop: for each element in ingredient list, check for parsable string and remove from list
                        String[] parser = ingredientList.get(j).split("\t\t\t\t\t\t\t\t\t\t\t\t\t");
                        for (String t : parser) {
                            temp.add(t);
                        }
                    }

                    // Export Updated List to File //
                    File file = new File(AC_RemoveIngredientPage.this.getFilesDir(), "ingredient"); // open ingredient directory
                    if (!file.exists()) { // if statement: if ingredient directory does not exist, create
                        file.mkdir(); // create ingredient directory
                    }
                    try {
                        File gpxfile = new File(file, "list"); // create ingredient list file
                        FileWriter writer = new FileWriter(gpxfile, false); // open ingredient list file w/o append (overwrite)
                        for(int k = 0; k < temp.size(); k+=2) { // for loop: for each ingredient entry, export product name and expiration date
                            writer.write(temp.get(k + 1) + "," + temp.get(k) + "\n");
                        }

                        writer.close(); // close ingredient file
                    } catch (Exception e) {
                    }
                }

                Toast.makeText(AC_RemoveIngredientPage.this, "Ingredient Entry Removed", Toast.LENGTH_LONG).show(); // prompt successful confirmation

                /* Redirect to Shopping Page */

                Intent intent = new Intent(AC_RemoveIngredientPage.this, AC_IngredientPage.class);
                startActivity(intent);
            }
        });

        /* Selection Tool */

        output.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(ingredientPressedList.get(position)) { // if statement: if item is not selected, do not highlight
                    output.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                    ingredientPressedList.set(position, false);
                }
                else{ // else statement: else if item is selected, highlight
                    output.getChildAt(position).setBackgroundColor(Color.RED);
                    ingredientPressedList.set(position, true); // highlight red
                }
            }
        });
    }

    /* Read from Ingredient File */

    private void readFile() {
        ArrayList<String> holder = new ArrayList<>();
        holder.clear();
        File fileEvents = new File(AC_RemoveIngredientPage.this.getFilesDir() + "/ingredient/list");

        if(!fileEvents.exists()) {} // if statement: if file path does not exist, do nothing (do not read/display shopping list)
        else { // else statement: else if file path does exist, fetch file contents and add to shopping list (listView)
            try {
                String line;

                BufferedReader br = new BufferedReader(new FileReader(fileEvents)); // open ingredient list file

                while ((line = br.readLine()) != null) { // for each string in the ingredient list file, add to ingredient list view
                    String[] temp = line.split(","); // parse product name and expiration date
                    for (String t : temp) {
                        holder.add(t);
                    }
                }
                for(int counter = 0; counter < holder.size(); counter += 2) { // for each extracted item, format listView row (date, product) and add pressed status
                    ingredientList.add(holder.get(counter + 1) + "\t\t\t\t\t\t\t\t\t\t\t\t\t" + holder.get(counter));
                    ingredientPressedList.add(false);
                }

                br.close(); // close ingredient file
            } catch (IOException e) {
            }
        }
    }

    /* Back Button Navigation */

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AC_RemoveIngredientPage.this, AC_IngredientPage.class);
        startActivity(intent);
    }
}