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

    public Card_Table(Context context) {
        super(context);
    }
    public Card_Table(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public Card_Table(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void Change(DB_Open_Helper db_open_helper, Random random){

        SQLiteDatabase data_base = db_open_helper.getReadableDatabase();

        String[] column_name = {
                DB_Open_Helper.id,
                DB_Open_Helper.name,
                DB_Open_Helper.value_one,
                DB_Open_Helper.value_two,
                DB_Open_Helper.id_image,
                DB_Open_Helper.money,
                DB_Open_Helper.type
        };

        Cursor cursor = data_base.query(
                DB_Open_Helper.table_mobs,
                column_name,
                DB_Open_Helper.id + "=?",
                new String[]{String.valueOf(random.nextInt(8))},
/*
                new String[]{0+""},
*/
                null,
                null,
                null
        );

        cursor.moveToFirst();

        name_text.setText(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(DB_Open_Helper.name)
                )
        );

        id_drawable = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.id_image)
        );

        type = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.type)
        );

        if(type==Card_Table_Type.MOB){

            value_one = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DB_Open_Helper.value_one)
            );
            value_one_text.setText(
                    String.format(" %s", value_one)
            );
            value_two = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DB_Open_Helper.value_two)
            );
            value_two_text.setText(
                    String.format("%s ", value_two)
            );
            money = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DB_Open_Helper.money)
            );
        }

        cursor.close();
    }
    void Copy(Card_Table card){

        super.Copy(card);
        this.value_two = card.Get_Damage();
        this.value_two_text.setText(card.value_two_text.getText());
        this.value_two_text.setVisibility(card.value_two_text.getVisibility());
    }

    void Open() {
        super.Open();
        if(type==Card_Table_Type.MOB){
            this.value_one_text.setVisibility(View.VISIBLE);
            this.value_two_text.setVisibility(View.VISIBLE);
        }
    }

    void Close(int card_back) {
        super.Close(card_back);
        this.value_two_text.setVisibility(View.INVISIBLE);
    }
    boolean Is_Close(){
        return ((id_drawable == card_back) || (id_drawable==cardCenterBack));
    }

    public AnimatorSet getTargetAnimation() {
        return mTargetAnimation;
    }
    public void setTargetAnimation(AnimatorSet targetAnimation) {
        mTargetAnimation = targetAnimation;
    }

    int Get_Damage(){
        return value_two;
    }

    void Set_Value_Two(int damage){
        this.value_two = damage;
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
}
