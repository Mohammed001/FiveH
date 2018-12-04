package com.example.asus.fiveh.loginproviders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.asus.fiveh.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

public class TwitterLogin {
    private AppCompatActivity context;
    private TwitterAuthClient mTwitterAuthClient;

    public TwitterLogin(AppCompatActivity context) {
        this.context = context;
    }

    public void initTwitter() {
        TwitterConfig config = new TwitterConfig.Builder(context)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(
                        context.getString(R.string.twitter_consumer_key),
                        context.getString(R.string.twitter_consumer_secret))
                ).debug(true).build();

        Twitter.initialize(config);
    }


    public void twitterOnCreate() {
        mTwitterAuthClient = new TwitterAuthClient();
//        twitterloginButton = findViewById(R.id.twitter_login_button);
//        twitterloginButton.setCallback(new Callback<TwitterSession>() {
//        });
    }


    public void twitterOnActivityResult(int requestCode, int resultCode, Intent data) {
        mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
    }


    public void doTwitterLogin() {
        mTwitterAuthClient.authorize(context, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {

//                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
//                TwitterAuthToken authToken = session.getAuthToken();
//                String token = authToken.token;
//                String secret = authToken.secret;

                // Do something with result, which provides a TwitterSession for making API calls
                String username = result.data.getUserName();
                Toast.makeText(context, "Hello " + username, Toast.LENGTH_SHORT).show();
                // todo: read the docs in:
                // https://github.com/twitter/twitter-kit-android/wiki/Log-In-with-Twitter
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Toast.makeText(context, "failure!", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
