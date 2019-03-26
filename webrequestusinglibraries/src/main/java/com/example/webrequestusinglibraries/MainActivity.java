package com.example.webrequestusinglibraries;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
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

        MyDownloadTask task = new MyDownloadTask();
        task.execute();
    }

    class MyDownloadTask extends AsyncTask<String,Void,String>
    {

        @Override
        protected void onPostExecute(String s) {
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

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL("http://gadgetworld.000webhostapp.com/getcategories.php");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setConnectTimeout(200000);
                connection.setReadTimeout(200000);

                InputStream inputStream = connection.getInputStream();
                InputStreamReader reader =new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line = bufferedReader.readLine();
                String data = line;

                while(line!=null)
                {
                    data+=line;
                    line=bufferedReader.readLine();
                    Log.i("myApp","data line"+ data);
                }

                return data;

            } catch (MalformedURLException e) {
                Log.i("myApp","error");
                e.printStackTrace();
                return null;
            } catch (Exception e) {
                Log.i("myApp","error");
                e.printStackTrace();
                return null;
            }



        }
    }
}
