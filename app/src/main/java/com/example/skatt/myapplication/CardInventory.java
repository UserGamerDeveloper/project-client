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

    final static String[] COLUMN_NAME = {DBOpenHelper.id, DBOpenHelper.name, DBOpenHelper.ID_IMAGE};
    protected Byte mIDItem;
    int mDurability;
    int mDurabilityMax;
    byte mSlotType;
    private byte mSlotId;
    int mCost;
    int TEST_MOB_GEARSCORE;
    TextView TEST_MOB_GEARSCORE_TEXT;
    TextView mDurabilityText;

    public CardInventory(Context context) {
        super(context);
    }
    public CardInventory(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CardInventory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void load(Stats stats, DBOpenHelper db_open_helper, ItemResponse item){
        mIDItem = item.getID();
        mValueOne = item.getValueOne();
        mType = item.getType();
        setGearScoreInUIThread(item.getGearScore());
        setMobGearScoreInUIThread(item.getMobGearScore());
        mCost = item.getCost();
        mDurability = item.getDurability();
        mSlotId = item.getSlotId();
        switch (mType){
            case InventoryType.WEAPON :{
                mValueOne += stats.getDamageBonus();
                mDurabilityMax = item.getDurabilityMax();
                break;
            }
            case InventoryType.SHIELD :{
                mValueOne += stats.getDefenceBonus();
                mDurabilityMax = item.getDurabilityMax();
                break;
            }
            default:{
                break;
            }
        }
        updateDurabilityText();
        updateValueOneText();
        //region DB work
        SQLiteDatabase data_base = db_open_helper.getReadableDatabase();
        Log.d("mIDItem.toString()", mIDItem.toString());
        Cursor cursor = data_base.query(
                DBOpenHelper.TABLE_INVENTORY,
                COLUMN_NAME,
                DBOpenHelper.id + "=?",
                new String[]{mIDItem.toString()},
                null,
                null,
                null
        );
        cursor.moveToFirst();
        mNameText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.name)));
        mIdDrawable = cursor.getInt(cursor.getColumnIndexOrThrow(DBOpenHelper.ID_IMAGE));
        cursor.close();
        //endregion
    }

    void setMobGearScoreInUIThread(int mobGearScore) {
        TEST_MOB_GEARSCORE = mobGearScore;
        this.TEST_MOB_GEARSCORE_TEXT.setText(String.format("%d", TEST_MOB_GEARSCORE));
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
        mDurability = mDurabilityMax;
        this.updateDurabilityText();
    }

    void updateDurabilityText(){
        if (mDurability/10>1){
            mDurabilityText.setText(
                    String.format("%d/%d", mDurability, mDurabilityMax)
            );
        }
        else{
            mDurabilityText.setText(
                    String.format(" %d/%d", mDurability, mDurabilityMax)
            );
        }
    }

    void copy(CardInventory card){
        super.copy(card);
        this.mIDItem = card.getIDItem();
        this.mValueOneText.setVisibility(View.VISIBLE);
        this.mDurability = card.getDurability();
        this.mDurabilityMax = card.getDurabilityMax();
        this.updateDurabilityText();
        this.mCost = card.getBuyCost();
        this.TEST_MOB_GEARSCORE = card.TEST_MOB_GEARSCORE;
        this.TEST_MOB_GEARSCORE_TEXT.setText(this.TEST_MOB_GEARSCORE+"");
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
        this.mDurabilityMax = card.getDurabilityMax();
        this.updateDurabilityText();
        this.mCost = card.Get_Cost();
        this.mGearScore = card.getGearScore();
        this.TEST_GearScoreText.setText(String.format("%d", mGearScore));
        this.TEST_MOB_GEARSCORE = card.TEST_MOB_GEARSCORE;
        this.TEST_MOB_GEARSCORE_TEXT.setText(this.TEST_MOB_GEARSCORE+"");
    }

    //region setters/getters
    boolean isEmpty(){
        return mIDItem == null;
    }

    boolean isWeaponOrShield(){
        return mType == InventoryType.WEAPON || mType == InventoryType.SHIELD;
    }
    public Byte getIDItem() {
        return mIDItem;
    }
    public void setIDItem(Byte IDItem) {
        mIDItem = IDItem;
    }

    int getBuyCost(){
        return mCost;
    }
    int getSellCost(){
        return mCost/2;
    }

    int getDurability(){
        return mDurability;
    }
    void setDurability(int durability){
        this.mDurability = durability;
    }
    void setDurabilityInUIThread(int durability){
        this.mDurability = durability;
        updateDurabilityText();
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
    //endregion
}