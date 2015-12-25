package com.example.yzhuo.homeowork4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailedMediaActivity extends AppCompatActivity {
    String media;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_media);

        media = getIntent().getExtras().getString(MainActivity.title);
        index = getIntent().getExtras().getInt("index");

        getDetail();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed_media, menu);
        return true;
    }


    private void getDetail(){

        Gson gson = new Gson();
        SharedPreferences myFile = getSharedPreferences(MainActivity.title, 0);
        String dataFinal = myFile.getString(getIntent().getExtras().getString(MainActivity.title), null);
        final ScrollView sv = (ScrollView) findViewById(R.id.detailScrollView);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final LinearLayout ly = new LinearLayout(this);
        ly.setOrientation(LinearLayout.VERTICAL);
        final TextView title = new TextView(this);
        final ImageView image = new ImageView(this);
        final TextView artist = new TextView(this);
        final TextView summary = new TextView(this);
        final TextView category = new TextView(this);
        final TextView price = new TextView(this);
        final TextView releaseDate = new TextView(this);
        final TextView linkTitle = new TextView(this);
        final TextView link = new TextView(this);
        final TextView duration = new TextView(this);
        final Spannable span;
        LinearLayout.LayoutParams layoutParams;

        switch(media) {
            case "Books":
                final ArrayList<Books> myBook =gson.fromJson(dataFinal, new TypeToken<List<Books>>(){}.getType());



                title.setText(myBook.get(index).getTitle());
                title.setGravity(Gravity.CENTER);
                title.setPadding(16, 16, 16, 16);
                title.setTextSize(20);
                layoutParams = new LinearLayout.LayoutParams(600, 600);
                layoutParams.gravity=Gravity.CENTER;
                image.setLayoutParams(layoutParams);
                Picasso.with(this)
                        .load(myBook.get(index).getLargeImage())
                        .resize(600, 600)
                        .centerCrop()
                        .into(image);
                artist.setText("By: " + myBook.get(index).getArtist());
                releaseDate.setText(myBook.get(index).getReleaseDate());
                releaseDate.setGravity(Gravity.CENTER);
                category.setText("Category: " + myBook.get(index).getCategory());
                price.setText("Price: " + myBook.get(index).getPrice());
                summary.setText("Summary: " + myBook.get(index).getSummary());
                linkTitle.setText("App Link in Store:");
                link.setText(myBook.get(index).getLink());
                link.setTextColor(Color.parseColor("#2D9DC8"));


                ly.addView(title);
                ly.addView(releaseDate);
                ly.addView(image);
                ly.addView(artist);
                ly.addView(price);
                ly.addView(category);
                ly.addView(summary);
                ly.addView(linkTitle);
                ly.addView(link);
                sv.addView(ly);
                sv.invalidate();

                link.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailedMediaActivity.this, PreviewActivity.class);
                        intent.putExtra("link",myBook.get(index).getLink());
                        startActivity(intent);
                    }
                });
                break;

            case "Audio Books":

                final ArrayList<AudioBooks> myAudioBook =gson.fromJson(dataFinal, new TypeToken<List<AudioBooks>>(){}.getType());

                //change
                title.setText(myAudioBook.get(index).getTitle());
                title.setGravity(Gravity.CENTER);
                title.setPadding(16, 16, 16, 16);
                title.setTextSize(20);
                layoutParams = new LinearLayout.LayoutParams(600, 600);
                layoutParams.gravity=Gravity.CENTER;
                image.setLayoutParams(layoutParams);
                Picasso.with(this)
                        //change
                        .load(myAudioBook.get(index).getLargeImage())
                        .resize(600, 600)
                        .centerCrop()
                        .into(image);
                //change
                artist.setText("By: " + myAudioBook.get(index).getArtist());
                releaseDate.setText(myAudioBook.get(index).getReleaseDate());
                releaseDate.setGravity(Gravity.CENTER);
                category.setText("Category: " + myAudioBook.get(index).getCategory());
                price.setText("Price: " + myAudioBook.get(index).getPrice());
                duration.setText("Duration: " + myAudioBook.get(index).getDuration());
                linkTitle.setText("App Link in Store:" );
                link.setText(myAudioBook.get(index).getLink());
                link.setTextColor(Color.parseColor("#2D9DC8"));

                ly.addView(title);
                ly.addView(releaseDate);
                ly.addView(image);
                ly.addView(artist);
                ly.addView(price);
                ly.addView(category);
                ly.addView(duration);
                ly.addView(linkTitle);
                ly.addView(link);
                sv.addView(ly);
                sv.invalidate();

                link.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailedMediaActivity.this, PreviewActivity.class);
                        //change
                        intent.putExtra("link",myAudioBook.get(index).getLink());
                        startActivity(intent);
                    }
                });
                break;

            case "iOS Apps":
                final ArrayList<IOSApp> myIosApp =gson.fromJson(dataFinal, new TypeToken<List<IOSApp>>(){}.getType());
                //change
                title.setText(myIosApp.get(index).getTitle());
                title.setGravity(Gravity.CENTER);
                title.setPadding(16, 16, 16, 16);
                title.setTextSize(20);
                layoutParams = new LinearLayout.LayoutParams(600, 600);
                layoutParams.gravity=Gravity.CENTER;
                image.setLayoutParams(layoutParams);
                Picasso.with(this)
                        //change
                        .load(myIosApp.get(index).getLargeImage())
                        .resize(600, 600)
                        .centerCrop()
                        .into(image);
                //change
                artist.setText("By: " + myIosApp.get(index).getArtist());
                releaseDate.setText(myIosApp.get(index).getReleaseDate());
                releaseDate.setGravity(Gravity.CENTER);
                category.setText("Category: " + myIosApp.get(index).getCategory());
                price.setText("Price: " + myIosApp.get(index).getPrice());
                linkTitle.setText("App Link in Store:" );
                link.setText(myIosApp.get(index).getLink());
                link.setTextColor(Color.parseColor("#2D9DC8"));

                ly.addView(title);
                ly.addView(releaseDate);
                ly.addView(image);
                ly.addView(artist);
                ly.addView(price);
                ly.addView(category);
                ly.addView(linkTitle);
                ly.addView(link);
                sv.addView(ly);
                sv.invalidate();

                link.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailedMediaActivity.this, PreviewActivity.class);
                        //change
                        intent.putExtra("link",myIosApp.get(index).getLink());
                        startActivity(intent);
                    }
                });
                break;

            case "iTunes U":
                final ArrayList<ItunesU> myItuneU =gson.fromJson(dataFinal, new TypeToken<List<ItunesU>>(){}.getType());
                //change
                title.setText(myItuneU.get(index).getTitle());
                title.setGravity(Gravity.CENTER);
                title.setPadding(16, 16, 16, 16);
                title.setTextSize(20);
                layoutParams = new LinearLayout.LayoutParams(600, 600);
                layoutParams.gravity=Gravity.CENTER;
                image.setLayoutParams(layoutParams);
                Picasso.with(this)
                        //change
                        .load(myItuneU.get(index).getLargeImage())
                        .resize(600, 600)
                        .centerCrop()
                        .into(image);
                //change
                artist.setText("By: " + myItuneU.get(index).getArtist());
                category.setText("Category: " + myItuneU.get(index).getCategory());
                summary.setText("Summary: "+myItuneU.get(index).getSummary());
                price.setText("Price: " + myItuneU.get(index).getPrice());
                linkTitle.setText("App Link in Store:" );
                link.setText(myItuneU.get(index).getLink());
                link.setTextColor(Color.parseColor("#2D9DC8"));

                ly.addView(title);
                ly.addView(image);
                ly.addView(artist);
                ly.addView(price);
                ly.addView(category);
                ly.addView(summary);
                ly.addView(linkTitle);
                ly.addView(link);
                sv.addView(ly);
                sv.invalidate();

                link.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailedMediaActivity.this, PreviewActivity.class);
                        //change
                        intent.putExtra("link",myItuneU.get(index).getLink());
                        startActivity(intent);
                    }
                });
                break;

            case "Mac Apps":
                final ArrayList<MacApp> myMacApp =gson.fromJson(dataFinal, new TypeToken<List<MacApp>>(){}.getType());
                //change
                title.setText(myMacApp.get(index).getTitle());
                title.setGravity(Gravity.CENTER);
                title.setPadding(16, 16, 16, 16);
                title.setTextSize(20);
                layoutParams = new LinearLayout.LayoutParams(600, 600);
                layoutParams.gravity=Gravity.CENTER;
                image.setLayoutParams(layoutParams);
                Picasso.with(this)
                        //change
                        .load(myMacApp.get(index).getLargeImage())
                        .resize(600, 600)
                        .centerCrop()
                        .into(image);
                //change
                releaseDate.setText(myMacApp.get(index).getReleaseDate());
                releaseDate.setGravity(Gravity.CENTER);
                artist.setText("By: " + myMacApp.get(index).getArtist());
                category.setText("Category: " + myMacApp.get(index).getCategory());
                summary.setText("Summary: "+myMacApp.get(index).getSummary());
                price.setText("Price: " + myMacApp.get(index).getPrice());
                linkTitle.setText("App Link in Store:" );
                link.setText(myMacApp.get(index).getLink());
                link.setTextColor(Color.parseColor("#2D9DC8"));

                ly.addView(title);
                ly.addView(releaseDate);
                ly.addView(image);
                ly.addView(artist);
                ly.addView(price);
                ly.addView(category);
                ly.addView(summary);
                ly.addView(linkTitle);
                ly.addView(link);
                sv.addView(ly);
                sv.invalidate();

                link.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailedMediaActivity.this, PreviewActivity.class);
                        //change
                        intent.putExtra("link",myMacApp.get(index).getLink());
                        startActivity(intent);
                    }
                });
                break;

            case "Movies":
                final ArrayList<Movies> myMovie =gson.fromJson(dataFinal, new TypeToken<List<Movies>>(){}.getType());
                //change
                title.setText(myMovie.get(index).getTitle());
                title.setGravity(Gravity.CENTER);
                title.setPadding(16, 16, 16, 16);
                title.setTextSize(20);
                layoutParams = new LinearLayout.LayoutParams(600, 600);
                layoutParams.gravity=Gravity.CENTER;
                image.setLayoutParams(layoutParams);
                Picasso.with(this)
                        //change
                        .load(myMovie.get(index).getLargeImage())
                        .resize(600, 600)
                        .centerCrop()
                        .into(image);
                //change
                //releaseDate.setText(myMovie.get(index).getReleaseDate());
                //releaseDate.setGravity(Gravity.CENTER);
                artist.setText("By: " + myMovie.get(index).getArtist());
                category.setText("Category: " + myMovie.get(index).getCategory());
                //summary.setText("Summary: "+myMovie.get(index).getSummary());
                price.setText("Price: " + myMovie.get(index).getPrice());
                linkTitle.setText("App Link in Store:" );
                link.setText(myMovie.get(index).getLink());
                link.setTextColor(Color.parseColor("#2D9DC8"));

                ly.addView(title);
                //ly.addView(releaseDate);
                ly.addView(image);
                ly.addView(artist);
                ly.addView(price);
                ly.addView(category);
                //ly.addView(summary);
                ly.addView(linkTitle);
                ly.addView(link);
                sv.addView(ly);
                sv.invalidate();

                link.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailedMediaActivity.this, PreviewActivity.class);
                        //change
                        intent.putExtra("link",myMovie.get(index).getLink());
                        startActivity(intent);
                    }
                });
                break;

            case "PodCasts":
                final ArrayList<Podcasts> myPodcast =gson.fromJson(dataFinal, new TypeToken<List<Podcasts>>(){}.getType());
                //change
                title.setText(myPodcast.get(index).getTitle());
                title.setGravity(Gravity.CENTER);
                title.setPadding(16, 16, 16, 16);
                title.setTextSize(20);
                layoutParams = new LinearLayout.LayoutParams(600, 600);
                layoutParams.gravity=Gravity.CENTER;
                image.setLayoutParams(layoutParams);
                Picasso.with(this)
                        //change
                        .load( myPodcast.get(index).getLargeImage())
                        .resize(600, 600)
                        .centerCrop()
                        .into(image);
                //change
                //releaseDate.setText( myPodcast.get(index).getReleaseDate());
                //releaseDate.setGravity(Gravity.CENTER);
                artist.setText("By: " +  myPodcast.get(index).getArtist());
                //category.setText("Category: " +  myPodcast.get(index).getCategory());
                summary.setText("Summary: "+ myPodcast.get(index).getSummary());
                price.setText("Price: " +  myPodcast.get(index).getPrice());
                linkTitle.setText("App Link in Store:" );
                link.setText( myPodcast.get(index).getLink());
                link.setTextColor(Color.parseColor("#2D9DC8"));

                ly.addView(title);
                //ly.addView(releaseDate);
                ly.addView(image);
                ly.addView(artist);
                ly.addView(price);
                //ly.addView(category);
                ly.addView(summary);
                ly.addView(linkTitle);
                ly.addView(link);
                sv.addView(ly);
                sv.invalidate();

                link.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailedMediaActivity.this, PreviewActivity.class);
                        //change
                        intent.putExtra("link", myPodcast.get(index).getLink());
                        startActivity(intent);
                    }
                });
                break;

            case "TV Shows":
                final ArrayList<TVShows> myTVShows =gson.fromJson(dataFinal, new TypeToken<List<TVShows>>(){}.getType());
                //change
                title.setText(myTVShows.get(index).getTitle());
                title.setGravity(Gravity.CENTER);
                title.setPadding(16, 16, 16, 16);
                title.setTextSize(20);
                layoutParams = new LinearLayout.LayoutParams(600, 600);
                layoutParams.gravity=Gravity.CENTER;
                image.setLayoutParams(layoutParams);
                Picasso.with(this)
                        //change
                        .load( myTVShows.get(index).getLargeImage())
                        .resize(600, 600)
                        .centerCrop()
                        .into(image);
                //change
                releaseDate.setText( myTVShows.get(index).getReleaseDate());
                releaseDate.setGravity(Gravity.CENTER);
                artist.setText("By: " + myTVShows.get(index).getArtist());
                category.setText("Category: " + myTVShows.get(index).getCategory());
                summary.setText("Summary: "+ myTVShows.get(index).getSummary());
                price.setText("Price: " +  myTVShows.get(index).getPrice());
                linkTitle.setText("App Link in Store:" );
                link.setText( myTVShows.get(index).getLink());
                link.setTextColor(Color.parseColor("#2D9DC8"));

                ly.addView(title);
                ly.addView(releaseDate);
                ly.addView(image);
                ly.addView(artist);
                ly.addView(price);
                ly.addView(category);
                ly.addView(summary);
                ly.addView(linkTitle);
                ly.addView(link);
                sv.addView(ly);
                sv.invalidate();

                link.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailedMediaActivity.this, PreviewActivity.class);
                        //change
                        intent.putExtra("link", myTVShows.get(index).getLink());
                        startActivity(intent);
                    }
                });
                break;

        }
    }
}
