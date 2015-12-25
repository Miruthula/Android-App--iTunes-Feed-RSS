package com.example.yzhuo.homeowork4;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yzhuo on 10/4/2015.
 */
public class ParseBooks {
    static public class parse {
        static String parseBooks(String in) throws JSONException {
            ArrayList<Books> myList = new ArrayList<>();

            JSONObject root = new JSONObject(in);
            JSONArray entry = root.getJSONObject("feed").getJSONArray("entry");
            for(int i = 0; i<entry.length(); i++){
                Books myBook = new Books();
                JSONObject entries = entry.getJSONObject(i);
                myBook.setTitle(entries.getJSONObject("title").getString("label"));
                Log.d("Title", myBook.getTitle());
                myBook.setArtist(entries.getJSONObject("im:artist").getString("label"));
                Log.d("Artist", myBook.getArtist());
                myBook.setCategory(entries.getJSONObject("category").getJSONObject("attributes").getString("label"));
                Log.d("Category", myBook.getCategory());
                myBook.setPrice(entries.getJSONObject("im:price").getString("label"));
                Log.d("Price", myBook.getPrice());
                myBook.setReleaseDate(entries.getJSONObject("im:releaseDate").getString("label"));
                Log.d("ReleaseDate", myBook.getReleaseDate());
                myBook.setSummary(entries.getJSONObject("summary").getString("label"));
                Log.d("Summary", myBook.getSummary());
                myBook.setLink(entries.getJSONObject("link").getJSONObject("attributes").getString("href"));
                Log.d("Link", myBook.getLink());

                JSONArray image = entries.getJSONArray("im:image");
                myBook.setLargeImage(image.getJSONObject(2).getString("label"));
                Log.d("LargeImage", myBook.getLargeImage());
                myBook.setSmallImage(image.getJSONObject(0).getString("label"));
                Log.d("SmallImage", myBook.getSmallImage());



                myList.add(myBook);
            }
            Gson gson = new Gson();
            String json = gson.toJson(myList);
            return json;



        }
    }
}
