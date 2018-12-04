package com.example.asus.fiveh.loginproviders;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.asus.fiveh.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class GoogleLogin {
    public static final String TAG = GoogleLogin.class.getSimpleName();

    AppCompatActivity context;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;

    public GoogleLogin(Context context) {
        this.context = (AppCompatActivity) context;
    }

    public void googleSignIn(int RC_SIGN_IN) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        context.startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void googleOnCreate() {
        signInButton = context.findViewById(R.id.sign_in_button);
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("631557339763-2o6k85slbs4oqcm9ne8i2dti4jjgjhvu.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                googleSignIn();
//            }
//        });
    }

    public void googleSignout() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(context, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "googleSignout", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void googleOnStart() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        ((UPDATEui) context).updateui(account);
    }

    public void googleOnActivityResult(int requestCode, Intent data, int RC_SIGN_IN) {
        if (requestCode == RC_SIGN_IN) {
            handleSignInResult(data);
        }
    }


    public void handleSignInResult(Intent data) {
        try {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = task.getResult(ApiException.class);
            ((UPDATEui) context).updateui(account);
        } catch (ApiException e) {
            Log.w(TAG, "Google sign in failed " + e.getStatusCode());
            ((UPDATEui) context).updateui(null);
        }
    }

    public void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(context, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ((UPDATEui) context).updateui(null);
                    }
                });
    }

    public interface UPDATEui {
        void updateui(GoogleSignInAccount account);
    }
}
