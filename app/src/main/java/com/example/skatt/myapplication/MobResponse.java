package com.example.skatt.myapplication;

class MobResponse {
    private byte mID;
    private int mValueOne;
    private byte mValueTwo;
    private int mMoney;
    private int mType;
    private int mGearScore;
    private int mSubType;
    private int mExperience;

    public byte getID() {
        return mID;
    }
    public int getValueOne() {
        return mValueOne;
    }
    public byte getValueTwo() {
        return mValueTwo;
    }
    public int getMoney() {
        return mMoney;
    }
    public int getType() {
        return mType;
    }
    public int getGearScore() {
        return mGearScore;
    }
    public int getSubType() {
        return mSubType;
    }
    public int getExperience() {
        return mExperience;
    }

    public void setID(byte ID) {
        mID = ID;
    }

    public void setValueOne(int valueOne) {
        mValueOne = valueOne;
    }

    public void setValueTwo(byte valueTwo) {
        mValueTwo = valueTwo;
    }

    public void setMoney(int money) {
        mMoney = money;
    }

    public void setType(int type) {
        mType = type;
    }

    public void setGearScore(int gearScore) {
        mGearScore = gearScore;
    }

    public void setSubType(int subType) {
        mSubType = subType;
    }

    public void setExperience(int experience) {
        mExperience = experience;
    }
}