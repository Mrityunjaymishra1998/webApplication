package com.example.simarbedi.webrequestdemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
    }


    public void ParseData(String s)
    {
        final ArrayList<Category> categories = new ArrayList<>();
        try
        {
            JSONArray array = new JSONArray(s);
            for(int i=0;i<array.length();i++)
            {
                Category c = new Category();
                JSONObject categoryObject = array.getJSONObject(i);
                c.setId(categoryObject.getInt("categoryid"));
                c.setName(categoryObject.getString("name"));
                categories.add(c);
                Log.i("myApp",""+c.getId()+ " "+c.getName()+" "+categories.get(i).toString());
            }
            CategoryAdapter adapter = new CategoryAdapter(MainActivity.this,categories);
            Log.i("myApp","post execute success");
            GridView listView = findViewById(R.id.myList);

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int cid=categories.get(i).getId();
                    Log.i("myApp","in main cid:"+cid);
                    Intent intent = new Intent(MainActivity.this,productData.class);
                    intent.putExtra("cid",cid);
                    startActivity(intent);
                }
            });

            Log.i("myApp","post execute success2");
        }
        catch(Exception e)
        {
            Log.i("myApp","post execute error");
        }

    }
}
