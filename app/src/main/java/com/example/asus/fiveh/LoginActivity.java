package com.example.asus.fiveh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.fiveh.loginproviders.FacebookLogin;
import com.example.asus.fiveh.loginproviders.GoogleLogin;
import com.example.asus.fiveh.loginproviders.InstagramLogin;
import com.example.asus.fiveh.loginproviders.TwitterLogin;
import com.example.asus.fiveh.models.Response;
import com.example.asus.fiveh.utils.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;

import static com.example.asus.fiveh.utils.Utils.ADVERTISER;
import static com.example.asus.fiveh.utils.Utils.GREED;

/**
 * Created by ASUS on 11/11/2018.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleLogin.UPDATEui {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final long DELAY = 3000;

    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        twitterLogin.initTwitter();
        setContentView(R.layout.activity_login);

        initViews();
        setListener();

        googleLogin = new GoogleLogin(this);
        facebookLogin = new FacebookLogin(this);
        instagramLogin = new InstagramLogin(this);
        twitterLogin = new TwitterLogin(this);

        facebookLogin.initfb();
        instagramLogin.instaOnCreate();
        googleLogin.googleOnCreate();
        twitterLogin.twitterOnCreate();

    }

    private void setListener() {
        _loginButton.setOnClickListener(this);
        _signupLink.setOnClickListener(this);
    }

    private void initViews() {
        _emailText = findViewById(R.id.email);
        _passwordText = findViewById(R.id.password);
        _loginButton = findViewById(R.id.email_sign_in_button);
        _signupLink = findViewById(R.id.link_signup);
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

    String email;
    String password;

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        email = _emailText.getText().toString();
        password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either connectToServer or onLoginFailed
//                        onloginsuccess();
                        connectToServer();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, DELAY);
    }

    public void connectToServer() {
        _loginButton.setEnabled(true);


        RetrofitAPI service = new RetrofitClient().getClient().create(RetrofitAPI.class);
        Call<Response> call = service.call_5H_server(email, password);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Log.i(TAG, "onResponse: " + (response.body() != null ? response.body().getMsg() : "null"));
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "onFailure login", Toast.LENGTH_SHORT).show();
            }
        });

        Utils.USER_TYPE = new Random().nextInt(2) + 1 == ADVERTISER ? ADVERTISER : GREED;
        invalidateOptionsMenu();
//        finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();

        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_sign_in_button:
                login();
                break;

            case R.id.link_signup:
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
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
