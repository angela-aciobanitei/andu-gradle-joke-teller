## Gradle for Android and Java Final Project

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/6752bd0bc30a443bba238d1716655e2f)](https://app.codacy.com/manual/angela-aciobanitei/andu-gradle-joke-teller?utm_source=github.com&utm_medium=referral&utm_content=angela-aciobanitei/andu-gradle-joke-teller&utm_campaign=Badge_Grade_Settings)

In this project, you will create an app with multiple flavors that uses
multiple libraries and Google Cloud Endpoints. The finished app will consist
of four modules. A Java library that provides jokes, a Google Cloud Endpoints
(GCE) project that serves those jokes, an Android Library containing an
activity for displaying jokes, and an Android app that fetches jokes from the
GCE module and passes them to the Android Library for display.

## Why this Project

As Android projects grow in complexity, it becomes necessary to customize the
behavior of the Gradle build tool, allowing automation of repetitive tasks.
Particularly, factoring functionality into libraries and creating product
flavors allow for much bigger projects with minimal added complexity.

## What Will I Learn?

*   Add free and paid flavors to an app, and set up your build to share code between them
*   Factor reusable functionality into a Java library
*   Factor reusable Android functionality into an Android library
*   Configure a multi project build to compile your libraries and app
*   Use the Gradle App Engine plugin to deploy a backend
*   Configure an integration test suite that runs against the local App Engine development server

## How to Complete this Project

### Step 0: Starting Point

This is the starting point for the final project, which is provided to you in
the [course repository](https://github.com/udacity/ud867/tree/master/FinalProject). 
It contains an activity with a [banner ad](https://developers.google.com/mobile-ads-sdk/docs/admob/android/quick-start) 
and a button that purports to tell ajoke, but actually just complains. 

You will notice a folder called backend in the starter code. It will be used in 
step 3 below, and you do not need to worry about it for now.

You may need to download the Google Repository from the Extras section of the
Android SDK Manager. When you can build an deploy this starter code to an emulator, 
you're ready to move on.

### Step 1: Create a Java library

Your first task is to create a Java library that provides jokes. Create a new
Gradle Java project either using the Android Studio wizard, or by hand. Then
introduce a project dependency between your app and the new Java Library. 
Make the button display a toast showing a joke retrieved from your Java joke
telling library.

### Step 2: Create an Android Library

Create an Android Library containing an Activity that will display a joke
passed to it as an intent extra. Wire up project dependencies so that the
button can now pass the joke from the Java Library to the Android Library.

### Step 3: Setup GCE

This next task will be pretty tricky. Instead of pulling jokes directly from
our Java library, we'll set up a Google Cloud Endpoints development server,
and pull our jokes from there. The starter code already includes the GCE module 
in the folder called backend.

Before going ahead you will need to be able to run a local instance of the GCE 
server. In order to do that you will have to install the [Cloud SDK](https://cloud.google.com/sdk/docs/).

Once installed, you will need to follow the instructions in the 
[Setup Cloud SDK](https://cloud.google.com/endpoints/docs/frameworks/java/migrating-android) section. 
You do not need to follow the rest of steps in the migration guide, only the Setup Cloud SDK.

Start or stop your local server by using the gradle tasks as shown in the following screenshot:

<img src="/screenshots/GCE-server-gradle-tasks.png" height="450">

Once your local GCE server is started you should see the following at [localhost:8080](http://localhost:8080)

<img src="/screenshots/localhost_800.png" height="450">

Now you are ready to continue! 

Introduce a project dependency between your Java library and your GCE module, and modify 
the GCE starter code to pull jokes from your Java library. 

Create an AsyncTask to retrieve jokes using the template included int these 
[instructions](https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/77e9910911d5412e5efede5fa681ec105a0f02ad/HelloEndpoints#2-connecting-your-android-app-to-the-backend). 
Make the button kick off a task to retrieve a joke, then launch the activity from your 
Android Library to display it.

### Step 4: Add Functional Tests

Add code to test that your Async task successfully retrieves a non-empty string. 

### Step 5: Add a Paid Flavor

Add free and paid product flavors to your app. Remove the ad (and any dependencies you can) from the paid flavor.

## Optional Tasks

For extra practice to make your project stand out, complete the following tasks.

### Step 6: Add Interstitial Ad

Follow these [instructions](https://developers.google.com/mobile-ads-sdk/docs/admob/android/interstitial) 
to add an interstitial ad to the free version. Display the ad after the user hits the 
button, but before the joke is shown.

### Step 7: Add Loading Indicator

Add a [loading indicator](http://www.tutorialspoint.com/android/android_loading_spinner.htm) 
that is shown while the joke is being retrieved and disappears when the joke is ready.

### Step 8: Configure Test Task

To tie it all together, create a Gradle task that:
1. Launches the GCE local development server
2. Runs all tests
3. Shuts the server down again

## Rubric

### Required Components

*   Project contains a Java library for supplying jokes
*   Project contains an Android library with an activity that displays jokes passed to it as intent extras.
*   Project contains a Google Cloud Endpoints module that supplies jokes from the Java library. 
*   Project loads jokes from GCE module via an async task.
*   Project contains connected tests to verify that the async task is indeed loading jokes.
*   Project contains paid/free flavors. The paid flavor has no ads, and no unnecessary dependencies.

### Required Behavior

*   App retrieves jokes from Google Cloud Endpoints module and displays them via an Activity from the Android Library.

### Optional Components

Once you have a functioning project, consider adding more features to test your Gradle and Android skills. Here are a few suggestions:

*   Make the free app variant display interstitial ads between the main activity and the joke-displaying activity.
*   Have the app display a loading indicator while the joke is being fetched from the server.
*   Write a Gradle task that starts the GCE dev server, runs all the Android tests, and shuts down the dev server.

## Screenshots
<img src="/screenshots/initial_screen.png" width="250"/> <img src="/screenshots/test_add_screen.png" width="250"/> 
<img src="/screenshots/joke_screen.png" width="250"/> 
 
