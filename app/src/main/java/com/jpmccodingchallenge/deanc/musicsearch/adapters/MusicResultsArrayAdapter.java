package com.jpmccodingchallenge.deanc.musicsearch.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jpmccodingchallenge.deanc.musicsearch.MusicData;
import com.jpmccodingchallenge.deanc.musicsearch.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MusicResultsArrayAdapter extends ArrayAdapter<MusicData> {
    public MusicResultsArrayAdapter(Context context, List<MusicData> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MusicResultsCell musicResultsCell;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.music_results_cell, parent, false);
            musicResultsCell = new MusicResultsCell();
            musicResultsCell.albumImageView = (ImageView) convertView.findViewById(R.id.imgAlbumCover);
            musicResultsCell.tracknameTextView = (TextView) convertView.findViewById(R.id.tracknameTextView);
            musicResultsCell.artistnameTextView = (TextView) convertView.findViewById(R.id.artistnameTextView);
            musicResultsCell.albumnameTextView = (TextView) convertView.findViewById(R.id.albumnameTextView);

            Typeface typeFaceTrackName = Typeface.createFromAsset(getContext().getAssets(),
                    "fonts/Jelloween - Machinato Bold.ttf");
            Typeface typeFaceArtistName = Typeface.createFromAsset(getContext().getAssets(),
                    "fonts/Jelloween - Machinato Light.ttf");
            Typeface typeFaceAlbumName = Typeface.createFromAsset(getContext().getAssets(),
                    "fonts/Jelloween - Machinato.ttf");

            musicResultsCell.tracknameTextView.setTypeface(typeFaceTrackName);
            musicResultsCell.artistnameTextView.setTypeface(typeFaceArtistName);
            musicResultsCell.albumnameTextView.setTypeface(typeFaceAlbumName);

            convertView.setTag(musicResultsCell);
        } else {
            musicResultsCell = (MusicResultsCell) convertView.getTag();
        }

        MusicData musicData = getItem(position);

        Picasso.with(getContext()).load(musicData.trackViewURL).into(musicResultsCell.albumImageView);
        musicResultsCell.tracknameTextView.setText(musicData.trackName);
        musicResultsCell.artistnameTextView.setText(musicData.artistName);
        musicResultsCell.albumnameTextView.setText(musicData.albumName);

        return convertView;
    }

    private static class MusicResultsCell {
        ImageView albumImageView;
        TextView tracknameTextView;
        TextView artistnameTextView;
        TextView albumnameTextView;
    }
}
