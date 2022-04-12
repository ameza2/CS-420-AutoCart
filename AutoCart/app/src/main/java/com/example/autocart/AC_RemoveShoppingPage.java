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

import java.io.FileWriter;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import android.util.Log;
import android.widget.Toast;

public class AC_RemoveShoppingPage extends AppCompatActivity {

    ArrayList<String> shoppingList;
    Button removeProduct;
    ListView output;
    ArrayAdapter<String> adapter;
    ArrayList<Boolean> productPressedList = new ArrayList<>(); // array consisting of buttons pressed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_removeshoppingpage);

        shoppingList = new ArrayList<>();

        removeProduct = (Button)findViewById(R.id.shoppingRemove);
        output = (ListView)findViewById(R.id.outputList);

        readFile();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, shoppingList);
        output.setAdapter(adapter);

        removeProduct.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < adapter.getCount(); i++) {
                    if(productPressedList.get(i)){
                        //Find string to remove
                        String toremove = shoppingList.get(i);

                        //Set to  "\t\t\t\t\t\t\t\t\t\t\t\t\t" so parser will ignore
                        shoppingList.set(i, "\t\t\t\t\t\t\t\t\t\t\t\t\t");
                        adapter.notifyDataSetChanged();

//                        Log.d("ingredientlist size a", ""+ ingredientList.size());
//                        Log.d("toremove", toremove);

                        //Parse ingredientList
                        ArrayList<String> temp = new ArrayList<>();
                        for(int j = 0; j < shoppingList.size(); j++){
                            String[] parser = shoppingList.get(j).split("\t\t\t\t\t\t\t\t\t\t\t\t\t");
                            for (String t : parser) {
                                temp.add(t);
                            }
                        }

                        //Save file
                        File file = new File(AC_RemoveShoppingPage.this.getFilesDir(), "shopping");
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        try {
                            File gpxfile = new File(file, "list");
                            FileWriter writer = new FileWriter(gpxfile, false);
                            for(int k = 0; k < temp.size(); k++){
                                writer.write(temp.get(k) + "\n");
                            }
                            writer.close();

                        } catch (Exception e) {
                        }
                    }
                }

                Toast.makeText(AC_RemoveShoppingPage.this, "Product Entry Removed", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AC_RemoveShoppingPage.this, AC_ShoppingPage.class);
                startActivity(intent);
            }
        });

        output.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(productPressedList.get(position)){ // If it is already selected
                    output.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                    productPressedList.set(position, false);
                }
                else{ // If it isn't
                    output.getChildAt(position).setBackgroundColor(Color.RED);
                    productPressedList.set(position, true);
                }
//                Log.d("Selected", output.getChildAt(position).toString());
            }
        });
    }

    private void readFile() {
        ArrayList<String> holder = new ArrayList<>();
        holder.clear();
        File fileEvents = new File(AC_RemoveShoppingPage.this.getFilesDir() + "/shopping/list");

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
                    productPressedList.add(false);
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
        Intent intent = new Intent(AC_RemoveShoppingPage.this, AC_ShoppingPage.class);
        startActivity(intent);
    }
}