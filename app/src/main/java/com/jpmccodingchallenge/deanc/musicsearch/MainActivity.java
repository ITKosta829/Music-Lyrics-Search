package com.jpmccodingchallenge.deanc.musicsearch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
    }

    public void onSearchButtonClicked(View v) {

        EditText search = (EditText) findViewById(R.id.et_search_bar);
        String text = search.getText().toString();

        //Check for network connectivity
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        //Pass user input to next activity
        Intent intent = new Intent(this, MusicResultsActivity.class);
        intent.putExtra("search_tag", text);

        if (isConnected) {
            startActivity(intent);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.connectivity_error)
                    .setMessage(R.string.connectivity_msg)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .show();

        }
    }
}
