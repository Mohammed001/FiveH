package com.example.asus.fiveh.models;


import com.google.gson.annotations.SerializedName;

public class User {

    // 14 data members

    @SerializedName("primary_email")
    private String primary_email;

    // todo: this is not found in the api docs!
    @SerializedName("account_type")
    private String account_type;

    // _________________


    @SerializedName("user_name")
    private String user_name;

    @SerializedName("user_type")
    private String user_type;


    @SerializedName("user_pass")
    private String user_pass;

    @SerializedName("user_first_name")
    private String user_first_name;

    @SerializedName("user_last_name")
    private String user_last_name;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("user_picture")
    private String user_picture;

    @SerializedName("user_face_id")
    private String user_face_id;

    @SerializedName("user_google_id")
    private String user_google_id;

    @SerializedName("user_insta_id")
    private String user_insta_id;

    @SerializedName("user_twitter_id")
    private String user_twitter_id;

    @SerializedName("user_linkedin_id")
    private String user_linkedin_id;

    @SerializedName("last_login_date")
    private String last_login_date;

    // _______________ (( constructors )) ___________________

    public User(String user_type, String account_type, String user_pass, String user_first_name, String user_last_name, String primary_email, String user_id, String user_picture, String user_name, String user_face_id, String user_google_id, String user_insta_id, String user_twitter_id, String user_linkedin_id, String last_login_date) {
        this.user_type = user_type;
        this.account_type = account_type;
        this.user_pass = user_pass;
        this.user_first_name = user_first_name;
        this.user_last_name = user_last_name;
        this.primary_email = primary_email;
        this.user_id = user_id;
        this.user_picture = user_picture;
        this.user_name = user_name;
        this.user_face_id = user_face_id;
        this.user_google_id = user_google_id;
        this.user_insta_id = user_insta_id;
        this.user_twitter_id = user_twitter_id;
        this.user_linkedin_id = user_linkedin_id;
        this.last_login_date = last_login_date;
    }

    // ________ (( setters )) ________________

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getUser_linkedin_id() {
        return user_linkedin_id;
    }

    public void setUser_linkedin_id(String user_linkedin_id) {
        this.user_linkedin_id = user_linkedin_id;
    }

    public String getUser_insta_id() {
        return user_insta_id;
    }

    public void setUser_insta_id(String user_insta_id) {
        this.user_insta_id = user_insta_id;
    }

    public String getUser_twitter_id() {
        return user_twitter_id;
    }

    public void setUser_twitter_id(String user_twitter_id) {
        this.user_twitter_id = user_twitter_id;
    }

    public String getUser_face_id() {
        return user_face_id;
    }

    public void setUser_face_id(String user_face_id) {
        this.user_face_id = user_face_id;
    }

    public String getUser_google_id() {
        return user_google_id;
    }

    public void setUser_google_id(String user_google_id) {
        this.user_google_id = user_google_id;
    }

    public String getLast_login_date() {
        return last_login_date;
    }

    public void setLast_login_date(String last_login_date) {
        this.last_login_date = last_login_date;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getUser_first_name() {
        return user_first_name;
    }

    public void setUser_first_name(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    public String getUser_last_name() {
        return user_last_name;
    }

    public void setUser_last_name(String user_last_name) {
        this.user_last_name = user_last_name;
    }

    public String getPrimary_email() {
        return primary_email;
    }

    public void setPrimary_email(String primary_email) {
        this.primary_email = primary_email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_picture() {
        return user_picture;
    }

    public void setUser_picture(String user_picture) {
        this.user_picture = user_picture;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

}
