package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

/**
 * An AsyncTask that makes the request to the Cloud Endpoints backend API to retrieve jokes.
 *
 * See: https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/77e9910911d5412e5efede5fa681ec105a0f02ad/HelloEndpoints#2-connecting-your-android-app-to-the-backend
 */
class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {

    private static MyApi api = null;
    private JokeLoadedCallback callback;

    public EndpointsAsyncTask(JokeLoadedCallback callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... params) {
        // Only do this once
        if (api == null) {
            MyApi.Builder builder =
                    new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                            new AndroidJsonFactory(), null)
                            // Note: options for running against local devappserver:
                            // -> 10.0.2.2 is localhost's IP address in Android emulator
                            // -> turn off compression when running against local devappserver
                            .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                @Override
                                public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                                    request.setDisableGZipContent(true);
                                }
                            }); // End options for devappserver

            api = builder.build();
        }

        try {
            return api.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        callback.onJokeLoaded(result);
    }
}
