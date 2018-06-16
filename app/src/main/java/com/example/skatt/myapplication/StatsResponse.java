package com.example.skatt.myapplication;

public class StatsResponse {
    private byte mLevel;
    private float mExperienceValue;
    private byte mDamagePoints;
    private byte mDefencePoints;
    private byte mHPPoints;
    private byte mPoints;
    private float mRequirementExperienceToNextLevel;
    private int mHPBonus;

    public byte getLevel() {
        return mLevel;
    }
    public float getExperienceValue() {
        return mExperienceValue;
    }
    public byte getDamagePoints() {
        return mDamagePoints;
    }
    public byte getDefencePoints() {
        return mDefencePoints;
    }
    public byte getHPPoints() {
        return mHPPoints;
    }
    public byte getPoints() {
        return mPoints;
    }
    public void setLevel(byte level) {
        mLevel = level;
    }
    public void setExperienceValue(float experienceValue) {
        mExperienceValue = experienceValue;
    }
    public void setDamagePoints(byte damagePoints) {
        mDamagePoints = damagePoints;
    }
    public void setDefencePoints(byte defencePoints) {
        mDefencePoints = defencePoints;
    }
    public void setHPPoints(byte HPPoints) {
        mHPPoints = HPPoints;
    }
    public void setPoints(byte points) {
        mPoints = points;
    }
    public float getRequirementExperienceToNextLevel() {
        return mRequirementExperienceToNextLevel;
    }
    public void setRequirementExperienceToNextLevel(float requirementExperienceToNextLevel) {
        mRequirementExperienceToNextLevel = requirementExperienceToNextLevel;
    }
    public int getHPBonus() {
        return mHPBonus;
    }

    public void setHPBonus(int HPBonus) {
        mHPBonus = HPBonus;
    }
}