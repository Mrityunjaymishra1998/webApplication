package com.example.webrequestusinglibraries;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class productData extends AppCompatActivity {

    int cid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_data);
        Intent intent = getIntent();
        cid=intent.getIntExtra("cid",0);
        Log.i("myApp","In Product cid:"+cid);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute();
    }

    class DownloadTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ListView myList = findViewById(R.id.myList2);
            ArrayList<Product> myProductList = new ArrayList<>();
            try {
                JSONArray myArray = new JSONArray(s);
                for (int i = 0; i < myArray.length(); i++) {

                    Product product =new Product();
                    JSONObject myObj = myArray.getJSONObject(i);
                    product.setId(myObj.getInt("productid"));
                    product.setName(myObj.getString("name"));
                    product.setPrice(myObj.getDouble("price"));

                    JSONArray imagesJson = myObj.getJSONArray("images");
                    ArrayList<String> images = new ArrayList<>();

                    for(int j=0;j<imagesJson.length();j++)
                    {
                        images.add(imagesJson.getJSONObject(j).getString("url"));
                    }

                    product.setImages(images);
                    myProductList.add(product);
                }

                Log.i("MyApp","Number of Products : " + myProductList.size());
                ProductAdapter productAdapter = new ProductAdapter(productData.this, myProductList);
                myList.setAdapter(productAdapter);

            } catch(Exception e){
                    e.printStackTrace();
                    Log.i("MyApp",e.getMessage());
                }

            }

        @Override
        protected String doInBackground(String... strings) {

            try {

               URL url = new URL("http://gadgetworld.000webhostapp.com/getproducts.php?cid="+cid);
               //URL url = new URL("http://gadgetworld.000webhostapp.com/getproducts.php?cid=7");

                Log.i("myApp",""+url);
                HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(200000);
                urlConnection.setReadTimeout(200000);
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader =  new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line = bufferedReader.readLine();
                String myData = line;

                while (line!=null)
                {
                    myData +=line;
                    Log.i("myApp",myData);
                    line=bufferedReader.readLine();
                }
                return myData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
