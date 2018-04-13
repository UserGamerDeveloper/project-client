package com.example.skatt.myapplication;

public class MyResponse {
    private String data;
    private Byte error;

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public Byte getError() {
        return error;
    }
    public void setError(Byte error) {
        this.error = error;
    }

    boolean isError(){
        return error!=null;
    }
}