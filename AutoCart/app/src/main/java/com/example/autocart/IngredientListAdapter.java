package com.example.autocart;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class IngredientListAdapter extends ArrayAdapter<DataIngredient> {
    private static final String TAG = "IngredientListAdapter";
    private Context mContext;
    int mResource;

    public IngredientListAdapter(Context context, int resource, ArrayList<DataIngredient> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String name = getItem(position).getName();
        String date = getItem(position).getDate();

        DataIngredient ingredient = new DataIngredient(name, date);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView ingredientName = (TextView) convertView.findViewById(R.id.textView1);
        TextView expDate = (TextView) convertView.findViewById(R.id.textView2);

        ingredientName.setText(name);
        expDate.setText(date);

        return convertView;

    }
}
