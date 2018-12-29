package com.example.asus.fiveh.loginproviders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.asus.fiveh.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class GoogleLogin {
    private static final String TAG = GoogleLogin.class.getSimpleName();
    public static final int RC_SIGN_IN = 9001;
    private SignInButton WOWsignInButton;

    private AppCompatActivity context;
    private GoogleSignInClient mGoogleSignInClient;

    public GoogleLogin(Context context) {
        this.context = (AppCompatActivity) context;
    }

    public void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        context.startActivityForResult(signInIntent, GoogleLogin.RC_SIGN_IN);
    }

    public void googleOnCreate() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken("631557339763-2o6k85slbs4oqcm9ne8i2dti4jjgjhvu.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    public void googleOnActivityResult(int requestCode, Intent data, int RC_SIGN_IN) {
        if (requestCode == RC_SIGN_IN) {
            handleSignInResult(data);
        }
    }

    private void handleSignInResult(Intent data) {
        try {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = task.getResult(ApiException.class);
            ((UPDATEui) context).updateui(account);
        } catch (ApiException e) {
            Log.w(TAG, "Google sign in failed " + e.getStatusCode());
            ((UPDATEui) context).updateui(null);
        }
    }

    public interface UPDATEui {
        void updateui(GoogleSignInAccount account);
    }
}
