package com.example.skatt.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Random;

class Card_Inventory extends Card {

    final static String[] COLUMN_NAME = {
            DB_Open_Helper.id,
            DB_Open_Helper.name,
            DB_Open_Helper.VALUEONE,
            DB_Open_Helper.id_image,
            DB_Open_Helper.type,
            DB_Open_Helper.cost,
            DB_Open_Helper.GEARSCORE,
            DB_Open_Helper.MOBGEARSCORE
    };
    int mDurability;
    int mDurabilityMax;
    byte mSlotType;
    byte mSlotId;
    int mCost;
    int TEST_MOB_GEARSCORE;
    TextView TEST_MOB_GEARSCORE_TEXT;

    public Card_Inventory(Context context) {
        super(context);
    }
    public Card_Inventory(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public Card_Inventory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void change(Stats stats, DB_Open_Helper db_open_helper, Random random, int gearScoreMob){
        gearScoreMob = 0;
        SQLiteDatabase data_base = db_open_helper.getReadableDatabase();

        int type = random.nextInt(3);

        Cursor cursor = data_base.query(
                DB_Open_Helper.table_inventory,
                COLUMN_NAME,
                DB_Open_Helper.MOBGEARSCORE + "=? AND " + DB_Open_Helper.type + "=?",
                new String[]{gearScoreMob+"",type+""},
                null,
                null,
                null
        );
        setData(stats, random, cursor);
    }
    void change(Stats stats, DB_Open_Helper db_open_helper, Random random, int gearScoreMob, int type){
        gearScoreMob = 0;
        SQLiteDatabase data_base = db_open_helper.getReadableDatabase();

        Cursor cursor = data_base.query(
                DB_Open_Helper.table_inventory,
                COLUMN_NAME,
                DB_Open_Helper.MOBGEARSCORE + "=? AND " + DB_Open_Helper.type + "=?",
                new String[]{gearScoreMob+"",type+""},
                null,
                null,
                null
        );

        setData(stats, random, cursor);
    }
    void change(Stats stats, DB_Open_Helper db_open_helper, int id){
        SQLiteDatabase data_base = db_open_helper.getReadableDatabase();

        Cursor cursor = data_base.query(
                DB_Open_Helper.table_inventory,
                COLUMN_NAME,
                DB_Open_Helper.id + "=?",
                new String[]{id+""},
                null,
                null,
                null
        );

        setData(stats, new Random(), cursor);
    }

    private void setData(Stats stats, Random random, Cursor cursor) {
        cursor.moveToPosition(random.nextInt(cursor.getCount()));

        this.mGearScore = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.GEARSCORE)
        );
        this.TEST_GearScoreText.setText(String.format("%d", mGearScore));

        TEST_MOB_GEARSCORE = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.MOBGEARSCORE)
        );
        this.TEST_GearScoreText.setText(String.format("%d", TEST_MOB_GEARSCORE));

        mNameText.setText(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(DB_Open_Helper.name)
                )
        );
        this.type = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.type)
        );
        mValueOne = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.VALUEONE)
        );
        switch (type){
            case Inventory_Type.WEAPON :{
                mValueOne +=stats.getDamageBonus();
                break;
            }
            case Inventory_Type.SHIELD :{
                mValueOne +=stats.getDefenceBonus();
                break;
            }
            default:{
                break;
            }
        }
        this.setValueOneText(mValueOne);

        mIdDrawable = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.id_image)
        );


        this.mCost = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.cost)
        );

        mDurabilityMax = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.DURABILITY)
        );
        mDurability = random.nextInt(mDurabilityMax)+1;

        cursor.close();
    }

    void open() {
        super.open();
        this.value_one_text.setVisibility(View.VISIBLE);
    }

    void copy(Card_Inventory card){
        super.copy(card);
        this.value_one_text.setVisibility(View.VISIBLE);
        this.mDurability = card.getDurability();
        this.mCost = card.getCost();
        this.TEST_MOB_GEARSCORE = card.TEST_MOB_GEARSCORE;
        TEST_MOB_GEARSCORE_TEXT.setText(this.TEST_MOB_GEARSCORE+"");
    }
    void copy(Card_Inventory_Temp card){

        this.mValueOne = card.Get_Hp();
        this.mIdDrawable = card.Get_Id_Drawable();
        Picasso.with(getContext()).load(mIdDrawable).placeholder(R.color.color_black).into(imageView);
/*
        this.imageView.setImageResource(mIdDrawable);
*/
        this.mNameText.setText(card.name_text);
        this.setValueOneText(card.value_one);
        this.type = card.Get_Type();
        this.mDurability = card.durability;
        this.mCost = card.Get_Cost();
        this.mGearScore = card.getGearScore();
        this.TEST_GearScoreText.setText(String.format("%d", mGearScore));
        this.TEST_MOB_GEARSCORE = card.TEST_MOB_GEARSCORE;
        TEST_MOB_GEARSCORE_TEXT.setText(this.TEST_MOB_GEARSCORE+"");
    }

    int getCost(){
        return mCost;
    }

    int getDurability(){
        return mDurability;
    }
    void setDurability(int durability){
        this.mDurability = durability;
    }

    public int getDurabilityMax() {
        return mDurabilityMax;
    }

    public byte getSlotType() {
        return mSlotType;
    }
    public void setSlotType(byte slotType) {
        this.mSlotType = slotType;
    }
}