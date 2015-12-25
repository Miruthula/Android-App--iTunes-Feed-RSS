package com.example.yzhuo.homeowork4;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MediaListActivity extends AppCompatActivity {

    final long twoMinutes = 120000;
    String dataFinal;
    String url;
    int index;
    int ready =0;
    int deleteCount = 0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences myFile = getSharedPreferences(MainActivity.title, 0);
        SharedPreferences.Editor editor = myFile.edit();
        editor.putString(getIntent().getExtras().getString(MainActivity.title), dataFinal);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_list);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle(getIntent().getExtras().getString(MainActivity.title));

        setURL();
        new loading().execute();

    }

    private void setURL() {
        switch(getIntent().getExtras().getString(MainActivity.title)) {
            case "Books":
                url = getIntent().getExtras().getString(MainActivity.books);
                Log.d("URL Selected:", "BOOK_URL");
                break;

            case "Audio Books":
                url = getIntent().getExtras().getString(MainActivity.audioBooks);
                Log.d("URL Selected:", "Audio Books");
                break;

            case "iOS Apps":
                url = getIntent().getExtras().getString(MainActivity.iosApp);
                Log.d("URL Selected:", "iOS Apps");
                break;

            case "iTunes U":
                url = getIntent().getExtras().getString(MainActivity.ituneU);
                Log.d("URL Selected:", "iTunes U");
                break;

            case "Mac Apps":
                url = getIntent().getExtras().getString(MainActivity.macApp);
                Log.d("URL Selected:", "Mac Apps");
                break;

            case "Movies":
                url = getIntent().getExtras().getString(MainActivity.movie);
                Log.d("URL Selected:", "Movies");
                break;

            case "PodCasts":
                url = getIntent().getExtras().getString(MainActivity.podcast);
                Log.d("URL Selected:", "PodCasts");
                break;

            case "TV Shows":
                url = getIntent().getExtras().getString(MainActivity.tvShows);
                Log.d("URL Selected:", "TV Shows");
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_media_list, menu);
        return true;
    }


    private class loading extends AsyncTask<Void,Void,ArrayList<JSONObject>> {
        ProgressDialog progressDialog;

        @Override
        protected ArrayList<JSONObject> doInBackground(Void... params) {
            SharedPreferences myFile = getSharedPreferences(MainActivity.title, 0);
            String data = myFile.getString(getIntent().getExtras().getString(MainActivity.title), null);
            Long time= myFile.getLong(getIntent().getExtras().getString(MainActivity.title) + " Time", 0);
            Long difference = System.currentTimeMillis() - time;
            Log.d("pTime:",""+difference);

            if(data.equals("") ||difference>twoMinutes || time == 0){
                download();
                dataFinal = myFile.getString(getIntent().getExtras().getString(MainActivity.title), null);
                Log.d("Display data", data);
                Log.d("pTime:", "" + difference);
                Log.d("fresh","checked");
            } else {
                Log.d("repeated","checked");
                dataFinal = myFile.getString(getIntent().getExtras().getString(MainActivity.title), null);
                Log.d("Display data", data);
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ready = 0;
            progressDialog = new ProgressDialog(MediaListActivity.this);
            progressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Downloading...");
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(ArrayList<JSONObject> medias) {
            super.onPostExecute(medias);
            progressDialog.dismiss();
            display();
        }
    }




    private void download() {
        if(isConnectedOnline()) {
            try {
                URL urlThis = new URL(url);
                HttpURLConnection con = (HttpURLConnection) urlThis.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                int statusCode = con.getResponseCode();

                if (statusCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine())!=null){
                        sb.append(line);
                        //Log.d("data", line);
                    }
                    Log.d("data2",sb.toString());
                    callParse(sb.toString());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(MediaListActivity.this,"No Network",Toast.LENGTH_LONG).show();
        }
    }

    private void callParse(String in) throws JSONException {
        SharedPreferences myFile = getSharedPreferences(MainActivity.title, 0);
        SharedPreferences.Editor editor = myFile.edit();
        switch(getIntent().getExtras().getString(MainActivity.title)) {
            case "Books":
                editor.putString(MainActivity.books, ParseBooks.parse.parseBooks(in));
                editor.putLong(MainActivity.booksTime, System.currentTimeMillis());
                editor.commit();
                break;

            case "Audio Books":
                editor.putString(MainActivity.audioBooks, ParseAudioBooks.parse.parseAudioBooks(in));
                editor.putLong(MainActivity.audioBooksTime, System.currentTimeMillis());
                editor.commit();
                break;

            case "iOS Apps":
                editor.putString(MainActivity.iosApp, ParseIOSApp.parse.parseIOSApp(in));
                editor.putLong(MainActivity.iosAppTime, System.currentTimeMillis());
                editor.commit();
                break;

            case "iTunes U":
                editor.putString(MainActivity.ituneU, ParseItunesU.parse.parseItunesU(in));
                editor.putLong(MainActivity.ituneUTime, System.currentTimeMillis());
                editor.commit();
                break;

            case "Mac Apps":
                editor.putString(MainActivity.macApp, ParseMacApp.parse.parseMacApp(in));
                editor.putLong(MainActivity.macAppTime, System.currentTimeMillis());
                editor.commit();
                break;

            case "Movies":
                editor.putString(MainActivity.movie, ParseMovies.parse.parseMovies(in));
                editor.putLong(MainActivity.movieTime, System.currentTimeMillis());
                editor.commit();
                break;

            case "PodCasts":
                editor.putString(MainActivity.podcast, ParsePodcasts.parse.parsePodcasts(in));
                editor.putLong(MainActivity.podcastTime, System.currentTimeMillis());
                editor.commit();
                break;

            case "TV Shows":
                editor.putString(MainActivity.tvShows, ParseTVShows.parse.parseTVShows(in));
                editor.putLong(MainActivity.tvShowsTime, System.currentTimeMillis());
                editor.commit();
                break;

        }
    }

    public boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }// end isConnectedOnline

    private void display() {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(MediaListActivity.this);
        progressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Gson gson = new Gson();
        final LinearLayout ly = (LinearLayout) findViewById(R.id.mediaListLinear);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5,10,10,10);
        switch (getIntent().getExtras().getString(MainActivity.title)) {
            case "Books":
                final ArrayList<Books> myBook =gson.fromJson(dataFinal, new TypeToken<List<Books>>(){}.getType());
                //change
                for (int i = 0; i < myBook.size(); i++) {
                    index = i;
                    final LinearLayout horizontal = new LinearLayout(this);
                    horizontal.setOrientation(LinearLayout.HORIZONTAL);
                    final TextView bookTV = new TextView(this);
                    final ImageView bookImage = new ImageView(this);
                    bookTV.setId(i);
                    //change
                    bookTV.setText(myBook.get(i).getTitle());
                    bookTV.setGravity(Gravity.CENTER_VERTICAL);
                    bookTV.setPadding(16, 16, 16, 16);
                    Picasso.with(this)
                            .load(myBook.get(i).getSmallImage())
                            .resize(200, 200)
                            .centerCrop()
                            .into(bookImage);
                    horizontal.addView(bookImage);
                    horizontal.addView(bookTV);
                    ly.addView(horizontal,params);
                    horizontal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MediaListActivity.this, DetailedMediaActivity.class);
                            //change
                            intent.putExtra(MainActivity.title, MainActivity.books);
                            intent.putExtra("index", bookTV.getId());
                            startActivity(intent);
                        }
                    });
                    horizontal.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {

                            deleteCount++;
                            Log.d("delete ID", "" + bookTV.getId());
                            myBook.remove(bookTV.getId());
                            ly.removeView(horizontal);
                            ly.invalidate();
                            Gson gson = new Gson();
                            dataFinal = gson.toJson(myBook);
                            //change

                            return true;
                        }
                    });
                }

                break;

            case "Audio Books":
                final ArrayList<AudioBooks> myAudioBook =gson.fromJson(dataFinal, new TypeToken<List<AudioBooks>>(){}.getType());
                //change
                for (int i = 0; i < myAudioBook.size(); i++) {

                    index = i;
                    final LinearLayout horizontal = new LinearLayout(this);
                    horizontal.setOrientation(LinearLayout.HORIZONTAL);
                    final TextView bookTV = new TextView(this);
                    final ImageView bookImage = new ImageView(this);
                    bookTV.setId(i);
                    //change
                    bookTV.setText(myAudioBook.get(i).getTitle());
                    bookTV.setGravity(Gravity.CENTER_VERTICAL);
                    bookTV.setPadding(16, 16, 16, 16);
                    Picasso.with(this)
                            //change
                            .load(myAudioBook.get(i).getSmallImage())
                            .resize(200, 200)
                            .centerCrop()
                            .into(bookImage);
                    horizontal.addView(bookImage);
                    horizontal.addView(bookTV);
                    ly.addView(horizontal,params);
                    horizontal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MediaListActivity.this, DetailedMediaActivity.class);
                            //change
                            intent.putExtra(MainActivity.title, MainActivity.audioBooks);
                            intent.putExtra("index", bookTV.getId());
                            startActivity(intent);
                        }
                    });
                    horizontal.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            deleteCount++;
                            SharedPreferences myFile = getSharedPreferences(MainActivity.title, 0);
                            SharedPreferences.Editor editor = myFile.edit();
                            Log.d("delete ID",""+bookTV.getId());
                            //change
                            myAudioBook.remove(bookTV.getId());
                            ly.removeView(horizontal);
                            ly.invalidate();
                            Gson gson = new Gson();
                            dataFinal = gson.toJson(myAudioBook);
                            return true;
                        }
                    });
                }
                break;

            case "iOS Apps":
                final ArrayList<IOSApp> myIosApp =gson.fromJson(dataFinal, new TypeToken<List<IOSApp>>(){}.getType());
                //change
                for (int i = 0; i < myIosApp.size(); i++) {

                    index = i;
                    final LinearLayout horizontal = new LinearLayout(this);
                    horizontal.setOrientation(LinearLayout.HORIZONTAL);
                    final TextView bookTV = new TextView(this);
                    final ImageView bookImage = new ImageView(this);
                    bookTV.setId(i);
                    //change
                    bookTV.setText(myIosApp.get(i).getTitle());
                    bookTV.setGravity(Gravity.CENTER_VERTICAL);
                    bookTV.setPadding(16, 16, 16, 16);
                    Picasso.with(this)
                            //change
                            .load(myIosApp.get(i).getSmallImage())
                            .resize(200, 200)
                            .centerCrop()
                            .into(bookImage);
                    horizontal.addView(bookImage);
                    horizontal.addView(bookTV);
                    ly.addView(horizontal,params);
                    horizontal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MediaListActivity.this, DetailedMediaActivity.class);
                            //change
                            intent.putExtra(MainActivity.title, MainActivity.iosApp);
                            intent.putExtra("index", bookTV.getId());
                            startActivity(intent);
                        }
                    });
                    horizontal.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            deleteCount++;
                            SharedPreferences myFile = getSharedPreferences(MainActivity.title, 0);
                            SharedPreferences.Editor editor = myFile.edit();
                            Log.d("delete ID",""+bookTV.getId());
                            //change
                            myIosApp.remove(bookTV.getId());
                            ly.removeView(horizontal);
                            ly.invalidate();
                            Gson gson = new Gson();
                            dataFinal = gson.toJson(myIosApp);
                            return true;
                        }
                    });
                }
                break;

            case "iTunes U":
                final ArrayList<ItunesU> myItuneU =gson.fromJson(dataFinal, new TypeToken<List<ItunesU>>(){}.getType());
                //change
                for (int i = 0; i < myItuneU.size(); i++) {

                    index = i;
                    final LinearLayout horizontal = new LinearLayout(this);
                    horizontal.setOrientation(LinearLayout.HORIZONTAL);
                    final TextView bookTV = new TextView(this);
                    final ImageView bookImage = new ImageView(this);
                    bookTV.setId(i);
                    //change
                    bookTV.setText(myItuneU.get(i).getTitle());
                    bookTV.setGravity(Gravity.CENTER_VERTICAL);
                    bookTV.setPadding(16, 16, 16, 16);
                    Picasso.with(this)
                            //change
                            .load(myItuneU.get(i).getSmallImage())
                            .resize(200, 200)
                            .centerCrop()
                            .into(bookImage);
                    horizontal.addView(bookImage);
                    horizontal.addView(bookTV);
                    ly.addView(horizontal,params);
                    horizontal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MediaListActivity.this, DetailedMediaActivity.class);
                            //change
                            intent.putExtra(MainActivity.title, MainActivity.ituneU);
                            intent.putExtra("index", bookTV.getId());
                            startActivity(intent);
                        }
                    });
                    horizontal.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            deleteCount++;
                            SharedPreferences myFile = getSharedPreferences(MainActivity.title, 0);
                            SharedPreferences.Editor editor = myFile.edit();
                            Log.d("delete ID",""+bookTV.getId());
                            //change
                            myItuneU.remove(bookTV.getId());
                            ly.removeView(horizontal);
                            ly.invalidate();
                            Gson gson = new Gson();
                            dataFinal = gson.toJson(myItuneU);
                            return true;
                        }
                    });
                }
                break;

            case "Mac Apps":
                final ArrayList<MacApp> myMacApp =gson.fromJson(dataFinal, new TypeToken<List<MacApp>>(){}.getType());
                //change
                for (int i = 0; i < myMacApp.size(); i++) {

                    index = i;
                    final LinearLayout horizontal = new LinearLayout(this);
                    horizontal.setOrientation(LinearLayout.HORIZONTAL);
                    final TextView bookTV = new TextView(this);
                    final ImageView bookImage = new ImageView(this);
                    bookTV.setId(i);
                    //change
                    bookTV.setText(myMacApp.get(i).getTitle());
                    bookTV.setGravity(Gravity.CENTER_VERTICAL);
                    bookTV.setPadding(16, 16, 16, 16);
                    Picasso.with(this)
                            //change
                            .load(myMacApp.get(i).getSmallImage())
                            .resize(200, 200)
                            .centerCrop()
                            .into(bookImage);
                    horizontal.addView(bookImage);
                    horizontal.addView(bookTV);
                    ly.addView(horizontal,params);
                    horizontal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MediaListActivity.this, DetailedMediaActivity.class);
                            //change
                            intent.putExtra(MainActivity.title, MainActivity.macApp);
                            intent.putExtra("index", bookTV.getId());
                            startActivity(intent);
                        }
                    });
                    horizontal.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            deleteCount++;
                            SharedPreferences myFile = getSharedPreferences(MainActivity.title, 0);
                            SharedPreferences.Editor editor = myFile.edit();
                            Log.d("delete ID",""+bookTV.getId());
                            //change
                            myMacApp.remove(bookTV.getId());
                            ly.removeView(horizontal);
                            ly.invalidate();
                            Gson gson = new Gson();
                            dataFinal = gson.toJson(myMacApp);
                            return true;
                        }
                    });
                }

                break;

            case "Movies":
                final ArrayList<Movies> myMovie =gson.fromJson(dataFinal, new TypeToken<List<Movies>>(){}.getType());
                //change
                for (int i = 0; i < myMovie.size(); i++) {

                    index = i;
                    final LinearLayout horizontal = new LinearLayout(this);
                    horizontal.setOrientation(LinearLayout.HORIZONTAL);
                    final TextView bookTV = new TextView(this);
                    final ImageView bookImage = new ImageView(this);
                    bookTV.setId(i);
                    //change
                    bookTV.setText(myMovie.get(i).getTitle());
                    bookTV.setGravity(Gravity.CENTER_VERTICAL);
                    bookTV.setPadding(16, 16, 16, 16);
                    Picasso.with(this)
                            //change
                            .load(myMovie.get(i).getSmallImage())
                            .resize(200, 200)
                            .centerCrop()
                            .into(bookImage);
                    horizontal.addView(bookImage);
                    horizontal.addView(bookTV);
                    ly.addView(horizontal,params);
                    horizontal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MediaListActivity.this, DetailedMediaActivity.class);
                            //change
                            intent.putExtra(MainActivity.title, MainActivity.movie);
                            intent.putExtra("index", bookTV.getId());
                            startActivity(intent);
                        }
                    });
                    horizontal.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            deleteCount++;
                            SharedPreferences myFile = getSharedPreferences(MainActivity.title, 0);
                            SharedPreferences.Editor editor = myFile.edit();
                            Log.d("delete ID", "" + bookTV.getId());
                            //change
                            myMovie.remove(bookTV.getId());
                            ly.removeView(horizontal);
                            ly.invalidate();
                            Gson gson = new Gson();
                            dataFinal = gson.toJson(myMovie);
                            return true;
                        }
                    });
                }

                break;

            case "PodCasts":
                final ArrayList<Podcasts> myPodcast =gson.fromJson(dataFinal, new TypeToken<List<Podcasts>>(){}.getType());
                //change
                for (int i = 0; i < myPodcast.size(); i++) {

                    index = i;
                    final LinearLayout horizontal = new LinearLayout(this);
                    horizontal.setOrientation(LinearLayout.HORIZONTAL);
                    final TextView bookTV = new TextView(this);
                    final ImageView bookImage = new ImageView(this);
                    bookTV.setId(i);
                    //change
                    bookTV.setText(myPodcast.get(i).getTitle());
                    bookTV.setGravity(Gravity.CENTER_VERTICAL);
                    bookTV.setPadding(16, 16, 16, 16);
                    Picasso.with(this)
                            //change
                            .load(myPodcast.get(i).getSmallImage())
                            .resize(200, 200)
                            .centerCrop()
                            .into(bookImage);
                    horizontal.addView(bookImage);
                    horizontal.addView(bookTV);
                    ly.addView(horizontal,params);
                    horizontal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MediaListActivity.this, DetailedMediaActivity.class);
                            //change
                            intent.putExtra(MainActivity.title, MainActivity.podcast);
                            intent.putExtra("index", bookTV.getId());
                            startActivity(intent);
                        }
                    });
                    horizontal.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            deleteCount++;
                            SharedPreferences myFile = getSharedPreferences(MainActivity.title, 0);
                            SharedPreferences.Editor editor = myFile.edit();
                            Log.d("delete ID",""+bookTV.getId());
                            //change
                            myPodcast.remove(bookTV.getId());
                            ly.removeView(horizontal);
                            ly.invalidate();
                            Gson gson = new Gson();
                            dataFinal = gson.toJson(myPodcast);
                            return true;
                        }
                    });
                }

                break;

            case "TV Shows":
                final ArrayList<TVShows> myTVShows =gson.fromJson(dataFinal, new TypeToken<List<TVShows>>(){}.getType());
                //change
                for (int i = 0; i < myTVShows.size(); i++) {

                    index = i;
                    final LinearLayout horizontal = new LinearLayout(this);
                    horizontal.setOrientation(LinearLayout.HORIZONTAL);
                    final TextView bookTV = new TextView(this);
                    final ImageView bookImage = new ImageView(this);
                    bookTV.setId(i);
                    //change
                    bookTV.setText(myTVShows.get(i).getTitle());
                    bookTV.setGravity(Gravity.CENTER_VERTICAL);
                    bookTV.setPadding(16, 16, 16, 16);
                    Picasso.with(this)
                            //change
                            .load(myTVShows.get(i).getSmallImage())
                            .resize(200, 200)
                            .centerCrop()
                            .into(bookImage);
                    horizontal.addView(bookImage);
                    horizontal.addView(bookTV);
                    ly.addView(horizontal,params);
                    horizontal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MediaListActivity.this, DetailedMediaActivity.class);
                            //change
                            intent.putExtra(MainActivity.title, MainActivity.tvShows);
                            intent.putExtra("index", bookTV.getId());
                            startActivity(intent);
                        }
                    });
                    horizontal.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            deleteCount++;
                            SharedPreferences myFile = getSharedPreferences(MainActivity.title, 0);
                            SharedPreferences.Editor editor = myFile.edit();
                            Log.d("delete ID",""+bookTV.getId());
                            //change
                            myTVShows.remove(bookTV.getId());
                            ly.removeView(horizontal);
                            ly.invalidate();
                            Gson gson = new Gson();
                            dataFinal = gson.toJson(myTVShows);
                            return true;
                        }
                    });
                }
                break;
        }
        progressDialog.dismiss();
    }
}
