package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.ang.acb.displayjoke.DisplayJokeActivity;
import com.ang.acb.joketeller.JokeTeller;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivityFragment extends Fragment implements JokeLoadedCallback {

    // Required public empty constructor
    public MainActivityFragment() {}

    private Button tellJokeButton;
    private ProgressBar loadingIndicator;
    private AdView adView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(root);
        hideLoadingIndicator();
        initAdView();
        setupTellJokeButton();

        return root;
    }

    private void initViews(View root){
        tellJokeButton = root.findViewById(R.id.tell_joke_button);
        loadingIndicator = root.findViewById(R.id.loading_indicator);
        adView = root.findViewById(R.id.ad_view);
    }

    private void showLoadingIndicator() {
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    private void hideLoadingIndicator() {
        loadingIndicator.setVisibility(View.INVISIBLE);
    }

    private void initAdView() {
        // Create an ad request. Check logcat output for the hashed device ID to get test
        // ads on a physical device: "Use AdRequest.Builder.addTestDevice("ABCDEF012345")
        // to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        adView.loadAd(adRequest);
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
