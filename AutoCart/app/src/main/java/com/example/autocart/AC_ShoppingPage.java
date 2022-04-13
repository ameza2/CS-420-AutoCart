package com.example.autocart;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class AC_ShoppingPage extends AppCompatActivity {

    /* Variable Initialization */

    ArrayList<String> shoppingList;
    ArrayList<String> ingredientList;
    ArrayAdapter<String> adapter;
    ListView output;

    Button addShopping;
    Button removeShopping;
    ImageButton sortShopping;
    ImageButton shareShopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_shoppingpage);

        /* Fetch Page Activity */

        addShopping = (Button) findViewById(R.id.shoppingAdd);
        removeShopping = (Button) findViewById(R.id.shoppingRemove);
        sortShopping = (ImageButton) findViewById(R.id.shoppingSort);
        shareShopping = (ImageButton) findViewById(R.id.shoppingShare);
        output = (ListView) findViewById(R.id.shoppingOutput);

        shoppingList = new ArrayList<>(); // create shopping list
        ingredientList = new ArrayList<>(); // create ingredient list

        /* Display Shopping File to ListView */

        readFile();
        readFileIngredient();

        /* Parse Ingredient file and look for expired items*/
        ArrayList<String> temp = new ArrayList<>();
        for(int j = 0; j < ingredientList.size(); j++){
            String[] parser = ingredientList.get(j).split(",");
            for (String t : parser) {
                temp.add(t);
            }
        }

        Date date1;
        Date date2;
        String curr = new SimpleDateFormat("M/d/yyyy", Locale.getDefault()).format(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy");

        try {
            for(int i = 0; i < temp.size(); i+=2){
                date1 = simpleDateFormat.parse(curr);
                date2 = simpleDateFormat.parse(temp.get(i + 1));

                if(date1.compareTo(date2) > 0) {
                    if(!shoppingList.contains(temp.get(i))){
                        shoppingList.add(temp.get(i));
                    }
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Save file
        File file = new File(AC_ShoppingPage.this.getFilesDir(), "shopping");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File gpxfile = new File(file, "list");
            FileWriter writer = new FileWriter(gpxfile, false);
            for(int k = 0; k < shoppingList.size(); k++){
                writer.write(shoppingList.get(k) + "\n");
            }
            writer.close();

        } catch (Exception e) {
        }

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
                            Collections.sort(shoppingList, String.CASE_INSENSITIVE_ORDER); // Sort Shopping List (A-Z)

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

        /* Share button Selection */
        shareShopping.setOnClickListener(new View.OnClickListener() {
            private ClipboardManager myClipboard;
            private ClipData myClip;

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v)
            {
                myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String Title = "Shopping List:\n-";
                String text = String.join("\n-", shoppingList);
                
                myClip = ClipData.newPlainText("text", Title + text);
                myClipboard.setPrimaryClip(myClip);

                Toast.makeText(getApplicationContext(), "Text Copied", Toast.LENGTH_SHORT).show();
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

    /* Read from Ingredient File */
    private void readFileIngredient() {
        ArrayList<String> holder = new ArrayList<>();
        holder.clear();
        File fileEvents = new File(AC_ShoppingPage.this.getFilesDir() + "/ingredient/list");

        if (!fileEvents.exists()) {} // if statement: if file path does not exist, do nothing (do not read/display shopping list)
        else { // else statement: else if file path does exist, fetch file contents and add to shopping list (listView)
            try {
                String line;

                BufferedReader br = new BufferedReader(new FileReader(fileEvents)); // open shopping list file

                while ((line = br.readLine()) != null) { // for each string in the shopping list file, add to shopping list view
                    ingredientList.add(line);
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

