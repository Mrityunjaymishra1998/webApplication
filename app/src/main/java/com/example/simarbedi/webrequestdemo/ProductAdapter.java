package com.example.simarbedi.webrequestdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.InflaterInputStream;


public class ProductAdapter extends ArrayAdapter<Product> {


    public ProductAdapter(Context context, ArrayList<Product> products) {
        super(context, 0, products);
        Log.i("MyApp",products.size()+"");
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        try {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_data_image, parent, false);

            Product product = getItem(position);
            TextView proId = view.findViewById(R.id.myProId);
            TextView proName = view.findViewById(R.id.myProName);
            TextView proPrice = view.findViewById(R.id.myProPrice);
            ImageView proImage = view.findViewById(R.id.myProImage);
            Log.i("MyApp", "inProduct" + product.getId() + " " + product.getName() + " " + product.getPrice());

            proId.setText(product.getId() + "");
            proName.setText(product.getName());
            proPrice.setText(product.getPrice() + "");

            //Picasso.get().load("http://gadgetworld.000webhostapp.com/images/" + product.getImages().get(0)).into(proImage);

            DownloadImage imageDownloader = new DownloadImage(proImage);
            imageDownloader.execute("http://gadgetworld.000webhostapp.com/images/" + product.getImages().get(0));

            return view;
        }
        catch (Exception e) {
            Log.i("MyApp", e.getMessage());
            return null;
        }


    }

    class DownloadImage extends AsyncTask<String,Void, Bitmap>
    {
        ImageView productImage;
        public DownloadImage(ImageView view)
        {
            productImage=view;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //UI Update
            productImage.setImageBitmap(bitmap);
        }



        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap=null;

            try
            {
                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setReadTimeout(20000);
                con.setConnectTimeout(20000);
                InputStream stream = con.getInputStream();
                bitmap = BitmapFactory.decodeStream(stream);
                return bitmap;
            }
            catch (Exception e)
            {
                return null;
            }
        }
    }
}
