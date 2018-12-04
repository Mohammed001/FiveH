package com.example.asus.fiveh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.fiveh.loginproviders.FacebookLogin;
import com.example.asus.fiveh.loginproviders.GoogleLogin;
import com.example.asus.fiveh.loginproviders.InstagramLogin;
import com.example.asus.fiveh.loginproviders.TwitterLogin;
import com.example.asus.fiveh.utils.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import static com.example.asus.fiveh.utils.Utils.ADVERTISER;
import static com.example.asus.fiveh.utils.Utils.GREED;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, GoogleLogin.UPDATEui {

    private static final String TAG = SignUpActivity.class.getSimpleName();

    TextInputEditText input_name;
    TextInputEditText input_email;
    TextInputEditText input_password;

    Button btn_signup;
    TextView link_login;


    int temp_user_type = -1;
    private static final int RC_SIGN_IN = 9001;

    GoogleLogin googleLogin;
    FacebookLogin facebookLogin;
    InstagramLogin instagramLogin;
    TwitterLogin twitterLogin;

    ImageView insta_login;
    ImageView facebookLoginButton;
    ImageView googleloginButton;
    ImageView twitterbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        twitterLogin.initTwitter();
        setContentView(R.layout.activity_sign_up);

        setlistener();
        initviews();

        googleLogin = new GoogleLogin(this);
        facebookLogin = new FacebookLogin(this);
        instagramLogin = new InstagramLogin(this);
        twitterLogin = new TwitterLogin(this);

        facebookLogin.initfb();
        instagramLogin.instaOnCreate();
        googleLogin.googleOnCreate();
        twitterLogin.twitterOnCreate();
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
        facebookLoginButton = findViewById(R.id.connectWithFbButton);
    }

    private void setlistener() {
        btn_signup.setOnClickListener(this);
        link_login.setOnClickListener(this);
        insta_login.setOnClickListener(this);
        googleloginButton.setOnClickListener(this);
        twitterbtn.setOnClickListener(this);
        facebookLoginButton.setOnClickListener(this);
    }

    // this method is needed to accomplish {GoogleSignIn} process
    @Override
    public void onStart() {
        super.onStart();
        googleLogin.googleOnStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookLogin.facebookInOnActivityResult(requestCode, resultCode, data);
        googleLogin.googleOnActivityResult(requestCode, data, RC_SIGN_IN);
        twitterLogin.twitterOnActivityResult(requestCode, resultCode, data);
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

    @Override
    public void onClick(View v) {
// Check which radio button was clicked
        switch (v.getId()) {
            case R.id.btn_signup:
                signup();
                break;
            case R.id.insta_login:
                instagramLogin.doinstalogin();
                break;
            case R.id.link_login:
                finish();
                break;
            case R.id.connectWithFbButton:
                facebookLogin.doFacebookLogin();
                break;
            case R.id.google_login:
                googleLogin.googleSignIn(RC_SIGN_IN);
                break;
            case R.id.twitter_login:
                twitterLogin.doTwitterLogin();
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
