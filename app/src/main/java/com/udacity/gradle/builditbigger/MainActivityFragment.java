package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ang.acb.displayjoke.DisplayJokeActivity;
import com.ang.acb.joketeller.JokeTeller;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivityFragment extends Fragment implements JokeLoadedCallback {

    public MainActivityFragment() {}
    String joke;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        initAdView(root);
        setupTellJokeButton(root);

        return root;
    }

    private void initAdView(View root) {
        AdView mAdView = root.findViewById(R.id.ad_view);
        // Create an ad request. Check logcat output for the hashed device ID to get test
        // ads on a physical device. e.g."Use AdRequest.Builder.addTestDevice("ABCDEF012345")
        // to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mAdView.loadAd(adRequest);
    }

    private void setupTellJokeButton(View root){
        Button button = root.findViewById(R.id.tell_joke_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadJoke();
                launchJokeActivity();
            }
        });
    }

    private void loadJavaLibJoke(){
        JokeTeller jokeTeller = new JokeTeller();
        setJoke(jokeTeller.tellJoke());
    }

    private void loadJoke(){
        new EndpointsAsyncTask(this).execute();
    }

    private void launchJokeActivity() {
        Intent intent = new Intent(getContext(), DisplayJokeActivity.class);
        intent.putExtra(getString(R.string.extra_joke), getJoke());
        startActivity(intent);
    }

    private String getJoke(){
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    @Override
    public void onJokeLoaded(String joke) {
        setJoke(joke);
    }
}
