package com.example.skatt.myapplication;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Card extends ConstraintLayout {

    int value_one;
    int id_drawable;
    ImageView imageView;
    TextView name_text;
    TextView value_one_text;

    public Card(Context context) {
        super(context);
    }

    public Card(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Card(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void Copy(Card card){

        this.value_one = card.Get_Value_One();
        this.id_drawable = card.Get_Id_Drawable();
        this.imageView.setImageResource(id_drawable);
        this.name_text.setText(card.name_text.getText());
        this.name_text.setVisibility(card.name_text.getVisibility());
        this.value_one_text.setText(card.value_one_text.getText());
        this.value_one_text.setVisibility(card.value_one_text.getVisibility());
    }

    void Open() {

        this.name_text.setVisibility(View.VISIBLE);
        this.value_one_text.setVisibility(View.VISIBLE);
        this.imageView.setImageResource(id_drawable);
    }

    void Close(int card_back) {

        this.name_text.setVisibility(View.INVISIBLE);
        this.value_one_text.setVisibility(View.INVISIBLE);
        this.imageView.setImageResource(card_back);
        this.id_drawable = card_back;
    }

    boolean Is_Close(int card_back){
        return id_drawable == card_back;
    }

    int Get_Value_One(){
        return value_one;
    }

    int Get_Id_Drawable(){
        return id_drawable;
    }

    void Set_Value_One_text(int hp){
        if (hp/10>1){
            value_one_text.setText(
                    String.format("%s", hp)
            );
        }
        else{
            value_one_text.setText(
                    String.format(" %s", hp)
            );
        }
    }

    void Set_Value_One(int hp){
        this.value_one = hp;
    }

    void Set_Id_Drawable(int id_drawable){
        this.id_drawable = id_drawable;
    }
}
