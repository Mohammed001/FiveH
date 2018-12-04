package com.example.asus.fiveh.loginproviders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

public class FacebookLogin {
    private AppCompatActivity context;

    public FacebookLogin(AppCompatActivity context) {
        this.context = context;
    }

    private CallbackManager callbackManager;


    public void initfb() {
        callbackManager = CallbackManager.Factory.create();
        // facebookLoginButton.setAuthType(AUTH_TYPE);
        // If you are using in a fragment, call facebookLoginButton.setFragment(this);

        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager, new
                FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Toast.makeText(context, "onSuccess " + loginResult.getAccessToken().getUserId(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(context, "onCancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(context, "onError", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void facebookInOnActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void doFacebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(
                context,
//                        Arrays.asList("user_photos", "email", "user_birthday", "public_profile")
                Arrays.asList("email", "public_profile")
        );
    }
}
