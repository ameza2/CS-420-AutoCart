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
import java.util.Collections;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import android.util.Log;

public class AC_ShoppingPage extends AppCompatActivity {

    ArrayList<String> shoppingList;

    Button addShopping;
    Button removeShopping;
    ImageButton sortShopping;
    ListView output;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_shoppingpage);

        shoppingList = new ArrayList<>();

        addShopping = (Button) findViewById(R.id.shoppingAdd);
        removeShopping = (Button) findViewById(R.id.shoppingRemove);
        sortShopping = (ImageButton) findViewById(R.id.shoppingSort);
        output = (ListView) findViewById(R.id.outputList2);

        readFile();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shoppingList);

        output.setAdapter(adapter);

        // Dropdown Menu Logic //

        // Setting onClick behavior to the button
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
                        // Toast message on menu item clicked
//                        Toast.makeText(AC_ShoppingPage.this, "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();

//                        if (menuItem.getTitle().equals("Alphabetically")) {
//                            //Parse shoppingList to save
//                            ArrayList<String> temp3 = new ArrayList<>();
//                            for (int j = 0; j < shoppingList.size(); j++) {
//                                String[] parser = shoppingList.get(j).split("\t\t\t\t\t\t\t\t\t\t\t\t\t");
//                                for (String t : parser) {
//                                    temp3.add(t);
//                                }
//                            }
//
//                            //Rewrite shoppingList for items first
//                            shoppingList.clear();
//                            for (int i = 0; i < temp3.size(); i += 2) {
//                                shoppingList.add(temp3.get(i + 1) + "\t\t\t\t\t\t\t\t\t\t\t\t\t" + temp3.get(i));
//                            }
//
//                            //Sort shoppingList
//                            Collections.sort(shoppingList);
//
//                            //Parse shoppingList to save
//                            ArrayList<String> temp4 = new ArrayList<>();
//                            for (int j = 0; j < shoppingList.size(); j++) {
//                                String[] parser = shoppingList.get(j).split("\t\t\t\t\t\t\t\t\t\t\t\t\t");
//                                for (String t : parser) {
//                                    temp4.add(t);
//                                }
//                            }
//
//                            //Save file
//                            File file = new File(AC_ShoppingPage.this.getFilesDir(), "shopping");
//                            if (!file.exists()) {
//                                file.mkdir();
//                            }
//                            try {
//                                File gpxfile = new File(file, "list");
//                                FileWriter writer = new FileWriter(gpxfile, false);
//                                for (int k = 0; k < temp4.size(); k += 2) {
//                                    writer.write(temp4.get(k) + "," + temp4.get(k + 1) + "\n");
//                                }
//                                writer.close();
//
//                            } catch (Exception e) {
//                            }
//                        }

                        //Refresh Page
                        Intent intent = new Intent(AC_ShoppingPage.this, AC_ShoppingPage.class);
                        startActivity(intent);

                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });

        addShopping.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AC_ShoppingPage.this, AC_AddShoppingPage.class);
                startActivity(intent);
            }
        });

//        removeShopping.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(AC_ShoppingPage.this, AC_removeShoppingPage.class);
//                startActivity(intent);
//            }
//        });
    }

    private void readFile() {
        ArrayList<String> holder = new ArrayList<>();
        holder.clear();
        File fileEvents = new File(AC_ShoppingPage.this.getFilesDir() + "/shopping/list");

        if (!fileEvents.exists()) {

        } else {
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileEvents));
                String line;

                while ((line = br.readLine()) != null) {
                    holder.add(line);
                }
                for (int counter = 0; counter < holder.size(); counter++) {
                    shoppingList.add(holder.get(counter));
                    //System.out.println(holder.size());
                    //System.out.println(holder.get(counter));
                    //System.out.println(holder.get(counter+1));
                }

                br.close();
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AC_ShoppingPage.this, MainActivity.class);
        startActivity(intent);
    }
}

