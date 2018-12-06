package com.example.asus.fiveh.loginproviders;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.fiveh.R;
import com.example.asus.fiveh.insta.InstagramApp;

import java.util.HashMap;

//import com.squareup.picasso.Picasso;

public class InstagramLogin {

    private AppCompatActivity context;

    public InstagramLogin(AppCompatActivity context) {
        this.context = context;
    }

    private InstagramApp mApp;
    private HashMap<String, String> userInfoHashmap = new HashMap<>();

    @NonNull
    private InstagramApp.OAuthAuthenticationListener createInstaListener(final Handler handler) {
        return new InstagramApp.OAuthAuthenticationListener() {

            @Override
            public void onSuccess() {
                Toast.makeText(context, "onSuccess", Toast.LENGTH_SHORT)
                        .show();
                // userInfoHashmap = mApp;
                mApp.fetchUserName(handler);
            }

            @Override
            public void onFail(String error) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT)
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

    public void instaDisplayInfoDialogView() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(
                context);
        alertDialog.setTitle("Profile Info");

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.profile_view, (FrameLayout) context.findViewById(R.id.root));
        alertDialog.setView(view);
        TextView tvName = view.findViewById(R.id.tvUserName);
        TextView tvNoOfFollwers = view
                .findViewById(R.id.tvNoOfFollowers);
        TextView tvNoOfFollowing = view
                .findViewById(R.id.tvNoOfFollowing);

        ImageView ivProfile = view
                .findViewById(R.id.ivProfileImage);

        String url = userInfoHashmap.get(InstagramApp.TAG_PROFILE_PICTURE);
        Glide.with(context).load(url).into(ivProfile);

        String name = userInfoHashmap.get(InstagramApp.TAG_USERNAME);
        tvName.setText(name == null ? "null" : name);
        tvNoOfFollowing.setText(userInfoHashmap.get(InstagramApp.TAG_FOLLOWS));
        tvNoOfFollwers.setText(userInfoHashmap
                .get(InstagramApp.TAG_FOLLOWED_BY));
        alertDialog.create().show();
    }

    public void doinstalogin() {
        mApp.authorize();
        String name = userInfoHashmap.get(InstagramApp.TAG_USERNAME);
        Snackbar.make(context.findViewById(R.id.root), "welcome: " + name, Snackbar.LENGTH_SHORT).show();
        // todo: logout code is {mApp.resetAccessToken();}
    }

    public void instaOnCreate() {
        String client_id = context.getResources().getString(R.string.insta_client_id);
        String client_secret = context.getResources().getString(R.string.insta_client_secret);
        String client_callbackuri = context.getResources().getString(R.string.insta_client_callbackuri);

        mApp = new InstagramApp(context, client_id, client_secret, client_callbackuri);

        final Handler handler = getInstaHandler();
        mApp.setListener(createInstaListener(handler));

        if (mApp.hasAccessToken()) {
            mApp.fetchUserName(handler);
            String name = userInfoHashmap.get(InstagramApp.TAG_USERNAME);
            Snackbar.make(context.findViewById(R.id.root), "hello " + name, Snackbar.LENGTH_SHORT).show();
//            instaDisplayInfoDialogView();
        }

    }
}
