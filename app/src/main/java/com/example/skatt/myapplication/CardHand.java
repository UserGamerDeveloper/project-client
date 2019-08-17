package com.example.skatt.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class CardHand extends CardInventory {
    static final byte ID_DEFAULT = 1;
    static final byte FIST_DAMAGE = 1;
    int mIDDrawableDefault;
    ImageView mDurabilityImage;

    public CardHand(Context context) {
        super(context);
    }
    public CardHand(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CardHand(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void load(Stats stats, DBOpenHelper db_open_helper, ItemResponse item) {
        super.load(stats, db_open_helper, item);
        if (isFist()){
            mIdDrawable = mIDDrawableDefault;
            mDurability = 0;
            mDurabilityImage.setVisibility(INVISIBLE);
            mDurabilityText.setVisibility(INVISIBLE);
        }
        else {
            mDurabilityImage.setVisibility(VISIBLE);
            mDurabilityText.setVisibility(VISIBLE);
        }
    }

    @Override
    void open() {
        super.open();
        if (mDurability!=0){
            mDurabilityImage.setVisibility(VISIBLE);
            mDurabilityText.setVisibility(VISIBLE);
        }
    }

    void close(int card_back) {
        super.close(card_back);
        mDurabilityImage.setVisibility(INVISIBLE);
        mDurabilityText.setVisibility(INVISIBLE);
    }

    void copy(CardInventory card){
        super.copy(card);
        this.open();
    }
    void copy(CardInventoryTemp card){
        super.copy(card);
        this.open();
    }

    void setFist(Stats stats){
        mIDItem = ID_DEFAULT;
        setValueOneInUIThread(FIST_DAMAGE+stats.getDamageBonus());
        mType = InventoryType.WEAPON;
        setGearScoreInUIThread(0);
        setMobGearScoreInUIThread(0);
        mCost = 0;
        mDurability = 0;
        mDurabilityMax = 0;
        updateValueOneText();
        mIdDrawable = mIDDrawableDefault;
        mDurabilityImage.setVisibility(INVISIBLE);
        mDurabilityText.setVisibility(INVISIBLE);
        this.mNameText.setText("Кулак");
    }

    void decrementDurability(){
        mDurability--;
    }

    void tryDestroy(Stats stats){
        if(!isFist()){
            mDurability--;
            if (mDurability < 1){
                close(CARD_BACK);
                setFist(stats);
                open();
            }
            else{
                updateDurabilityText();
            }
        }
    }

    //region setters/getters
    boolean isFist(){
        return mIDItem == ID_DEFAULT;
    }

    public int getIDDrawableDefault() {
        return mIDDrawableDefault;
    }
    public void setIDDrawableDefault(int IDDrawableDefault) {
        mIDDrawableDefault = IDDrawableDefault;
    }

    public byte getIDDefault() {
        return ID_DEFAULT;
    }

    void setDurabilityInUIThread(int durability){
        super.setDurability(durability);
        updateDurabilityText();
    }
    //endregion
}