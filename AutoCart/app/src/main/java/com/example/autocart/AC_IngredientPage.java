package com.example.autocart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    ArrayList<String> ingredientList;
    Button addIngredient;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocart_ingredientpage);

        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        TextView currDate = (TextView)findViewById(R.id.currentDate);
        currDate.setText(date_n);

        ingredientList = new ArrayList<>();
        addIngredient = (Button)findViewById(R.id.ingredientAdd);
        output = (TextView)findViewById(R.id.outputList);


        output.setText(readFile());

        addIngredient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AC_IngredientPage.this, AC_AddIngredientPage.class);
                startActivity(intent);
            }
        });
    }

    private String readFile() {

        File fileEvents = new File(AC_IngredientPage.this.getFilesDir() + "/ingredient/list");

        if(!fileEvents.exists()) {

        }
        else {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileEvents));
                String line;

                while ((line = br.readLine()) != null){
                    ingredientList.add(line);
                    text.append(line);
                    text.append('\n');
                }
                br.close();
            } catch (IOException e) {
            }
        }

        String result = TextUtils.join("\n", ingredientList);

        return result;
    }
}