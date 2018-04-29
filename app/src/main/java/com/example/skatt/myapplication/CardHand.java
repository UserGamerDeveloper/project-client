package com.example.skatt.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class CardHand extends CardInventory {
    static final byte ID_DEFAULT = 0;
    int mIDDrawableDefault;
    TextView mDurabilityText;
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
    protected void setData(Stats stats, Random random, Cursor cursor) {
        super.setData(stats,random,cursor);
        if (mIDItem == ID_DEFAULT){
            mIdDrawable = mIDDrawableDefault;
            mDurability = 0;
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
        updateDurabilityText();
    }
    void copy(CardInventoryTemp card){
        super.copy(card);
        updateDurabilityText();
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

    void updateDurabilityText(){
        if (mDurability/10>1){
            mDurabilityText.setText(
                    String.format("%s", mDurability)
            );
        }
        else{
            mDurabilityText.setText(
                    String.format(" %s", mDurability)
            );
        }
    }
}
