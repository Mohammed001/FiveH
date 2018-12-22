package com.example.asus.fiveh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
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
import static com.example.asus.fiveh.Intro.BEHAVE_KEY;
import static com.example.asus.fiveh.Intro.BEHAVE_LOGIN;
import static com.example.asus.fiveh.Intro.BEHAVE_SIGNUP;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, GoogleLogin.UPDATEui {

    private static final String TAG = SignUpActivity.class.getSimpleName();

    static final String DEBUGEMAIL = "hazem@sadv.sa";
    static final String DEBUGPASSWORD = "12345";
    static final int RC_SIGN_IN = 9001;

    static String behaviour;

    String email;
    String password;
    String password2;

    TextInputEditText retype_pass;
    TextInputEditText input_password;
    TextInputEditText input_email;

    ProgressDialog progressDialog;
    Button btn_signup;
    TextView link_login;
    RadioGroup radio_group;

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
        twitterLogin = new TwitterLogin(this);
        twitterLogin.initTwitter();
        setContentView(R.layout.sign_up);

        initviews();
        setlistener();

        googleLogin = new GoogleLogin(this);
        facebookLogin = new FacebookLogin(this);
        instagramLogin = new InstagramLogin(this);

        facebookLogin.initfb();
        instagramLogin.instaOnCreate();
        googleLogin.googleOnCreate();
        twitterLogin.twitterOnCreate();

        Intent intent = getIntent();
        behaviour = intent.getStringExtra(BEHAVE_KEY);
        if (behaviour != null && behaviour.equals(BEHAVE_LOGIN)) {
            link_login.setText(getString(R.string.no_account_yet_create_one_tv));
            retype_pass.setVisibility(View.GONE);
            radio_group.setVisibility(View.GONE);
            btn_signup.setText(getString(R.string.login_btn));
        }
    }

    private void initviews() {

        radio_group = findViewById(R.id.radio_group);
        retype_pass = findViewById(R.id.retype_pass);
        twitterbtn = findViewById(R.id.twitter_login);
        retype_pass = findViewById(R.id.retype_pass2);
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

    public void login() {
        //        email = _emailText.getText().toString();
//        password = _passwordText.getText().toString();

        email = DEBUGEMAIL;
        password = DEBUGPASSWORD;
        password2 = password;

//        email = input_email.getText().toString();
//        password = input_password.getText().toString();
//        password2 = retype_pass.getText().toString();

        if (!validate()) {
            Toast.makeText(getBaseContext(), "Login failed ", Toast.LENGTH_LONG).show();
            btn_signup.setEnabled(true);
            return;
        }

        btn_signup.setEnabled(false);

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.authing_mag));
        progressDialog.show();

        connectToServer();
    }

    public void connectToServer() {
        btn_signup.setEnabled(true);
        RetrofitAPI service = new RetrofitClient().getAuthClient().create(RetrofitAPI.class);
        Call<Response> call = null;
        if (behaviour.equals(BEHAVE_LOGIN)) {
            call = service.call_5H_login(email, password);
        } else if (behaviour.equals(BEHAVE_SIGNUP)) {
            call = service.call_5H_signup(email, password);
        }
        if (call != null) {
            call.enqueue(getResponseCallback());
        }
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
        Toast.makeText(SignUpActivity.this, "onFailure " + t.getMessage(), Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
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
            input_email.setError("enter a valid email address");
            valid = false;
        } else {
            input_email.setError(null);
        }

        if (password.isEmpty()) {
            input_password.setError("password is empty");
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
                login();
                break;
            case R.id.insta_login:
                instagramLogin.doinstalogin();
                break;
            case R.id.link_login:
                if (behaviour.equals(BEHAVE_LOGIN)) {
                    // convert to BEHAVE_LOGOUT

                    link_login.setText(getString(R.string.already_a_member_login_tv));
                    btn_signup.setText(getString(R.string.create_account_btn));

                    Animation animation = getfadeInAnimation();

                    retype_pass.startAnimation(animation); // BRING RETYPE PASSWORD
//                    retype_pass.clearAnimation();
                    retype_pass.setVisibility(View.VISIBLE);

                    radio_group.startAnimation(animation);
                    radio_group.setVisibility(View.VISIBLE);

                    behaviour = BEHAVE_SIGNUP;
                } else if (behaviour.equals(BEHAVE_SIGNUP)) {

                    link_login.setText(getString(R.string.no_account_yet_create_one_tv));
                    btn_signup.setText(getString(R.string.login_btn));

                    Animation animation = getfadeoutAnimation();

                    radio_group.startAnimation(animation);
                    radio_group.setVisibility(View.GONE);

                    retype_pass.startAnimation(animation);
                    retype_pass.setVisibility(View.GONE);

                    behaviour = BEHAVE_LOGIN;
                }
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

    @NonNull
    private Animation getfadeInAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        animation.setFillAfter(true);
        return animation;
    }

    @NonNull
    private Animation getfadeoutAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        animation.setFillAfter(true);
        animation.setDuration(100);
        return animation;
    }

    @Override
    public void updateui(GoogleSignInAccount account) {
        if (account != null) {
            Toast.makeText(this, account.getDisplayName(), Toast.LENGTH_SHORT).show();
        }
    }
}
