package com.example.asus.fiveh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.asus.fiveh.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;

import static com.example.asus.fiveh.ApplicationData.APP_PREFERENCES_FILE;
import static com.example.asus.fiveh.ApplicationData.GREED;
import static com.example.asus.fiveh.ApplicationData.USER_DATA;
import static com.example.asus.fiveh.ApplicationData.USER_TYPE;
import static com.example.asus.fiveh.SignUpActivity.DEBUGEMAIL;
import static com.example.asus.fiveh.SignUpActivity.DEBUGPASSWORD;
import static com.example.asus.fiveh.SignUpActivity.RC_SIGN_IN;

/**
 * Created by ASUS on 11/11/2018.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleLogin.UPDATEui {
    private static final String TAG = LoginActivity.class.getSimpleName();

    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;
    ProgressDialog progressDialog;

    ImageView insta_login;

    String email;
    String password;

    GoogleLogin googleLogin;
    FacebookLogin facebookLogin;
    InstagramLogin instagramLogin;
    TwitterLogin twitterLogin;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        twitterLogin = new TwitterLogin(this);
        twitterLogin.initTwitter();
        setContentView(R.layout.login);

        initViews();
        setListeners();

        googleLogin = new GoogleLogin(this);
        facebookLogin = new FacebookLogin(this);
        instagramLogin = new InstagramLogin(this);

        facebookLogin.initfb();
        instagramLogin.instaOnCreate();
        googleLogin.googleOnCreate();
        twitterLogin.twitterOnCreate();

    }

    private void setListeners() {
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
    public void updateui(GoogleSignInAccount account) {
        if (account != null) {
            Toast.makeText(this, "hello " + account.getDisplayName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookLogin.facebookInOnActivityResult(requestCode, resultCode, data);
        googleLogin.googleOnActivityResult(requestCode, data, RC_SIGN_IN);
        twitterLogin.twitterOnActivityResult(requestCode, resultCode, data);
    }

    public void login() {
        Log.d(TAG, "Login");

//        email = _emailText.getText().toString();
//        password = _passwordText.getText().toString();

        email = DEBUGEMAIL;
        password = DEBUGPASSWORD;

        if (!validate()) {
            Toast.makeText(getBaseContext(), "Login failed ", Toast.LENGTH_LONG).show();
            _loginButton.setEnabled(true);
            return;
        }

        _loginButton.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.authing_mag));
        progressDialog.show();

        connectToServer();
    }

    public void connectToServer() {
        _loginButton.setEnabled(true);
        RetrofitAPI service = new RetrofitClient().getAuthClient().create(RetrofitAPI.class);
        Call<Response> call = service.call_5H_login(email, password);
        call.enqueue(getResponseCallback());
    }

    @NonNull
    private Callback<Response> getResponseCallback() {
        return new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                connection_succeed(response);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                connection_failed(t);
            }
        };
    }

    private void connection_failed(Throwable t) {
        Toast.makeText(LoginActivity.this, "onFailure " + t.getMessage(), Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }

    private void connection_succeed(retrofit2.Response<Response> server_response) {
        Response my_response = server_response.body();
        // it should be not null, but to handle un expected mistakes i will interest in it
        if (my_response != null) {
            Toast.makeText(this, my_response.getMsg(), Toast.LENGTH_SHORT).show();
            if (my_response.getResult().equals("ko")) {
                return;
            }
            // result ok..
            User user = my_response.getData();
            // it should be not null, but to handle un expected mistakes i will interest in it
            if (user != null) {
                // write it in sharedpreferences.
                ApplicationData.current_user = user;
                SharedPreferences sharedPref = getSharedPreferences(APP_PREFERENCES_FILE, Context.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = sharedPref.edit();
                Gson gson = new Gson();
                String user_data = gson.toJson(user);
                prefsEditor.putString(USER_DATA, user_data);
                String user_type = user.getUser_type();
                // todo: user type in response is null!! what a demo.
                user_type = user_type == null || user_type.equals("") ? GREED : user_type;
                prefsEditor.putString(USER_TYPE, user_type);
                prefsEditor.apply();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(USER_TYPE, user_type);
                startActivity(intent);
                finish();

            } else {
                Log.i(TAG, "onResponse: no DATA in json!");
            }
        } else {
            Log.i(TAG, "onResponse: no JSON!");
        }

        progressDialog.dismiss();
    }

    public boolean validate() {
        boolean valid = true;

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//        if (email.isEmpty()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

//        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
        if (password.isEmpty()) {
            _passwordText.setError("no password entered");
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

}
