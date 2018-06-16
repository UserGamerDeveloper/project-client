package com.example.skatt.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class Stats {
    private int mLevel;
    private float mRequirementExperienceToNextLevel;
    private float mExperienceValue;
    private TextView mLevelAndExperienceText;
    private int mDamagePoints;
    private TextView mDamageBonusText;
    private int mDefencePoints;
    private TextView mDefenceBonusText;
    private int mHPPoints;
    private int mHPBonus;
    private TextView mHPBonusText;
    private int mPointsTemp;
    private int mPoints;
    private TextView mPointsText;

    LinearLayout mLayout;
    ImageView mDamageButtonMinus;
    ImageView mDamageButtonPlus;
    ImageView mDefenceButtonMinus;
    ImageView mDefenceButtonPlus;
    ImageView mHPButtonMinus;
    ImageView mHPButtonPlus;
    ImageView mConfirmButton;
    ImageView mResetButton;

    class Request {
        private int mDamagePoints;
        private int mDefencePoints;
        private int mHPPoints;

        public Request(int damagePoints, int defencePoints, int HPPoints) {
            mDamagePoints = damagePoints;
            mDefencePoints = defencePoints;
            mHPPoints = HPPoints;
        }

        public int getDamagePoints() {
            return mDamagePoints;
        }
        public int getDefencePoints() {
            return mDefencePoints;
        }
        public int getHPPoints() {
            return mHPPoints;
        }

        public void setDamagePoints(int damagePoints) {
            mDamagePoints = damagePoints;
        }
        public void setDefencePoints(int defencePoints) {
            mDefencePoints = defencePoints;
        }
        public void setHPPoints(int HPPoints) {
            mHPPoints = HPPoints;
        }
    }

    void login(StatsResponse statsResponse){
        mLevel = statsResponse.getLevel();
        mExperienceValue = statsResponse.getExperienceValue();
        mDamagePoints = statsResponse.getDamagePoints();
        mDefencePoints = statsResponse.getDefencePoints();
        mHPPoints = statsResponse.getHPPoints();
        mPoints = statsResponse.getPoints();
        mRequirementExperienceToNextLevel = statsResponse.getRequirementExperienceToNextLevel();
        mHPBonus = statsResponse.getHPBonus();
    }

    void addExperience(int experience){
        mExperienceValue += experience;
        if (mExperienceValue>=mRequirementExperienceToNextLevel){
            mExperienceValue -= mRequirementExperienceToNextLevel;
            mLevel++;
            mPoints++;
        }
        updateLevelAndExperienceTextInThreadUI();
    }
    void updateLevelAndExperienceTextInThreadUI(){
        mLevelAndExperienceText.setText(
                String.format("%d %d/%f", mLevel, (int)mExperienceValue, mRequirementExperienceToNextLevel)
        );
    }
    void removeDamagePoint() {
        setPointsTempValueAndText(mPointsTemp+1);
        mDamageBonusText.setText(String.format("%d", Byte.valueOf(mDamageBonusText.getText().toString())-1));
        tryChangeVisibilityConfirmButton();
        mDamageButtonPlus.setVisibility(View.VISIBLE);
        mDefenceButtonPlus.setVisibility(View.VISIBLE);
        mHPButtonPlus.setVisibility(View.VISIBLE);
        if (mDamagePoints == Byte.valueOf(mDamageBonusText.getText().toString())){
            mDamageButtonMinus.setVisibility(View.GONE);
        }
    }
    void addDamagePoint() {
        setPointsTempValueAndText(mPointsTemp-1);
        mDamageBonusText.setText(String.format("%d", Byte.valueOf(mDamageBonusText.getText().toString())+1));
        mConfirmButton.setVisibility(View.VISIBLE);
        tryChangeVisibilityPlusButton();
        mDamageButtonMinus.setVisibility(View.VISIBLE);
    }
    void removeDefencePoint() {
        setPointsTempValueAndText(mPointsTemp+1);
        mDefenceBonusText.setText(String.format("%d", Byte.valueOf(mDefenceBonusText.getText().toString())-1));
        tryChangeVisibilityConfirmButton();
        mDamageButtonPlus.setVisibility(View.VISIBLE);
        mDefenceButtonPlus.setVisibility(View.VISIBLE);
        mHPButtonPlus.setVisibility(View.VISIBLE);
        if (mDefencePoints == Byte.valueOf(mDefenceBonusText.getText().toString())){
            mDefenceButtonMinus.setVisibility(View.GONE);
        }
    }
    void addDefencePoint() {
        setPointsTempValueAndText(mPointsTemp-1);
        mDefenceBonusText.setText(String.format("%d", Byte.valueOf(mDefenceBonusText.getText().toString())+1));
        mConfirmButton.setVisibility(View.VISIBLE);
        mDefenceButtonMinus.setVisibility(View.VISIBLE);
        tryChangeVisibilityPlusButton();
    }
    void removeHPPoint() {
        setPointsTempValueAndText(mPointsTemp+1);
        mHPBonusText.setText(String.format("%d", Byte.valueOf(mHPBonusText.getText().toString())-1));
        tryChangeVisibilityConfirmButton();
        mDamageButtonPlus.setVisibility(View.VISIBLE);
        mDefenceButtonPlus.setVisibility(View.VISIBLE);
        mHPButtonPlus.setVisibility(View.VISIBLE);
        if (mHPPoints == Byte.valueOf(mHPBonusText.getText().toString())){
            mHPButtonMinus.setVisibility(View.GONE);
        }
    }
    void addHPPoint() {
        setPointsTempValueAndText(mPointsTemp-1);
        mHPBonusText.setText(String.format("%d", Byte.valueOf(mHPBonusText.getText().toString())+1));
        mConfirmButton.setVisibility(View.VISIBLE);
        tryChangeVisibilityPlusButton();
        mHPButtonMinus.setVisibility(View.VISIBLE);
    }
    private void tryChangeVisibilityConfirmButton() {
        if (mDamagePoints == Integer.valueOf(mDamageBonusText.getText().toString())&&
                mDefencePoints == Integer.valueOf(mDefenceBonusText.getText().toString()) &&
                mHPPoints == Integer.valueOf(mHPBonusText.getText().toString()))
        {
            mConfirmButton.setVisibility(View.GONE);
        }
    }

    String confirm(ObjectMapper jackson) {
        mPoints = mPointsTemp;
        mDamagePoints = Integer.valueOf(mDamageBonusText.getText().toString());
        mDefencePoints = Integer.valueOf(mDefenceBonusText.getText().toString());
        mHPPoints = Integer.valueOf(mHPBonusText.getText().toString());
        mDamageButtonMinus.setVisibility(View.GONE);
        mDefenceButtonMinus.setVisibility(View.GONE);
        mHPButtonMinus.setVisibility(View.GONE);
        if (mPoints!=0){
            mDamageButtonPlus.setVisibility(View.VISIBLE);
            mDefenceButtonPlus.setVisibility(View.VISIBLE);
            mHPButtonPlus.setVisibility(View.VISIBLE);
        }
        mConfirmButton.setVisibility(View.GONE);
        mResetButton.setClickable(true);
        mResetButton.setVisibility(View.VISIBLE);
        try {
            return jackson.writeValueAsString(new Request(mDamagePoints, mDefencePoints, mHPPoints));
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    void reset(){
        setPointsValueAndText(mLevel);
        mPointsTemp = mPoints;
        mDamagePoints = 0;
        mDamageBonusText.setText(String.format("%d", mDamagePoints));
        mDefencePoints = 0;
        mDefenceBonusText.setText(String.format("%d", mDefencePoints));
        mHPPoints = 0;
        mHPBonusText.setText(String.format("%d", mHPPoints));
        mDamageButtonPlus.setVisibility(View.VISIBLE);
        mDefenceButtonPlus.setVisibility(View.VISIBLE);
        mHPButtonPlus.setVisibility(View.VISIBLE);
        mResetButton.setClickable(false);
        mResetButton.setVisibility(View.INVISIBLE);
    }

    void setVisibility() {
        if (mLayout.getVisibility()==View.GONE){
            setPointsTempValueAndText(mPoints);
            mDamageBonusText.setText(String.valueOf(mDamagePoints));
            mDefenceBonusText.setText(String.valueOf(mDefencePoints));
            mHPBonusText.setText(String.valueOf(mHPPoints));
            mConfirmButton.setVisibility(View.GONE);
            mDamageButtonMinus.setVisibility(View.GONE);
            mDefenceButtonMinus.setVisibility(View.GONE);
            mHPButtonMinus.setVisibility(View.GONE);
            tryChangeVisibilityPlusButton();
            if (mLevel==mPoints){
                mResetButton.setVisibility(View.INVISIBLE);
                mResetButton.setClickable(false);
            }
            mLayout.setVisibility(View.VISIBLE);
        }
        else{
            mLayout.setVisibility(View.GONE);
        }
    }

    private void tryChangeVisibilityPlusButton() {
        if (mPointsTemp==0){
            mDamageButtonPlus.setVisibility(View.GONE);
            mDefenceButtonPlus.setVisibility(View.GONE);
            mHPButtonPlus.setVisibility(View.GONE);
        }
    }

    //region setters/getters
    int getHPBonus() {
        return mHPPoints*mHPBonus;
    }
    int getDamageBonus() {
        return mDamagePoints;
    }
    int getDefenceBonus() {
        return mDefencePoints;
    }

    private void setPointsTempValueAndText(int points){
        mPointsTemp = points;
        setPointsText(points);
    }
    private void setPointsValueAndText(int points){
        mPoints = points;
        setPointsText(points);
    }
    private void setPointsText(int points){
        mPointsText.setText(String.format("Количество очков: %d", points));
    }

    void setLayout(View statsLayout) {
        mLayout = (LinearLayout) statsLayout;
    }
    void setDamageButtonPlus(View statsDamageButton) {
        mDamageButtonPlus = (ImageView) statsDamageButton;
    }
    void setDefenceButtonPlus(View statsDefenceButton) {
        mDefenceButtonPlus = (ImageView) statsDefenceButton;
    }
    void setHPButtonPlus(View statsHPButton) {
        mHPButtonPlus = (ImageView) statsHPButton;
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
    public void setDamageButtonMinus(View damageButtonMinus) {
        mDamageButtonMinus = (ImageView) damageButtonMinus;
    }
    public void setDefenceButtonMinus(View defenceButtonMinus) {
        mDefenceButtonMinus = (ImageView) defenceButtonMinus;
    }
    public void setHPButtonMinus(View HPButtonMinus) {
        mHPButtonMinus = (ImageView) HPButtonMinus;
    }
    public void setConfirmButton(View confirmButton) {
        mConfirmButton = (ImageView) confirmButton;
    }
    //endregion
}