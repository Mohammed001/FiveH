package com.example.asus.fiveh.models;

import com.google.gson.annotations.SerializedName;

public class FiveHResponse {

    @SerializedName("result")
    private String result;

    @SerializedName("msg")
    private String msg;

    @SerializedName("code")
    private String code; // todo: not sure if always

    @SerializedName("data")
    private User data;


    // _______________ (( setters & getters )) _______________

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
