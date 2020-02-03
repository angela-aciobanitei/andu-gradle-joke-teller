package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.ang.acb.displayjoke.DisplayJokeActivity;

public class MainActivityFragment extends Fragment implements JokeLoadedCallback {

    private Button tellJokeButton;
    private ProgressBar loadingIndicator;

    // Required public empty constructor
    public MainActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(root);
        hideLoadingIndicator();
        setupTellJokeButton();

        return root;
    }

    private void initViews(View root){
        tellJokeButton = root.findViewById(R.id.tell_joke_button);
        loadingIndicator = root.findViewById(R.id.loading_indicator);
    }

    private void showLoadingIndicator() {
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    private void hideLoadingIndicator() {
        loadingIndicator.setVisibility(View.INVISIBLE);
    }


    private void setupTellJokeButton(){
        tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingIndicator();
                loadJoke();
            }
        });
    }

    private void loadJoke(){
        new EndpointsAsyncTask(this).execute();
    }

    @Override
    public void onJokeLoaded(String joke) {
        launchJokeActivity(joke);
        hideLoadingIndicator();
    }

    private void launchJokeActivity(String joke) {
        Intent intent = new Intent(getContext(), DisplayJokeActivity.class);
        intent.putExtra(DisplayJokeActivity.EXTRA_JOKE, joke);
        startActivity(intent);
    }
}

