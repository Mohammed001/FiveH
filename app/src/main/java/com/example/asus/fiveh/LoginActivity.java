package com.example.asus.fiveh;

import android.app.ProgressDialog;
import android.content.Intent;
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

import retrofit2.Call;
import retrofit2.Callback;

import static com.example.asus.fiveh.AplicationData.ADVERTISER_INT;
import static com.example.asus.fiveh.AplicationData.GREED_INT;

/**
 * Created by ASUS on 11/11/2018.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleLogin.UPDATEui {
    private static final String TAG = LoginActivity.class.getSimpleName();
    public static final long DELAY = 10_000; // ms
    private static final String USER_AS_STRING = "user";
    private static final String DEBUGEMAIL = "hazem@sadv.sa";
    private static final String DEBUGPASSWORD = "12345";
    private static final int RC_SIGN_IN = 9001;

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
                handle_server_response(response);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                handle_connection_problems(t);
            }
        };
    }

    private void handle_connection_problems(Throwable t) {
        Toast.makeText(LoginActivity.this, "onFailure " + t.getMessage(), Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }

    private void handle_server_response(retrofit2.Response<Response> server_response) {
        Response my_response = server_response.body();
        // it should be not null, but to handle un expected mistakes i will interest in it
        if (my_response != null) {
            Toast.makeText(this, my_response.getMsg(), Toast.LENGTH_SHORT).show();
            if (my_response.getResult().equals("ko")){
                return;
            }
            // result ok..
            User user = my_response.getData();
            // it should be not null, but to handle un expected mistakes i will interest in it
            if (user != null) {
                String userType = user.getUser_type();
                // todo
                // write it in sharedpreferences.

                AplicationData.USER_TYPE_INT = userType.equals(USER_AS_STRING) ? GREED_INT : ADVERTISER_INT;
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
