package com.example.skatt.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

class Stats {
    private int mLevel;
    private final float[] mRequirementExperience;
    private float mExperienceValue;
    private TextView mLevelAndExperienceText;
    private int mDamagePoints;
    private TextView mDamageBonusText;
    private int mDefencePoints;
    private TextView mDefenceBonusText;
    private int mHPPoints;
    private int mHPBonus;
    private TextView mHPBonusText;
    private int mPoints;
    private TextView mPointsText;
    private int mGearScorePerStat;

    LinearLayout mLayout;
    ImageView mDamageButton;
    ImageView mDefenceButton;
    ImageView mHPButton;
    ImageView mResetButton;

    Stats(DB_Open_Helper db_open_helper) {

        SQLiteDatabase data_base = db_open_helper.getReadableDatabase();

        String[] column_name = {
                DB_Open_Helper.LVL,
                DB_Open_Helper.LVL1,
                DB_Open_Helper.LVL2,
                DB_Open_Helper.LVL3,
                DB_Open_Helper.LVL4,
                DB_Open_Helper.LVL5,
                DB_Open_Helper.LVL6,
                DB_Open_Helper.LVL7,
                DB_Open_Helper.LVL8,
                DB_Open_Helper.LVL9,
                DB_Open_Helper.LVL10,
                DB_Open_Helper.LVL11,
                DB_Open_Helper.LVL12,
                DB_Open_Helper.LVL13,
                DB_Open_Helper.LVL14,
                DB_Open_Helper.LVL15,
                DB_Open_Helper.LVL16,
                DB_Open_Helper.LVL17,
                DB_Open_Helper.LVL18,
                DB_Open_Helper.LVL19,
                DB_Open_Helper.LVL20,
                DB_Open_Helper.GSPERSTAT,
                DB_Open_Helper.HPBONUS
        };

        Cursor cursor = data_base.query(
                DB_Open_Helper.sTableTest,
                column_name,
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        mLevel = cursor.getInt(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL));
        mPoints = mLevel;
        mGearScorePerStat = cursor.getInt(cursor.getColumnIndexOrThrow(DB_Open_Helper.GSPERSTAT));
        mHPBonus = cursor.getInt(cursor.getColumnIndexOrThrow(DB_Open_Helper.HPBONUS));
        mRequirementExperience = new float[]{
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL1)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL2)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL3)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL4)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL5)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL6)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL7)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL8)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL9)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL10)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL11)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL12)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL13)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL14)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL15)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL15)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL16)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL17)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL18)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL19)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Open_Helper.LVL20))
        };
        cursor.close();
    }

    void setClickable() {
        if (mPoints==0){
            mDamageButton.setClickable(false);
            mDefenceButton.setClickable(false);
            mHPButton.setClickable(false);
        }
    }

    void addExperience(int experience){
        mExperienceValue += experience;
        if (mExperienceValue>=mRequirementExperience[mLevel]){
            mExperienceValue -= mRequirementExperience[mLevel];
            mLevel++;
            mPoints++;
        }
        mLevelAndExperienceText.setText(
                String.format("%d %d/%d", mLevel, (int)mExperienceValue, (int)mRequirementExperience[mLevel])
        );
    }
    void addDamagePoint() {
        mDamagePoints++;
        setPoints(mPoints-1);
        mDamageBonusText.setText(String.format("Damage: +%d", mDamagePoints));
        setClickable();
        mResetButton.setClickable(true);
    }
    void addDefencePoint() {
        mDefencePoints++;
        setPoints(mPoints-1);
        mDefenceBonusText.setText(String.format("Defence: +%d", mDefencePoints));
        setClickable();
        mResetButton.setClickable(true);
    }
    void addHPPoint() {
        mHPPoints++;
        setPoints(mPoints-1);
        mHPBonusText.setText(String.format("HP: +%d", mHPPoints));
        setClickable();
        mResetButton.setClickable(true);
    }

    void reset(){
        setPoints(mLevel);
        mDamagePoints = 0;
        mDamageBonusText.setText(String.format("Damage: +%d", mDamagePoints));
        mDefencePoints = 0;
        mDefenceBonusText.setText(String.format("Defence: +%d", mDefencePoints));
        mHPPoints = 0;
        mHPBonusText.setText(String.format("HP: +%d", mHPPoints));
        mDamageButton.setClickable(true);
        mDefenceButton.setClickable(true);
        mHPButton.setClickable(true);
        mResetButton.setClickable(false);
    }

    void setPoints(int points){
        mPoints = points;
        mPointsText.setText(String.format("Количество очков: %d", mPoints));
    }

    void setVisibility() {
        if (mLayout.getVisibility()==View.GONE){
            mPointsText.setText(String.format("Количество очков: %d", mPoints));
            setClickable();
            if (mLevel==mPoints){
                mResetButton.setClickable(false);
            }
            mLayout.setVisibility(View.VISIBLE);
        }
        else{
            mLayout.setVisibility(View.GONE);
        }
    }

    int getGearScoreBonus(){
        return (mHPPoints + mDamagePoints + mDefencePoints)*mGearScorePerStat;
    }
    int getDamageBonus() {
        return mDamagePoints;
    }
    int getDefenceBonus() {
        return mDefencePoints;
    }
    int getHPBonus() {
        return mHPPoints*mHPBonus;
    }

    void setLayout(View statsLayout) {
        mLayout = (LinearLayout) statsLayout;
    }
    void setDamageButton(View statsDamageButton) {
        mDamageButton = (ImageView) statsDamageButton;
    }
    void setDefenceButton(View statsDefenceButton) {
        mDefenceButton = (ImageView) statsDefenceButton;
    }
    void setHPButton(View statsHPButton) {
        mHPButton = (ImageView) statsHPButton;
    }
    void setLevelAndExperienceText(View statsExperience) {
        mLevelAndExperienceText = (TextView) statsExperience;
    }
    void setPointsText(View pointsText) {
        mPointsText = (TextView) pointsText;
    }
    void setDamageBonusText(View damageBonusText) {
        mDamageBonusText = (TextView) damageBonusText;
    }
    void setDefenceBonusText(View defenceBonusText) {
        mDefenceBonusText = (TextView) defenceBonusText;
    }
    void setHPBonusText(View HPBonusText) {
        mHPBonusText = (TextView) HPBonusText;
    }
    void setResetButton(View resetButton) {
        mResetButton = (ImageView) resetButton;
    }
}