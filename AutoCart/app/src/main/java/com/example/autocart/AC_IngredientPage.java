package com.example.autocart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import android.util.Log;

public class AC_IngredientPage extends AppCompatActivity {

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

        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        TextView currDate = (TextView)findViewById(R.id.currentDate);
        currDate.setText(date_n);

        ingredientList = new ArrayList<>();

        addIngredient = (Button)findViewById(R.id.ingredientAdd);
        removeIngredient = (Button)findViewById(R.id.ingredientRemove);
        sortIngredient = (ImageButton)findViewById(R.id.ingredientSort);
        output = (ListView)findViewById(R.id.outputList);

        readFile();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ingredientList);
        output.setAdapter(adapter);

        // Dropdown Menu Logic //

        // Setting onClick behavior to the button
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
                        // Toast message on menu item clicked
                        Toast.makeText(AC_IngredientPage.this, "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });

        ///////////////////////

        ///// Color Logic /////

        //Parse ingredientList
        ArrayList<String> temp = new ArrayList<>();
        for(int j = 0; j < ingredientList.size(); j++){
            String[] parser = ingredientList.get(j).split("\t\t\t\t\t\t\t\t\t\t\t\t\t");
            for (String t : parser) {
                temp.add(t);
            }
        }

        for(int i = 0; i < temp.size(); i+=2){
            Date date1;
            Date date2;
            String curr = new SimpleDateFormat("M/d/yyyy", Locale.getDefault()).format(new Date());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy");

            try {
                date1 = simpleDateFormat.parse(curr);
                date2 = simpleDateFormat.parse(temp.get(i));

                //Comparing dates
                long difference = Math.abs(date1.getTime() - date2.getTime());
                long differenceDates = difference / (24 * 60 * 60 * 1000);

                if(date1.compareTo(date2) > 0) {
                    Log.d("option 1","Expired: Red");
                }
                else if((date1.compareTo(date2) < 0) || (date1.compareTo(date2) == 0)){
                    if (differenceDates <= 7) {
                        Log.d("option 2", "Almost Expired : Yellow");
                    }
                    else {
                        Log.d("option 3", "Healthy : Green");
                    }

//                    int indexOf = temp.indexOf(temp.get(i));
//                    output.getChildAt(0).setBackgroundColor(Color.RED);
//                    Log.d("child", ""+output.getChildAt(0));

                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        ///////////////////////

        addIngredient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AC_IngredientPage.this, AC_AddIngredientPage.class);
                startActivity(intent);
            }
        });

        removeIngredient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AC_IngredientPage.this, AC_RemoveIngredientPage.class);
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

                while ((line = br.readLine()) != null) {
                    String[] temp = line.split(",");
                    for (String t : temp) {
                        holder.add(t);
                    }
                }
                for(int counter = 0; counter < holder.size(); counter += 2) {
                    ingredientList.add(holder.get(counter + 1) + "\t\t\t\t\t\t\t\t\t\t\t\t\t" + holder.get(counter));
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
        Intent intent = new Intent(AC_IngredientPage.this, MainActivity.class);
        startActivity(intent);
    }

}