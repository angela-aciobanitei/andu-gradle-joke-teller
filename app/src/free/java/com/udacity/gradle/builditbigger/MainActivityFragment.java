package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.ang.acb.displayjoke.DisplayJokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Objects;

public class MainActivityFragment extends Fragment implements JokeLoadedCallback {

    private Button tellJokeButton;
    private ProgressBar loadingIndicator;
    private AdView adView;
    private InterstitialAd interstitialAd;
    private String joke;

    // Required public empty constructor
    public MainActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(root);
        hideLoadingIndicator();
        initAd();
        initAdListener();
        loadAd();
        setupTellJokeButton();
        setupBannerAd();

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

    private void initAd() {
        // Initialize the Google Mobile Ads SDK.
        MobileAds.initialize(getContext(), getString(R.string.ad_mob_app_id));

        // Interstitial ads are requested and shown by InterstitialAd objects. The first
        // step is instantiating an InterstitialAd and setting its ad unit ID. A single
        // InterstitialAd object can be used to request and display multiple interstitial ads
        // over the course of an activity's lifespan, so you only need to construct it once.
        interstitialAd = new InterstitialAd(Objects.requireNonNull(getContext()));

        // Note: when building and testing your apps, make sure you use test ads rather than
        // live, production ads. The easiest way to load test ads is to use our dedicated
        // test ad unit ID for Android interstitials: ca-app-pub-3940256099942544/1033173712.
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
    }

    private void loadAd() {
        // See: https://developers.google.com/admob/android/interstitial
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public void initAdListener() {
        // To customize the behavior of your ad, you can hook onto a number of events in
        // the ad's lifecycle: loading, opening, closing, and so on. You can listen for
        // these events through the AdListener class.
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                // Display joke.
                launchJokeActivity();
                // Load the next interstitial ad.
                loadAd();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                loadAd();
            }
        });
    }

    private void setupBannerAd() {
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
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    if (!TextUtils.isEmpty(joke)) {
                        launchJokeActivity();
                    }
                }
            }
        });
    }

    private void loadJoke(){
        new EndpointsAsyncTask(this).execute();
    }

    @Override
    public void onJokeLoaded(String loadedJoke) {
        joke = loadedJoke;
        hideLoadingIndicator();
    }

    private void launchJokeActivity() {
        Intent intent = new Intent(getContext(), DisplayJokeActivity.class);
        intent.putExtra(DisplayJokeActivity.EXTRA_JOKE, joke);
        startActivity(intent);
    }
}
