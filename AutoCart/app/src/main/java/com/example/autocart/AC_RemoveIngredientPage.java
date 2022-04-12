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

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import android.util.Log;
import android.widget.Toast;

public class AC_RemoveIngredientPage extends AppCompatActivity {

    ArrayList<String> ingredientList;
    Button removeIngredient;
    ListView output;
    ArrayAdapter<String> adapter;
    ArrayList<Boolean> ingredientPressedList = new ArrayList<>(); // array consisting of buttons pressed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_removeingredientpage);

        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        TextView currDate = (TextView)findViewById(R.id.currentDate);
        currDate.setText(date_n);

        ingredientList = new ArrayList<>();

        removeIngredient = (Button)findViewById(R.id.ingredientRemove);
        output = (ListView)findViewById(R.id.outputList);

        readFile();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ingredientList);
        output.setAdapter(adapter);

        removeIngredient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < adapter.getCount(); i++) {
                    if(ingredientPressedList.get(i)){
                        //Find string to remove
                        String toremove = ingredientList.get(i);

                        //Set to  "\t\t\t\t\t\t\t\t\t\t\t\t\t" so parser will ignore
                        ingredientList.set(i, "\t\t\t\t\t\t\t\t\t\t\t\t\t");
                        adapter.notifyDataSetChanged();

//                        Log.d("ingredientlist size a", ""+ ingredientList.size());
//                        Log.d("toremove", toremove);

                        //Parse ingredientList
                        ArrayList<String> temp = new ArrayList<>();
                        for(int j = 0; j < ingredientList.size(); j++){
                            String[] parser = ingredientList.get(j).split("\t\t\t\t\t\t\t\t\t\t\t\t\t");
                            for (String t : parser) {
                                temp.add(t);
                            }
                        }

                        //Save file
                        File file = new File(AC_RemoveIngredientPage.this.getFilesDir(), "ingredient");
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        try {
                            File gpxfile = new File(file, "list");
                            FileWriter writer = new FileWriter(gpxfile, false);
                            for(int k = 0; k < temp.size(); k+=2){
                                writer.write(temp.get(k + 1) + "," + temp.get(k) + "\n");
                            }
                            writer.close();

                        } catch (Exception e) {
                        }
                    }
                }

                Toast.makeText(AC_RemoveIngredientPage.this, "Ingredient Entry Removed", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AC_RemoveIngredientPage.this, AC_IngredientPage.class);
                startActivity(intent);
            }
        });

        output.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(ingredientPressedList.get(position)){ // If it is already selected
                    output.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                    ingredientPressedList.set(position, false);
                }
                else{ // If it isn't
                    output.getChildAt(position).setBackgroundColor(Color.RED);
                    ingredientPressedList.set(position, true);
                }
//                Log.d("Selected", output.getChildAt(position).toString());
            }
        });
    }

    private void readFile() {
        ArrayList<String> holder = new ArrayList<>();
        holder.clear();
        File fileEvents = new File(AC_RemoveIngredientPage.this.getFilesDir() + "/ingredient/list");

        if(!fileEvents.exists()) {

        }
        else {
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileEvents));
                String line;

                while ((line = br.readLine()) != null) {
                    String[] temp = line.split(",");
                    for (String t : temp) {
                        holder.add(t);
                    }
                }
                for(int counter = 0; counter < holder.size(); counter += 2) {
                    ingredientList.add(holder.get(counter + 1) + "\t\t\t\t\t\t\t\t\t\t\t\t\t" + holder.get(counter));
                    ingredientPressedList.add(false);
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
        Intent intent = new Intent(AC_RemoveIngredientPage.this, AC_IngredientPage.class);
        startActivity(intent);
    }
}