package com.example.user.youtubevideolist;

import android.app.Activity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    String id, title, description, url;
    ListView listView;
    Video_array video_array;
    Youtube_Adapter youtube_adapter;
    ArrayList<Video_array> videoshow = new ArrayList<Video_array>();
    private boolean advertised = false;
    Button test;
    String nextPageToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayImageOptions defaultOption = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).defaultDisplayImageOptions(defaultOption).build();
        ImageLoader.getInstance().init(config);
        listView = (ListView) findViewById(R.id.listView);
        test = (Button) findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nextPageToken.equals("") || advertised == true) {
                    Playlist();
                }
            }
        });
        videoshow = new ArrayList<Video_array>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String vid = videoshow.get(i).getVideo_id();
                Toast.makeText(getApplicationContext(), vid, Toast.LENGTH_LONG).show();
                Intent set = new Intent(MainActivity.this, Youtubeplayer.class);
                set.putExtra("ID", vid);
                startActivity(set);

            }
        });
        Channel();

    }

    public void Channel() {
        AsyncHttpClient client = new AsyncHttpClient();
        //  Dummy UCKyLMBSgGfeN1Lw14GYR8Wg

        client.get("https://www.googleapis.com/youtube/v3/channels?part=contentDetails&id=Channel_id&key=Google_APIKEY ", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                Log.e("response", response + "");

                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray albums = obj.getJSONArray("items");
                    for (int i = 0; i < albums.length(); i++) {
                        JSONObject c = albums.getJSONObject(i);
                        id = c.getString("id");
                        Log.e("ID", id);
                        if (!id.equals(" ")) {
                            Playlist();
                        }

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    //Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {

                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), statusCode + " " + error + " " + content, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void Playlist() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + id + "&key=googel_APIkey&pageToken=" + nextPageToken, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                Log.e("response", response + "");
                try {
                    JSONObject obj = new JSONObject(response);
                    nextPageToken = obj.getString("nextPageToken");
                    if (nextPageToken.equals("")) {
                        advertised = true;
                    }
                    JSONArray albums = obj.getJSONArray("items");
                    for (int i = 0; i < albums.length(); i++) {
                        video_array = new Video_array();
                        JSONObject c = albums.getJSONObject(i);
                        JSONObject snippet = c.getJSONObject("snippet");
                        title = snippet.getString("title");
                        video_array.setVIdeotitle(title);
                        description = snippet.getString("description");
                        video_array.setVideo_des(description);
                        JSONObject resourceId = snippet.getJSONObject("resourceId");
                        video_array.setVideo_id(resourceId.getString("videoId"));
                        JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                        JSONObject defaulta = thumbnails.getJSONObject("default");
                        url = defaulta.getString("url");
                        Log.e("Tittle", title + description + url);
                        video_array.setVideothumb(url);
                        // JSONArray thumbnails=c.getJSONArray("thumbnails");
                        videoshow.add(video_array);


                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    //Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
                youtube_adapter = new Youtube_Adapter(getApplicationContext(), R.layout.youtube_adapter, videoshow);
                listView.setAdapter(youtube_adapter);
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), statusCode + " " + error + " " + content, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}

