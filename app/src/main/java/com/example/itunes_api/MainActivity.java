package com.example.itunes_api;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.itunes_api.Model.iTuneStuff;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtArtistName, txtType, txtKind, txtCollectionName, txtTrackName;
    ImageView imgArt;
    String imgURL;
    Button btnGetData;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtArtistName = findViewById(R.id.txtArtistName);
        txtType = findViewById(R.id.txtType);
        txtKind = findViewById(R.id.txtKind);
        txtCollectionName = findViewById(R.id.txtCollectionName);
        txtTrackName = findViewById(R.id.txtTrackName);
        imgArt = findViewById(R.id.imgArt);
        btnGetData = findViewById(R.id.btnGetData);

        btnGetData.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        JSONItunesStuffTask jsonItunesStuffTask = new JSONItunesStuffTask(MainActivity.this);
        jsonItunesStuffTask.execute();
    }

    private class JSONItunesStuffTask extends AsyncTask<String, Void, iTuneStuff> {
        Context context;

        ProgressDialog progressDialog;

        public JSONItunesStuffTask(Context context){
            this.context = context;
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            progressDialog.setTitle("Downloading info from iTunes... \n please wait!");
            progressDialog.show();
        }

        @Override
        protected iTuneStuff doInBackground(String... params){
            iTuneStuff itunesStuff = new iTuneStuff();

            ItunesHTTPClient itunesHTTPClient = new ItunesHTTPClient();

            String data = (itunesHTTPClient.getItunesStuffData());

            try {
                itunesStuff = JsonItunesParser.getItunesStuff(data);
                imgURL = itunesStuff.getArtistViewUrl();
                bitmap = ((new ItunesHTTPClient()).getBitmapFromURL(imgURL));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return itunesStuff;
        }

        @Override
        protected void onPostExecute(iTuneStuff iTuneStuff){
            super.onPostExecute(iTuneStuff);

            txtArtistName.setText(iTuneStuff.getArtistName());
            txtType.setText(iTuneStuff.getType());
            txtKind.setText(iTuneStuff.getKind());
            txtCollectionName.setText(iTuneStuff.getCollectionName());
            txtTrackName.setText(iTuneStuff.getTrackName());
            imgArt.setImageBitmap(bitmap);

            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }


        }
    }


}