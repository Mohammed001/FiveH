package com.example.asus.fiveh.models;

public class Response {
    private String result;
    private String msg;
    private ResponseData data;

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

    public ResponseData getData() {
        return data;
    }

    public void setData(ResponseData data) {
        this.data = data;
    }
}
