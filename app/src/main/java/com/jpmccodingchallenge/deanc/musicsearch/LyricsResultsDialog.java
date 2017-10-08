package com.jpmccodingchallenge.deanc.musicsearch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by DeanC on 10/7/2017.
 */

public class LyricsResultsDialog extends DialogFragment{

    TextView artistName, artistNameDetails, albumName, albumNameDetails, trackName, trackNameDetails, lyrics, lyricsDetails;
    ImageView trackImage;
    String artist, album, track, lyricText, trackPicURL;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.lyrics_results_dialog, null);

        Bundle bundle = getArguments();
        artist = bundle.getString("artist_name");
        album = bundle.getString("album_name");
        track = bundle.getString("track_name");
        trackPicURL = bundle.getString("track_image");
        lyricText = bundle.getString("lyrics");


        artistName = (TextView) v.findViewById(R.id.artistName);
        artistNameDetails = (TextView) v.findViewById(R.id.artistNameDetails);
        albumName = (TextView) v.findViewById(R.id.albumName);
        albumNameDetails = (TextView) v.findViewById(R.id.albumNameDetails);
        trackName = (TextView) v.findViewById(R.id.trackname);
        trackNameDetails = (TextView) v.findViewById(R.id.trackNameDetails);
        lyrics = (TextView) v.findViewById(R.id.lyrics);
        lyricsDetails = (TextView) v.findViewById(R.id.lyricsDetails);
        trackImage = (ImageView) v.findViewById(R.id.track_image);

        Typeface details = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Jelloween - Machinato.ttf");
        Typeface titles = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Jelloween - Machinato Bold.ttf");

        artistName.setTypeface(titles);
        artistNameDetails.setTypeface(details);
        albumName.setTypeface(titles);
        albumNameDetails.setTypeface(details);
        trackName.setTypeface(titles);
        trackNameDetails.setTypeface(details);
        lyrics.setTypeface(titles);
        lyricsDetails.setTypeface(details);

        artistNameDetails.setText(artist);
        albumNameDetails.setText(album);
        trackNameDetails.setText(track);
        lyricsDetails.setText(lyricText);

        Picasso.with(getActivity()).load(trackPicURL).into(trackImage);

        AlertDialog.Builder b;
        b = new AlertDialog.Builder(getActivity());
        b.setView(v)

                //.setCustomTitle(title)

                .setNegativeButton("Close",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        }
                );

        return b.create();
    }
}
