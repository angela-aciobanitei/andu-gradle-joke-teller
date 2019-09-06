package com.ang.acb.displayjoke;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE = "EXTRA_JOKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);

        TextView textview = findViewById(R.id.tv_joke);

        String joke = null;
        Intent intent = getIntent();
        if(intent.hasExtra(getString(R.string.extra_joke))) {
            joke = intent.getStringExtra(getString(R.string.extra_joke));
        }

        if (joke != null) {
            textview.setText(joke);
        } else {
            textview.setText(getString(R.string.no_jokes_yet));
        }
    }
}
