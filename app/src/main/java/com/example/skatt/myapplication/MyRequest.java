package com.example.skatt.myapplication;

public class MyRequest {
    private String key;
    private String data;

    public MyRequest(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
    public String getData() {
        return data;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public void setData(String data) {
        this.data = data;
    }
}