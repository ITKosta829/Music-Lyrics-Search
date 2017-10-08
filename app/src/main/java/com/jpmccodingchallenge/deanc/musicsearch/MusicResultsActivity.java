package com.jpmccodingchallenge.deanc.musicsearch;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jpmccodingchallenge.deanc.musicsearch.adapters.MusicResultsArrayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanC on 10/7/2017.
 */

public class MusicResultsActivity extends AppCompatActivity {

    protected static final String API_HTTP = "https://itunes.apple.com/search?term=";
    protected static final String LYRICS_API_HTTP = "http://lyrics.wikia.com/api.php?func=getSong&artist=";
    private static final String LOG_TAG = "MusicResultsActivity";

    List<MusicData> musicDataList;
    ListView listView;
    MusicResultsArrayAdapter musicResultsArrayAdapter;

    String userSearch, trackSearch, trackName, artistName, albumName, lyricsURL, trackImage, lyrics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.music_results_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listView = (ListView) findViewById(R.id.listView);
        musicDataList = new ArrayList<>();

        Intent intent = getIntent();
        userSearch = intent.getExtras().getString("search_tag");

        if (userSearch.contains(" ")) {
            userSearch = userSearch.replace(' ', '+');
        }

        String URL = API_HTTP + userSearch;
        Log.w(LOG_TAG, URL);

        new getMusicList().execute(URL);

        musicResultsArrayAdapter = new MusicResultsArrayAdapter(this, musicDataList);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private class getMusicList extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            StringBuilder sb = new StringBuilder();

            HttpURLConnection urlConnection = null;
            try {
                java.net.URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setUseCaches(false);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                urlConnection.connect();

                int HttpResult = urlConnection.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream(), "utf-8"));
                    String line;
                    while ((line = in.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    in.close();

                    //System.out.println("" + sb.toString());
                    return sb.toString();

                } else {
                    System.out.println(urlConnection.getResponseMessage());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            try {

                JSONObject json = new JSONObject(result);
                JSONArray songList = json.getJSONArray("results");

                for (int i = 0; i < songList.length(); i++) {

                    JSONObject song = songList.getJSONObject(i);

                    if (song != null) {
                        String s = song.getString("artworkUrl100");

                        if (!s.equals("null")) {
                            MusicData musicData = new MusicData(song);
                            musicDataList.add(musicData);
                        }

                    }

                }

                updateListView();

            } catch (Exception e) {
                Log.w(LOG_TAG, e);
                e.printStackTrace();
            }
        }
    }

    public void updateListView() {
        listView.setAdapter(musicResultsArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                trackName = musicDataList.get(i).trackName;
                artistName = musicDataList.get(i).artistName;
                albumName = musicDataList.get(i).albumName;
                trackImage = musicDataList.get(i).trackViewURL;

                createLyricsURL();
            }
        });

    }

    public void createLyricsURL() {

        if (trackName.contains(" ")) {
            trackSearch = trackName.replace(' ', '+');
        } else {
            trackSearch = trackName;
        }

        lyricsURL = LYRICS_API_HTTP + userSearch + "&song=" + trackSearch + "&fmt=json";

        Log.w(LOG_TAG, lyricsURL);

        new getTrackLyrics().execute(lyricsURL);

    }

    private class getTrackLyrics extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            StringBuilder sb = new StringBuilder();

            HttpURLConnection urlConnection = null;
            try {
                java.net.URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setUseCaches(false);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                urlConnection.connect();

                int HttpResult = urlConnection.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream(), "utf-8"));
                    String line;
                    while ((line = in.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    in.close();

                    //System.out.println("" + sb.toString());
                    return sb.toString();

                } else {
                    System.out.println(urlConnection.getResponseMessage());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            String s = result.substring(7);

            try {

                JSONObject json = new JSONObject(s);

                lyrics = json.getString("lyrics");

                displayResultingLyricsPage();

            } catch (Exception e) {
                Log.w(LOG_TAG, e);
                e.printStackTrace();
            }
        }
    }

    public void displayResultingLyricsPage() {

        android.app.FragmentManager fm = getFragmentManager();

        LyricsResultsDialog lyricsResultsDialog = new LyricsResultsDialog();
        Bundle bundle = new Bundle();
        bundle.putString("artist_name", artistName);
        bundle.putString("track_name", trackName);
        bundle.putString("album_name", albumName);
        bundle.putString("track_image", trackImage);
        bundle.putString("lyrics", lyrics);
        lyricsResultsDialog.setArguments(bundle);
        lyricsResultsDialog.show(fm, "Show Lyrics Dialog");

    }


}
