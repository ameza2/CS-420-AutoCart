package com.example.autocart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Button;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import android.text.TextUtils;

public class AC_IngredientPage extends AppCompatActivity {

    ArrayList<DataIngredient> ingredientList;
    Button addIngredient;
    ListView output;
    IngredientListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_ingredientpage);

        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        TextView currDate = (TextView)findViewById(R.id.currentDate);
        currDate.setText(date_n);

        ingredientList = new ArrayList<>();
        addIngredient = (Button)findViewById(R.id.ingredientAdd);
        output = (ListView)findViewById(R.id.outputList);

        ingredientList.clear();
        readFile();
        adapter = new IngredientListAdapter(this, R.layout.adapter_view_layout, ingredientList);
        output.setAdapter(adapter);

        addIngredient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AC_IngredientPage.this, AC_AddIngredientPage.class);
                startActivity(intent);
            }
        });
    }

    private void readFile() {
        ArrayList<String> holder = new ArrayList<>();
        holder.clear();
        File fileEvents = new File(AC_IngredientPage.this.getFilesDir() + "/ingredient/list");

        if(!fileEvents.exists()) {

        }
        else {
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileEvents));
                String line;

                while ((line = br.readLine()) != null){
                    String[] temp = line.split(",");
                    for(String t : temp)
                        holder.add(t);
                    for(int counter = 0; counter < holder.size(); counter += 2) {
//                        ingredientList.add(new DataIngredient(holder.get(counter), holder.get(counter + 1)));
                        System.out.println(holder.size());
                        System.out.println(holder.get(counter));
                        System.out.println(holder.get(counter+1));
                    }
                }
                br.close();
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AC_IngredientPage.this, MainActivity.class);
        startActivity(intent);
    }
}