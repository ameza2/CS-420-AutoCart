package com.example.autocart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import android.util.Log;

import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class AC_RemoveShoppingPage extends AppCompatActivity {

    /* Variable Initialization */

    ArrayList<String> shoppingList;
    ArrayAdapter<String> adapter;
    ArrayList<Boolean> productPressedList = new ArrayList<>(); // array consisting of buttons pressed
    ListView output;

    Button removeProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_removeshoppingpage);

        /* Page Activity */

        removeProduct = (Button)findViewById(R.id.shoppingRemove);
        output = (ListView)findViewById(R.id.outputList);

        shoppingList = new ArrayList<>();

        /* Display Shopping File to ListView */

        readFile();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, shoppingList);
        output.setAdapter(adapter);

        removeProduct.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < adapter.getCount(); i++) { // for loop: for each element in the list, check for selection
                    if(productPressedList.get(i)) { // if statement: if product is selected, replace contents with parsable string
                        // Set to "\t\t\t\t\t\t\t\t\t\t\t\t\t" so parser will ignore //
                        shoppingList.set(i, "\t\t\t\t\t\t\t\t\t\t\t\t\t");
                        adapter.notifyDataSetChanged();
                    }

                    // Parse Shopping List //
                    ArrayList<String> temp = new ArrayList<>();
                    for(int j = 0; j < shoppingList.size(); j++) { // for loop: for each element in shopping list, check for parsable string and remove from list
                        String[] parser = shoppingList.get(j).split("\t\t\t\t\t\t\t\t\t\t\t\t\t");
                        for (String t : parser) { // for loop: for each string that is not parsable, add to temp shopping list
                            temp.add(t);
                        }
                    }

                    // Export Updated List to File //
                    File file = new File(AC_RemoveShoppingPage.this.getFilesDir(), "shopping"); // open shopping list directory
                    if (!file.exists()) { // if statement: if shopping list directory does not exist, create
                        file.mkdir(); // create shopping list directory
                    }
                    try {
                        File gpxfile = new File(file, "list"); // create new shopping list file
                        FileWriter writer = new FileWriter(gpxfile, false); // write to new shopping list file w/o appending
                        for(int k = 0; k < temp.size(); k++) { // for loop: for each element in updated shopping list, export to file
                            writer.write(temp.get(k) + "\n"); // export shopping list entry
                        }

                        writer.close(); // close shopping list file

                    } catch (Exception e) {
                    }
                }

                Toast.makeText(AC_RemoveShoppingPage.this, "Product Entry Removed", Toast.LENGTH_LONG).show(); // prompt successful confirmation

                /* Redirect to Shopping Page */

                Intent intent = new Intent(AC_RemoveShoppingPage.this, AC_ShoppingPage.class);
                startActivity(intent);
            }
        });

        /* Selection Tool */

        output.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(productPressedList.get(position)) { // if statement: if item is not selected, do not highlight
                    output.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                    productPressedList.set(position, false);
                }
                else{ // else statement: else if item is selected, highlight
                    output.getChildAt(position).setBackgroundColor(Color.RED);
                    productPressedList.set(position, true); // highlight item red
                }
            }
        });
    }

    /* Read from Shopping File */

    private void readFile() {
        ArrayList<String> holder = new ArrayList<>();
        holder.clear();
        File fileEvents = new File(AC_RemoveShoppingPage.this.getFilesDir() + "/shopping/list");

        if (!fileEvents.exists()) {} // if statement: if file path does not exist, do nothing (do not read/display shopping list)
        else { // else statement: else if file path does exist, fetch file contents and add to shopping list (listView)
            try {
                String line;

                BufferedReader br = new BufferedReader(new FileReader(fileEvents)); // open shopping list file

                while ((line = br.readLine()) != null) { // for each string in the shopping list file, add to shopping list view and create pressed status
                    shoppingList.add(line);
                    productPressedList.add(false);
                }

                br.close(); // close shopping list file
            } catch (IOException e) {
            }
        }
    }

    /* Back Button Navigation */

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AC_RemoveShoppingPage.this, AC_ShoppingPage.class);
        startActivity(intent);
    }
}