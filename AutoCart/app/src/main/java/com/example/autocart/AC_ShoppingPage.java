package com.example.autocart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.view.MenuItem;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;

public class AC_ShoppingPage extends AppCompatActivity {

    /* Variable Initialization */

    ArrayList<String> shoppingList;
    ArrayAdapter<String> adapter;
    ListView output;

    Button addShopping;
    Button removeShopping;
    ImageButton sortShopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_shoppingpage);

        /* Fetch Page Activity */

        addShopping = (Button) findViewById(R.id.shoppingAdd);
        removeShopping = (Button) findViewById(R.id.shoppingRemove);
        sortShopping = (ImageButton) findViewById(R.id.shoppingSort);
        output = (ListView) findViewById(R.id.shoppingOutput);

        shoppingList = new ArrayList<>(); // create shopping list

        /* Display Shopping File to ListView */

        readFile();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shoppingList);
        output.setAdapter(adapter);

        /* Sorting Dropdown Menu */

        sortShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(AC_ShoppingPage.this, sortShopping);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.autocart_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        /* Alphabetical Sort */

                        if (menuItem.getTitle().equals("Alphabetically")) {
                            Collections.sort(shoppingList); // Sort Shopping List (A-Z)

                            // Export Sorted List to File //
                            File file = new File(AC_ShoppingPage.this.getFilesDir(), "shopping"); // open shopping directory
                            if (!file.exists()) { // if statement: if directory does not exist, then create new directory
                                file.mkdir(); // make shopping directory
                            }
                            try {
                                File gpxfile = new File(file, "list"); // create/open shopping list file
                                FileWriter writer = new FileWriter(gpxfile, false); // overwrite existing file
                                for (int k = 0; k < shoppingList.size(); k++) { // export sorted shopping list
                                    writer.write(shoppingList.get(k) + "\n");
                                }
                                writer.close(); // close shopping list file

                            } catch (Exception e) {
                            }
                        }

                        // Refresh ListView : Refresh Page //

                        Intent intent = new Intent(AC_ShoppingPage.this, AC_ShoppingPage.class);
                        startActivity(intent);

                        return true;
                    }
                });

                popupMenu.show(); // show sorting algorithm popup menu
            }
        });

        /* Add Shopping Button */

        addShopping.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AC_ShoppingPage.this, AC_AddShoppingPage.class);
                startActivity(intent);
            }
        });

        /* Remove Shopping Button */

        removeShopping.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AC_ShoppingPage.this, AC_RemoveShoppingPage.class);
                startActivity(intent);
            }
        });
    }

    /* Read from Shopping File */

    private void readFile() {
        ArrayList<String> holder = new ArrayList<>();
        holder.clear();
        File fileEvents = new File(AC_ShoppingPage.this.getFilesDir() + "/shopping/list");

        if (!fileEvents.exists()) {} // if statement: if file path does not exist, do nothing (do not read/display shopping list)
        else { // else statement: else if file path does exist, fetch file contents and add to shopping list (listView)
            try {
                String line;

                BufferedReader br = new BufferedReader(new FileReader(fileEvents)); // open shopping list file

                while ((line = br.readLine()) != null) { // for each string in the shopping list file, add to shopping list view
                    shoppingList.add(line);
                }

                br.close(); // close shopping list file
            } catch (IOException e) {
            }
        }
    }

    /* Back Button Navigation */

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AC_ShoppingPage.this, MainActivity.class);
        startActivity(intent);
    }
}

