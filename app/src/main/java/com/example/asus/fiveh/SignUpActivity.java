package com.example.asus.fiveh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.fiveh.insta.InstagramApp;
import com.example.asus.fiveh.loginproviders.GoogleLogin;
import com.example.asus.fiveh.utils.Utils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.util.Arrays;
import java.util.HashMap;

import static com.example.asus.fiveh.utils.Utils.ADVERTISER;
import static com.example.asus.fiveh.utils.Utils.GREED;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, GoogleLogin.UPDATEui {

    private static final String EMAIL = "email";
    private static final String TAG = SignUpActivity.class.getSimpleName();
    android.support.design.widget.TextInputEditText input_name;
    android.support.design.widget.TextInputEditText input_email;
    android.support.design.widget.TextInputEditText input_password;
    Button btn_signup;
    TextView link_login;

    ImageView insta_login;
    ImageView facebookLoginButton;
    ImageView googleloginButton;

    CallbackManager callbackManager;
    int temp_user_type = -1;
    private InstagramApp mApp;
    private HashMap<String, String> userInfoHashmap = new HashMap<>();
    private static final int RC_SIGN_IN = 9001;
    //    TwitterLoginButton twitterloginButton;
    TwitterAuthClient mTwitterAuthClient;
    ImageView twitterbtn;

    private void initTwitter() {
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(
                        getString(R.string.twitter_consumer_key),
                        getString(R.string.twitter_consumer_secret))
                ).debug(true).build();

        Twitter.initialize(config);
    }


    GoogleLogin googleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTwitter();
        setContentView(R.layout.activity_sign_up);
        googleLogin = new GoogleLogin(this);
        initviews();

        initfb();

        setlistener();

        instaOnCreate();

        googleLogin.googleOnCreate();

        twitterOnCreate();

    }

    private void initviews() {
        twitterbtn = findViewById(R.id.twitter_login);
        input_name = findViewById(R.id.input_name);
        input_email = findViewById(R.id.input_email);
        input_password = findViewById(R.id.input_password);
        btn_signup = findViewById(R.id.btn_signup);
        link_login = findViewById(R.id.link_login);
        findViewById(R.id.radio_pirates).setOnClickListener(this);
        findViewById(R.id.radio_ninjas).setOnClickListener(this);
        insta_login = findViewById(R.id.insta_login);
        googleloginButton = findViewById(R.id.google_login);
    }

    private void setlistener() {
        btn_signup.setOnClickListener(this);
        link_login.setOnClickListener(this);
        insta_login.setOnClickListener(this);
        googleloginButton.setOnClickListener(this);
        twitterbtn.setOnClickListener(this);
    }

    private void twitterOnCreate() {
        mTwitterAuthClient = new TwitterAuthClient();
//        twitterloginButton = findViewById(R.id.twitter_login_button);
//        twitterloginButton.setCallback(new Callback<TwitterSession>() {
//        });
    }

    // this method is needed to accomplish {GoogleSignIn} process
    @Override
    public void onStart() {
        super.onStart();
        googleLogin.googleOnStart();
    }

    private void initfb() {
        callbackManager = CallbackManager.Factory.create();
        facebookLoginButton = findViewById(R.id.connectWithFbButton);
        facebookLoginButton.setOnClickListener(this);
        // facebookLoginButton.setAuthType(AUTH_TYPE);
        // If you are using in a fragment, call facebookLoginButton.setFragment(this);

        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager, new
                FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Toast.makeText(SignUpActivity.this, "onSuccess " + loginResult.getAccessToken().getUserId(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(SignUpActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(SignUpActivity.this, "onError", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void instaOnCreate() {
        String client_id = getResources().getString(R.string.insta_client_id);
        String client_secret = getResources().getString(R.string.insta_client_secret);
        String client_callbackuri = getResources().getString(R.string.insta_client_callbackuri);

        mApp = new InstagramApp(this, client_id, client_secret, client_callbackuri);

        final Handler handler = getInstaHandler();
        mApp.setListener(createInstaListener(handler));

        if (mApp.hasAccessToken()) {
            mApp.fetchUserName(handler);
            String name = userInfoHashmap.get(InstagramApp.TAG_USERNAME);
            Snackbar.make(findViewById(R.id.root), "hello " + name, Snackbar.LENGTH_SHORT).show();
//            instaDisplayInfoDialogView();
        }

    }

    @NonNull
    private InstagramApp.OAuthAuthenticationListener createInstaListener(final Handler handler) {
        return new InstagramApp.OAuthAuthenticationListener() {

            @Override
            public void onSuccess() {
                Toast.makeText(SignUpActivity.this, "onSuccess", Toast.LENGTH_SHORT)
                        .show();
                // userInfoHashmap = mApp;
                mApp.fetchUserName(handler);
            }

            @Override
            public void onFail(String error) {
                Toast.makeText(SignUpActivity.this, error, Toast.LENGTH_SHORT)
                        .show();
            }
        };
    }

    @NonNull
    private Handler getInstaHandler() {
        return new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == InstagramApp.WHAT_FINALIZE) {
                    userInfoHashmap = mApp.getUserInfo();
                }
                // the following is not needed:
//                else if (msg.what == InstagramApp.WHAT_FINALIZE) {
//                    Toast.makeText(SignUpActivity.this, "Check your network.",
//                            Toast.LENGTH_SHORT).show();
//                }
                return false;
            }
        });
    }

    private void instaDisplayInfoDialogView() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(
                SignUpActivity.this);
        alertDialog.setTitle("Profile Info");

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.profile_view, (FrameLayout) findViewById(R.id.root));
        alertDialog.setView(view);
        TextView tvName = view.findViewById(R.id.tvUserName);
        TextView tvNoOfFollwers = view
                .findViewById(R.id.tvNoOfFollowers);
        TextView tvNoOfFollowing = view
                .findViewById(R.id.tvNoOfFollowing);

        ImageView ivProfile = view
                .findViewById(R.id.ivProfileImage);

        String url = userInfoHashmap.get(InstagramApp.TAG_PROFILE_PICTURE);
        Picasso.get().load(url).into(ivProfile);

        String name = userInfoHashmap.get(InstagramApp.TAG_USERNAME);
        tvName.setText(name == null ? "null" : name);
        tvNoOfFollowing.setText(userInfoHashmap.get(InstagramApp.TAG_FOLLOWS));
        tvNoOfFollwers.setText(userInfoHashmap
                .get(InstagramApp.TAG_FOLLOWED_BY));
        alertDialog.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        googleLogin.googleOnActivityResult(requestCode, data, RC_SIGN_IN);
    }

    public void signup() {
        Log.d(TAG, "Signup");
        if (!validate()) {
            onSignupFailed();
            return;
        }

        btn_signup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

//        String name = input_name.getText().toString();
//        String email = input_email.getText().toString();
//        String password = input_password.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onSignupSuccess() {
        Utils.USER_TYPE = (temp_user_type == ADVERTISER & temp_user_type != -1) ? ADVERTISER : GREED;
        invalidateOptionsMenu();
        btn_signup.setEnabled(true);
//        setResult(RESULT_OK, null);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        btn_signup.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = input_name.getText().toString();
        String email = input_email.getText().toString();
        String password = input_password.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            input_name.setError("at least 3 characters");
            valid = false;
        } else {
            input_name.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.setError("enter a valid email address");
            valid = false;
        } else {
            input_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            input_password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            input_password.setError(null);
        }

        return valid;
    }


    private void doinstalogin() {
        mApp.authorize();
        String name = userInfoHashmap.get(InstagramApp.TAG_USERNAME);
        Snackbar.make(findViewById(R.id.root), "welcome: " + name, Snackbar.LENGTH_SHORT).show();
        // todo: logout code is {mApp.resetAccessToken();}
    }

    @Override
    public void onClick(View v) {
// Check which radio button was clicked
        switch (v.getId()) {
            case R.id.btn_signup:
                signup();
                break;
            case R.id.insta_login:
                doinstalogin();
                break;
            case R.id.link_login:
                finish();
                break;
            case R.id.connectWithFbButton:
                LoginManager.getInstance().logInWithReadPermissions(
                        this,
//                        Arrays.asList("user_photos", "email", "user_birthday", "public_profile")
                        Arrays.asList("email", "public_profile")
                );
                break;
            case R.id.google_login:
                googleLogin.googleSignIn(RC_SIGN_IN);
                break;
            case R.id.twitter_login:
                mTwitterAuthClient.authorize(this, new Callback<TwitterSession>() {
                    @Override
                    public void success(Result<TwitterSession> result) {

//                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
//                TwitterAuthToken authToken = session.getAuthToken();
//                String token = authToken.token;
//                String secret = authToken.secret;

                        // Do something with result, which provides a TwitterSession for making API calls
                        String username = result.data.getUserName();
                        Toast.makeText(SignUpActivity.this, "Hello " + username, Toast.LENGTH_SHORT).show();
                        // todo: read the docs in:
                        // https://github.com/twitter/twitter-kit-android/wiki/Log-In-with-Twitter
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        // Do something on failure
                        Toast.makeText(SignUpActivity.this, "failure!", Toast.LENGTH_SHORT).show();
                    }

                });
                break;
        }

    }

    @Override
    public void updateui(GoogleSignInAccount account) {
        if (account == null) {
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, account.getDisplayName(), Toast.LENGTH_SHORT).show();
        }
    }
}
