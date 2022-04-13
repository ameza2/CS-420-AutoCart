package com.example.autocart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.util.Log;

import java.io.FileWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class AC_IngredientPage extends AppCompatActivity {

    /* Variable Initialization */

    ArrayList<String> ingredientList;

    Button addIngredient;
    Button removeIngredient;
    ImageButton sortIngredient;
    ListView output;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_ingredientpage);

        /* Device Local Date */

        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        TextView currDate = (TextView)findViewById(R.id.currentDate);
        currDate.setText(date_n);

        /* Page Activity */

        addIngredient = (Button)findViewById(R.id.ingredientAdd);
        removeIngredient = (Button)findViewById(R.id.ingredientRemove);
        sortIngredient = (ImageButton)findViewById(R.id.ingredientSort);
        output = (ListView)findViewById(R.id.outputList);

        ingredientList = new ArrayList<>();

        /* Display Ingredient File to ListView */

        readFile();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ingredientList){

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view =super.getView(position, convertView, parent);
            //TextView textView=(TextView) view.findViewById(android.R.id.text1);

            /* Color-Coded Expiration Dates */

            // Parse Ingredient List //
            ArrayList<String> temp = new ArrayList<>();
            for(int j = 0; j < ingredientList.size(); j++){
                String[] parser = ingredientList.get(j).split("\t\t\t\t\t\t\t\t\t\t\t\t\t");
                for (String t : parser) {
                    temp.add(t);
                }
            }

            // Date Comparison + Color Assignments //
            Date date1;
            Date date2;
            String curr = new SimpleDateFormat("M/d/yyyy", Locale.getDefault()).format(new Date());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy");

            try {
                date1 = simpleDateFormat.parse(curr);
                date2 = simpleDateFormat.parse(temp.get(position * 2));

                // Fetch Date Difference (e.g., days) //
                long difference = Math.abs(date1.getTime() - date2.getTime());
                long differenceDates = difference / (24 * 60 * 60 * 1000);

                for(int j = 0; j < position + 1; j++) { // for loop: for each ingredient entry, color-code background based on expiration date status
                    if(date1.compareTo(date2) > 0) { // if statement: if item expired, highlight red
                        //Log.d("option 1","Expired: Red");
                        //textView.setTextColor(Color.RED);
                        view.setBackgroundColor(Color.rgb(255, 89, 89));
                    }
                    else if((date1.compareTo(date2) < 0) || (date1.compareTo(date2) == 0)) { // else if statement: else if item is not expired, check for remaining days
                        if (differenceDates <= 7) { // if statement: if remaining days <= 7, item expires soon, highlight yellow
                            //Log.d("option 2", "Almost Expired : Yellow");
                            //textView.setTextColor(Color.YELLOW);
                            view.setBackgroundColor(Color.rgb(254, 255, 162));
                        }
                        else { // else statement: else if remaining days > 7, item does not expire soon, highlight green
                            //Log.d("option 3", "Healthy : Green");
                            //textView.setTextColor(Color.GREEN);
                            view.setBackgroundColor(Color.rgb(121, 255, 154));
                        }
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

            return view;
        }
    };

        /* Display Color-Coded Ingredient List */

        output.setAdapter(adapter);

        /* Sorting Dropdown Menu */

        sortIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(AC_IngredientPage.this, sortIngredient);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.autocart_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().equals("Alphabetically")) { // Alphabetical Sort
                            // Parse Ingredient List to Save //
                            ArrayList<String> temp3 = new ArrayList<>();
                            for(int j = 0; j < ingredientList.size(); j++){
                                String[] parser = ingredientList.get(j).split("\t\t\t\t\t\t\t\t\t\t\t\t\t");
                                for (String t : parser) {
                                    temp3.add(t);
                                }
                            }

                            // Rewrite Ingredient List (Formatting: Date - Name) //
                            ingredientList.clear();
                            for(int i = 0; i < temp3.size(); i += 2){
                                ingredientList.add(temp3.get(i + 1) + "\t\t\t\t\t\t\t\t\t\t\t\t\t" + temp3.get(i));
                            }

                            Collections.sort(ingredientList, String.CASE_INSENSITIVE_ORDER); // Sort Ingredient List (A-Z)

                            // Parse Ingredient List to Save //
                            ArrayList<String> temp4 = new ArrayList<>();
                            for(int j = 0; j < ingredientList.size(); j++){
                                String[] parser = ingredientList.get(j).split("\t\t\t\t\t\t\t\t\t\t\t\t\t");
                                for (String t : parser) {
                                    temp4.add(t);
                                }
                            }

                            // Export Sorted List to File //
                            File file = new File(AC_IngredientPage.this.getFilesDir(), "ingredient");
                            if (!file.exists()) {
                                file.mkdir();
                            }
                            try {
                                File gpxfile = new File(file, "list");
                                FileWriter writer = new FileWriter(gpxfile, false);
                                for(int k = 0; k < temp4.size(); k+=2){
                                    writer.write(temp4.get(k) + "," + temp4.get(k + 1) + "\n");
                                }
                                writer.close();

                            } catch (Exception e) {
                            }


                        }
                        else if(menuItem.getTitle().equals("Ascending Date")) { // Ascending Sort
                            // Sort Ingredient List //
                            Collections.sort(ingredientList, new Comparator<String>() {
                                DateFormat f = new SimpleDateFormat("M/d/yyyy");
                                @Override
                                public int compare(String o1, String o2) {
                                    try {
                                        return f.parse(o1).compareTo(f.parse(o2));
                                    } catch (ParseException e) {
                                        throw new IllegalArgumentException(e);
                                    }
                                }
                            });

                            // Parse Ingredient List to Save //
                            ArrayList<String> temp3 = new ArrayList<>();
                            for(int j = 0; j < ingredientList.size(); j++){
                                String[] parser = ingredientList.get(j).split("\t\t\t\t\t\t\t\t\t\t\t\t\t");
                                for (String t : parser) {
                                    temp3.add(t);
                                }
                            }

                            // Export Sorted List to File //
                            File file = new File(AC_IngredientPage.this.getFilesDir(), "ingredient");
                            if (!file.exists()) {
                                file.mkdir();
                            }
                            try {
                                File gpxfile = new File(file, "list");
                                FileWriter writer = new FileWriter(gpxfile, false);
                                for(int k = 0; k < temp3.size(); k+=2){
                                    writer.write(temp3.get(k + 1) + "," + temp3.get(k) + "\n");
                                }
                                writer.close();

                            } catch (Exception e) {
                            }

                        }
                        else if(menuItem.getTitle().equals("Descending Date")) { // Descending Sort
                            // Sort Ingredient List //
                            Collections.sort(ingredientList, new Comparator<String>() {
                                DateFormat f = new SimpleDateFormat("M/d/yyyy");
                                @Override
                                public int compare(String o1, String o2) {
                                    try {
                                        return f.parse(o1).compareTo(f.parse(o2));
                                    } catch (ParseException e) {
                                        throw new IllegalArgumentException(e);
                                    }
                                }
                            });

                            Collections.reverse(ingredientList); // reverse order

                            // Parse Ingredient List to Save //
                            ArrayList<String> temp3 = new ArrayList<>();
                            for(int j = 0; j < ingredientList.size(); j++){
                                String[] parser = ingredientList.get(j).split("\t\t\t\t\t\t\t\t\t\t\t\t\t");
                                for (String t : parser) {
                                    temp3.add(t);
                                }
                            }

                            // Export Sorted List to File //
                            File file = new File(AC_IngredientPage.this.getFilesDir(), "ingredient");
                            if (!file.exists()) {
                                file.mkdir();
                            }
                            try {
                                File gpxfile = new File(file, "list");
                                FileWriter writer = new FileWriter(gpxfile, false);
                                for(int k = 0; k < temp3.size(); k+=2){
                                    writer.write(temp3.get(k + 1) + "," + temp3.get(k) + "\n");
                                }
                                writer.close();

                            } catch (Exception e) {
                            }
                        }

                        // Refresh ListView : Refresh Page //

                        Intent intent = new Intent(AC_IngredientPage.this, AC_IngredientPage.class);
                        startActivity(intent);

                        return true;
                    }
                });

                popupMenu.show(); // show sorting algorithm popup menu
            }
        });

        /* Add Ingredient Button */

        addIngredient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AC_IngredientPage.this, AC_AddIngredientPage.class);
                startActivity(intent);
            }
        });

        /* Remove Ingredient Button */

        removeIngredient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AC_IngredientPage.this, AC_RemoveIngredientPage.class);
                startActivity(intent);
            }
        });
    }

    /* Read from Ingredient File */

    private void readFile() {
        ArrayList<String> holder = new ArrayList<>();
        holder.clear();
        File fileEvents = new File(AC_IngredientPage.this.getFilesDir() + "/ingredient/list");

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
                for(int counter = 0; counter < holder.size(); counter += 2) { // for each extracted item, format listView row (date, product)
                    ingredientList.add(holder.get(counter + 1) + "\t\t\t\t\t\t\t\t\t\t\t\t\t" + holder.get(counter));
                }

                br.close(); // close ingredient list file
            } catch (IOException e) {
            }
        }
    }

    /* Back Button Navigation */

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AC_IngredientPage.this, MainActivity.class);
        startActivity(intent);
    }

}