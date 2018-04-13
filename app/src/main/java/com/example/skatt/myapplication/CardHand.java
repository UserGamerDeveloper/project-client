package com.example.skatt.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

public class CardHand extends CardInventory {

    byte mIDDefault;
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
    void open() {
        super.open();
        if (mDurability!=0){
            mDurabilityImage.setVisibility(VISIBLE);
            mDurabilityText.setVisibility(VISIBLE);
        }
    }

    void copy(CardInventory card){
        super.copy(card);
        setDurabilityText(mDurability);
    }
    void copy(Card_Inventory_Temp card){

        super.copy(card);
        setDurabilityText(mDurability);
    }

    public byte getIDDefault() {
        return mIDDefault;
    }
    public void setIDDefault(byte IDDefault) {
        mIDDefault = IDDefault;
    }

    void setDurabilityInUIThread(int durability){
        super.setDurability(durability);
        setDurabilityText(durability);
    }

    void setDurabilityText(int durability){
        if (durability/10>1){
            mDurabilityText.setText(
                    String.format("%s", durability)
            );
        }
        else{
            mDurabilityText.setText(
                    String.format(" %s", durability)
            );
        }
    }
}
