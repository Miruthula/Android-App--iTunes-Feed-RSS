package com.example.yzhuo.homeowork4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    final static String title = "Yi Personal App";
    final static String audioBooks= "Audio Books";
    final static String books= "Books";
    final static String iosApp= "iOS Apps";
    final static String ituneU= "iTunes U";
    final static String macApp= "Mac Apps";
    final static String movie= "Movies";
    final static String podcast= "PodCasts";
    final static String tvShows= "TV Shows";

    final static String audioBooksTime= "Audio Books Time";
    final static String booksTime= "Books Time";
    final static String iosAppTime= "iOS Apps Time";
    final static String ituneUTime= "iTunes U Time";
    final static String macAppTime= "Mac Apps Time";
    final static String movieTime= "Movies Time";
    final static String podcastTime= "PodCasts Time";
    final static String tvShowsTime= "TV Shows Time";

    final static String AUDIO_BOOK_URL ="https://itunes.apple.com/us/rss/topaudiobooks/limit=25/json";
    final static String BOOK_URL ="https://itunes.apple.com/us/rss/topfreeebooks/limit=25/json";
    final static String IOS_APP_URL ="https://itunes.apple.com/us/rss/newapplications/limit=25/json";
    final static String ITUNES_U_URL ="https://itunes.apple.com/us/rss/topitunesucollections/limit=25/json";
    final static String MAC_APP_URL ="https://itunes.apple.com/us/rss/topfreemacapps/limit=25/json";
    final static String MOVIES_URL ="https://itunes.apple.com/us/rss/topmovies/limit=25/json";
    final static String PODCASTS_URL ="https://itunes.apple.com/us/rss/toppodcasts/limit=25/json";
    final static String TV_SHOW_URL ="https://itunes.apple.com/us/rss/toptvepisodes/limit=25/json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        SharedPreferences myFile = getSharedPreferences(title,0);
        SharedPreferences.Editor editor = myFile.edit();
        editor.putString(audioBooks," Test 1");
        editor.putString(books,"");
        editor.putString(iosApp,"");
        editor.putString(ituneU,"");
        editor.putString(macApp,"");
        editor.putString(movie,"");
        editor.putString(podcast,"");
        editor.putString(tvShows,"");
        editor.commit();

        findViewById(R.id.audioBooks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MediaListActivity.class);
                intent.putExtra(title, "Audio Books");
                intent.putExtra(audioBooks,AUDIO_BOOK_URL);
                startActivity(intent);
            }
        });

        findViewById(R.id.books).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MediaListActivity.class);
                intent.putExtra(title,"Books");
                intent.putExtra(books,BOOK_URL);
                startActivity(intent);
            }
        });


        findViewById(R.id.iosApps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MediaListActivity.class);
                intent.putExtra(title,"iOS Apps");
                intent.putExtra(iosApp,IOS_APP_URL);
                startActivity(intent);
            }
        });

        findViewById(R.id.itunesU).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MediaListActivity.class);
                intent.putExtra(title,"iTunes U");
                intent.putExtra(ituneU,ITUNES_U_URL);
                startActivity(intent);
            }
        });

        findViewById(R.id.macApps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MediaListActivity.class);
                intent.putExtra(title,"Mac Apps");
                intent.putExtra(macApp,MAC_APP_URL);
                startActivity(intent);
            }
        });

        findViewById(R.id.movies).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MediaListActivity.class);
                intent.putExtra(title,"Movies");
                intent.putExtra(movie,MOVIES_URL);
                startActivity(intent);
            }
        });

        findViewById(R.id.podcasts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MediaListActivity.class);
                intent.putExtra(title,"PodCasts");
                intent.putExtra(podcast, PODCASTS_URL);
                startActivity(intent);
            }
        });

        findViewById(R.id.tvShows).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MediaListActivity.class);
                intent.putExtra(title,"TV Shows");
                intent.putExtra(tvShows,TV_SHOW_URL);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
