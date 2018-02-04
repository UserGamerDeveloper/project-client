package com.example.skatt.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

public class Card_Hand extends Card_Inventory {

    public Card_Hand(Context context) {
        super(context);
    }
    public Card_Hand(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public Card_Hand(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int hand_drawable;
    TextView durability_text;
    ImageView durability_image;

    void Copy(Card_Inventory card){

        super.Copy(card);
        Set_Durability_Text(durability);
    }

    void Copy(Card_Inventory_Temp card){

        super.Copy(card);
        Set_Durability_Text(durability);
    }

    void Set_Durability_Text(int durability){
        if (durability/10>1){
            durability_text.setText(
                    String.format("%s", durability)
            );
        }
        else{
            durability_text.setText(
                    String.format(" %s", durability)
            );
        }
    }
}
