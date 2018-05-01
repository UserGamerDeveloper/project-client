package com.example.skatt.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

class CardInventory extends Card {

    final static String[] COLUMN_NAME = {
            DBOpenHelper.id,
            DBOpenHelper.name,
            DBOpenHelper.VALUEONE,
            DBOpenHelper.id_image,
            DBOpenHelper.type,
            DBOpenHelper.cost,
            DBOpenHelper.GEARSCORE,
            DBOpenHelper.MOBGEARSCORE,
            DBOpenHelper.DURABILITY
    };
    protected Byte mIDItem;
    int mDurability;
    int mDurabilityMax;
    byte mSlotType;
    private byte mSlotId;
    int mCost;
    int TEST_MOB_GEARSCORE;
    TextView TEST_MOB_GEARSCORE_TEXT;

    public CardInventory(Context context) {
        super(context);
    }
    public CardInventory(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CardInventory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void load(Stats stats, DBOpenHelper db_open_helper){
        SQLiteDatabase data_base = db_open_helper.getReadableDatabase();

        Log.d("mIDItem.toString()", mIDItem.toString());
        Cursor cursor = data_base.query(
                DBOpenHelper.table_inventory,
                COLUMN_NAME,
                DBOpenHelper.id + "=?",
                new String[]{mIDItem.toString()},
                null,
                null,
                null
        );
        setData(stats, cursor);
    }
    protected void setData(Stats stats, Cursor cursor) {
        cursor.moveToFirst();

        this.mGearScore = cursor.getInt(
                cursor.getColumnIndexOrThrow(DBOpenHelper.GEARSCORE)
        );
        this.TEST_GearScoreText.setText(String.format("%d", mGearScore));

        TEST_MOB_GEARSCORE = cursor.getInt(
                cursor.getColumnIndexOrThrow(DBOpenHelper.MOBGEARSCORE)
        );
        this.TEST_GearScoreText.setText(String.format("%d", TEST_MOB_GEARSCORE));

        mNameText.setText(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(DBOpenHelper.name)
                )
        );
        this.mType = cursor.getInt(
                cursor.getColumnIndexOrThrow(DBOpenHelper.type)
        );
        mValueOne = cursor.getInt(
                cursor.getColumnIndexOrThrow(DBOpenHelper.VALUEONE)
        );
        switch (mType){
            case InventoryType.WEAPON :{
                mValueOne +=stats.getDamageBonus();
                mDurabilityMax = cursor.getInt(
                        cursor.getColumnIndexOrThrow(DBOpenHelper.DURABILITY)
                );
                break;
            }
            case InventoryType.SHIELD :{
                mValueOne +=stats.getDefenceBonus();
                mDurabilityMax = cursor.getInt(
                        cursor.getColumnIndexOrThrow(DBOpenHelper.DURABILITY)
                );
                break;
            }
            default:{
                break;
            }
        }
        this.setValueOneText(mValueOne);

        mIdDrawable = cursor.getInt(
                cursor.getColumnIndexOrThrow(DBOpenHelper.id_image)
        );

        this.mCost = cursor.getInt(
                cursor.getColumnIndexOrThrow(DBOpenHelper.cost)
        );

        cursor.close();
    }

    void open() {
        super.open();
        this.mValueOneText.setVisibility(View.VISIBLE);
    }
    void close(int card_back) {
        super.close(card_back);
        this.mIDItem = null;
    }

    void repair() {
        this.mDurability = mDurabilityMax;
    }

    void copy(CardInventory card){
        super.copy(card);
        mIDItem = card.getIDItem();
        this.mValueOneText.setVisibility(View.VISIBLE);
        this.mDurability = card.getDurability();
        this.mCost = card.getCost();
        this.TEST_MOB_GEARSCORE = card.TEST_MOB_GEARSCORE;
        TEST_MOB_GEARSCORE_TEXT.setText(this.TEST_MOB_GEARSCORE+"");
    }
    void copy(CardInventoryTemp card){
        this.mIDItem = card.getIDItem();
        this.mValueOne = card.Get_Hp();
        this.mIdDrawable = card.Get_Id_Drawable();
        Picasso.with(getContext()).load(mIdDrawable).placeholder(R.color.color_black).into(mImageView);
/*
        this.mImageView.setImageResource(mIdDrawable);
*/
        this.mNameText.setText(card.name_text);
        this.setValueOneText(card.value_one);
        this.mType = card.Get_Type();
        this.mDurability = card.durability;
        this.mCost = card.Get_Cost();
        this.mGearScore = card.getGearScore();
        this.TEST_GearScoreText.setText(String.format("%d", mGearScore));
        this.TEST_MOB_GEARSCORE = card.TEST_MOB_GEARSCORE;
        TEST_MOB_GEARSCORE_TEXT.setText(this.TEST_MOB_GEARSCORE+"");
    }

    boolean isEmpty(){
        return mIDItem == null;
    }

    public Byte getIDItem() {
        return mIDItem;
    }
    public void setIDItem(Byte IDItem) {
        mIDItem = IDItem;
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

    public byte getSlotId() {
        return mSlotId;
    }
    public void setSlotId(byte slotId) {
        mSlotId = slotId;
    }

    public byte getSlotType() {
        return mSlotType;
    }
    public void setSlotType(byte slotType) {
        this.mSlotType = slotType;
    }
}