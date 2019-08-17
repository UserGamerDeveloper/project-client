package com.example.skatt.myapplication;

public class MyResponse {
    private String data;
    private Byte error;
    private Byte mGearScore;

    public Byte getGearScore() {
        return mGearScore;
    }
    public void setGearScore(Byte gearScore) {
        mGearScore = gearScore;
    }

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