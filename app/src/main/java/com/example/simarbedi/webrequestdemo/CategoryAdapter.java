package com.example.simarbedi.webrequestdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<Category>
{
    protected CategoryAdapter(Context context, ArrayList<Category> categories)
    {
        super(context,0,categories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.i("myApp","in adapter");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_data,parent,false);

        final Category category = getItem(position);

        TextView text2 = view.findViewById(R.id.myData);

        text2.setText(category.getName());

       return view;
    }
}
