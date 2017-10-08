package com.jpmccodingchallenge.deanc.musicsearch;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DeanC on 10/6/2017.
 */

public class MusicData {

    private static final String LOG_TAG = "MusicData";

    public String artistName;
    public String trackName;
    public String albumName;
    public String trackViewURL;

    public MusicData(JSONObject jsonObject)
    {
        if (jsonObject != null)
        {
            try
            {
                artistName = jsonObject.getString("artistName");
                trackName = jsonObject.getString("trackName");
                albumName = jsonObject.getString("collectionName");
                trackViewURL = jsonObject.getString("artworkUrl100");
            }
            catch (JSONException e)
            {
                Log.w(LOG_TAG, e);
            }
        }
    }
}
