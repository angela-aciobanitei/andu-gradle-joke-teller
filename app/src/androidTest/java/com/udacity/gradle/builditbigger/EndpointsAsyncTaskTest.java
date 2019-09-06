package com.udacity.gradle.builditbigger;

import android.text.TextUtils;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class EndpointsAsyncTaskTest {

    private String result = null;

    @Test
    public void jokeLoadedTest() {
        // Note: a CountDownLatch is a synchronization tool that allows one or more threads
        // to wait until a set of operations being performed in other threads completes. A
        // CountDownLatch is initialized with a given count. The await() methods block until
        // the current count reaches zero due to invocations of the countDown() method,
        // after which all waiting threads are released and any subsequent invocations of
        // await return immediately. This is a one-shot phenomenon - count cannot be reset.
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        new EndpointsAsyncTask(new JokeLoadedCallback() {
            @Override
            public void onJokeLoaded(String joke) {
                result = joke;
                countDownLatch.countDown();
            }
        }).execute();

        try {
            countDownLatch.await();
            assertNotNull(result);
            assertFalse(TextUtils.isEmpty(result));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
