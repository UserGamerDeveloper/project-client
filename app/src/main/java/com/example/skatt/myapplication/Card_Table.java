package com.example.skatt.myapplication;

import android.animation.AnimatorSet;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

class Card_Table extends Card {

    private static final int cardCenterBack = R.drawable.perekrestok;
    private AnimatorSet mTargetAnimation = new AnimatorSet();
    private AnimatorSet mCloseAnimation = new AnimatorSet();
    private AnimatorSet mChangeAnimation = new AnimatorSet();
    private byte mIdInArray;
    private int value_two;
    TextView value_two_text;
    private int money;
    private int mExperience;
    static float sGearScoreRangeRate;
    static final String[] column_name = {
            DB_Open_Helper.id,
            DB_Open_Helper.name,
            DB_Open_Helper.VALUEONE,
            DB_Open_Helper.VALUETWO,
            DB_Open_Helper.id_image,
            DB_Open_Helper.money,
            DB_Open_Helper.type,
            DB_Open_Helper.GEARSCORE,
            DB_Open_Helper.EXPERIENCE
    };

    public Card_Table(Context context) {
        super(context);
    }
    public Card_Table(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public Card_Table(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void Change(DB_Open_Helper db_open_helper, Random random, int gearScore){
        gearScore = 0;
        SQLiteDatabase data_base = db_open_helper.getReadableDatabase();

        Cursor cursor = data_base.query(
                DB_Open_Helper.table_mobs,
                column_name,
                DB_Open_Helper.GEARSCORE + ">=? AND "+ DB_Open_Helper.GEARSCORE + "<=?",
                new String[]{(gearScore* sGearScoreRangeRate)+"",gearScore+""},
                null,
                null,
                null
        );
        cursor.moveToPosition(random.nextInt(cursor.getCount()));

        setData(cursor);
    }
    void Change(DB_Open_Helper db_open_helper, int id){
        SQLiteDatabase data_base = db_open_helper.getReadableDatabase();

        Cursor cursor = data_base.query(
                DB_Open_Helper.table_mobs,
                column_name,
                DB_Open_Helper.id + "=?",
                new String[]{id+""},
                null,
                null,
                null
        );
        cursor.moveToFirst();

        setData(cursor);
    }
    private void setData(Cursor cursor) {
        mNameText.setText(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(DB_Open_Helper.name)
                )
        );

        mIdDrawable = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.id_image)
        );

        type = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.type)
        );

        this.mGearScore = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.GEARSCORE)
        );
        this.TEST_GearScoreText.setText(String.format("%d", mGearScore));

        if(type== CardTableType.MOB){

            mValueOne = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DB_Open_Helper.VALUEONE)
            );
            value_one_text.setText(
                    String.format(" %s", mValueOne)
            );
            value_two = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DB_Open_Helper.VALUETWO)
            );
            value_two_text.setText(
                    String.format("%s ", value_two)
            );
            money = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DB_Open_Helper.money)
            );
            mExperience = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DB_Open_Helper.EXPERIENCE)
            );
        }

        cursor.close();
    }

    void Copy(Card_Table card){

        super.copy(card);
        this.value_two = card.getValueTwo();
        this.value_two_text.setText(card.value_two_text.getText());
        this.value_two_text.setVisibility(card.value_two_text.getVisibility());
        this.mExperience = card.getExperience();
    }

    void open() {
        super.open();
        if(type== CardTableType.MOB){
            this.value_one_text.setVisibility(View.VISIBLE);
            this.value_two_text.setVisibility(View.VISIBLE);
        }
    }

    void close(int card_back) {
        super.close(card_back);
        this.value_two_text.setVisibility(View.INVISIBLE);
    }
    boolean isClose(){
        return ((mIdDrawable == card_back) || (mIdDrawable ==cardCenterBack));
    }

    public AnimatorSet getTargetAnimation() {
        return mTargetAnimation;
    }
    public void setTargetAnimation(AnimatorSet targetAnimation) {
        mTargetAnimation = targetAnimation;
    }

    int getValueTwo(){
        return value_two;
    }
    void setValueTwo(int damage){
        this.value_two = damage;
    }

    void setValueTwoText(int hp){
        if (hp/10>1){
            value_two_text.setText(
                    String.format("%s", hp)
            );
        }
        else{
            value_two_text.setText(
                    String.format(" %s", hp)
            );
        }
    }

    int Get_Money(){
        return money;
    }
    void Set_Money(int money){
        this.money = money;
    }

    public byte getIdInArray() {
        return mIdInArray;
    }
    public void setIdInArray(byte idInArray) {
        mIdInArray = idInArray;
    }

    public AnimatorSet getCloseAnimation() {
        return mCloseAnimation;
    }
    public void setCloseAnimation(AnimatorSet closeAnimation) {
        mCloseAnimation = closeAnimation;
    }

    public AnimatorSet getChangeAnimation() {
        return mChangeAnimation;
    }
    public void setChangeAnimation(AnimatorSet changeAnimation) {
        mChangeAnimation = changeAnimation;
    }

    public int getExperience() {
        return mExperience;
    }
    public void setExperience(int experience) {
        mExperience = experience;
    }

    public static float getGearScoreRangeRate() {
        return sGearScoreRangeRate;
    }
    public static void setGearScoreRangeRate(float gearScoreRangeRate) {
        sGearScoreRangeRate = gearScoreRangeRate;
    }
}