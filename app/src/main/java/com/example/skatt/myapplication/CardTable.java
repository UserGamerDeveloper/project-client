package com.example.skatt.myapplication;

import android.animation.AnimatorSet;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

class CardTable extends Card {

    private static final int CARD_CENTER_BACK = R.drawable.perekrestok;
    private static final String[] COLUMN_NAME = {
            DBOpenHelper.name,
            DBOpenHelper.VALUEONE,
            DBOpenHelper.VALUETWO,
            DBOpenHelper.id_image,
            DBOpenHelper.money,
            DBOpenHelper.type,
            DBOpenHelper.GEARSCORE,
            DBOpenHelper.EXPERIENCE,
            DBOpenHelper.SUBTYPE
    };
    protected Byte mIDMob;
    private AnimatorSet mTargetAnimation = new AnimatorSet();
    private AnimatorSet mCloseAnimation = new AnimatorSet();
    private AnimatorSet mChangeAnimation = new AnimatorSet();
    private byte mIdInArray;
    private int mValueTwo;
    TextView mValueTwoText;
    private int mMoney;
    private int mExperience;
    private boolean mLogin = false;
    static float sGearScoreRangeRate;

    public CardTable(Context context) {
        super(context);
    }
    public CardTable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CardTable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void load(DBOpenHelper db_open_helper){
        SQLiteDatabase data_base = db_open_helper.getReadableDatabase();
        Cursor cursor = data_base.query(
                DBOpenHelper.table_mobs,
                COLUMN_NAME,
                DBOpenHelper.id + "=?",
                new String[]{mIDMob.toString()},
                null,
                null,
                null
        );
        cursor.moveToFirst();
        //region set data
        mNameText.setText(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(DBOpenHelper.name)
                )
        );

        mIdDrawable = cursor.getInt(
                cursor.getColumnIndexOrThrow(DBOpenHelper.id_image)
        );

        mType = cursor.getInt(
                cursor.getColumnIndexOrThrow(DBOpenHelper.type)
        );
        mSubType = cursor.getInt(
                cursor.getColumnIndexOrThrow(DBOpenHelper.SUBTYPE)
        );

        this.mGearScore = cursor.getInt(
                cursor.getColumnIndexOrThrow(DBOpenHelper.GEARSCORE)
        );
        this.TEST_GearScoreText.setText(String.format("%d", mGearScore));

        if(mType == CardTableType.MOB){

            mValueOne = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DBOpenHelper.VALUEONE)
            );
            mValueOneText.setText(
                    String.format(" %s", mValueOne)
            );
            mValueTwo = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DBOpenHelper.VALUETWO)
            );
            mValueTwoText.setText(
                    String.format("%s ", mValueTwo)
            );
            mMoney = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DBOpenHelper.money)
            );
            mExperience = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DBOpenHelper.EXPERIENCE)
            );
        }
        //endregion
        cursor.close();
    }

    void copy(CardTable card){
        super.copy(card);
        mIDMob = card.getIDMob();
        this.mValueTwo = card.getValueTwo();
        this.mValueTwoText.setText(card.mValueTwoText.getText());
        this.mValueTwoText.setVisibility(card.mValueTwoText.getVisibility());
        this.mExperience = card.getExperience();
    }

    void open() {
        super.open();
        if(mType == CardTableType.MOB){
            this.mValueOneText.setVisibility(View.VISIBLE);
            this.mValueTwoText.setVisibility(View.VISIBLE);
        }
    }

    void close(int card_back) {
        super.close(card_back);
        this.mIDMob = null;
        this.mValueTwoText.setVisibility(View.INVISIBLE);
    }
    boolean isClose(){
        return ((mIdDrawable == CARD_BACK) || (mIdDrawable == CARD_CENTER_BACK));
    }

    boolean isEmpty(){
        return mIDMob == null;
    }

    public Byte getIDMob() {
        return mIDMob;
    }
    public void setIDMob(Byte IDMob) {
        mIDMob = IDMob;
    }

    public AnimatorSet getTargetAnimation() {
        return mTargetAnimation;
    }
    public void setTargetAnimation(AnimatorSet targetAnimation) {
        mTargetAnimation = targetAnimation;
    }

    int getValueTwo(){
        return mValueTwo;
    }
    void setValueTwo(int valueTwo){
        mValueTwo = valueTwo;
    }
    void setValueTwoInUIThread(int damage){
        this.mValueTwo = damage;
        this.updateValueTwoText();
    }
    void changeValueTwo(int delta){
        this.mValueTwo += delta;
    }
    void changeValueTwoInUIThread(int delta){
        this.mValueTwo += delta;
        this.updateValueTwoText();
    }

    void setValueTwoText(int hp){
        if (hp/10>1){
            mValueTwoText.setText(
                    String.format("%s", hp)
            );
        }
        else{
            mValueTwoText.setText(
                    String.format(" %s", hp)
            );
        }
    }
    void updateValueTwoText(){
        if (mValueTwo/10>1){
            mValueTwoText.setText(
                    String.format("%s", mValueTwo)
            );
        }
        else{
            mValueTwoText.setText(
                    String.format(" %s", mValueTwo)
            );
        }
    }

    int getMoney(){
        return mMoney;
    }
    void setMoney(int money){
        this.mMoney = money;
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

    public boolean isLogin() {
        return mLogin;
    }
    public void setLogin(boolean login) {
        mLogin = login;
    }
}