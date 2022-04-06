package com.example.autocart;

import java.util.ArrayList;

public class DataIngredient {

    DataIngredient(ArrayList<String> ingredient,  ArrayList<String> shopping){
        ingredientList = new ArrayList<>(ingredient);
        shoppingList = new ArrayList<>(shopping);
    }
    DataIngredient(String n, String d){
        this.name = n;
        this.expDate = d;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDate(){
        return expDate;
    }

    public void setDate(String date){
        this.expDate = date;
    }



    public String name;
    public String expDate;
    public ArrayList<String> ingredientList;
    public ArrayList<String> shoppingList;

}
