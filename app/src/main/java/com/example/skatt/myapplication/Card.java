package com.example.skatt.myapplication;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Card extends ConstraintLayout {

    protected static final int card_back = R.drawable.card_back;
    int type;
    int mValueOne;
    int mIdDrawable;
    ImageView imageView;
    TextView mNameText;
    TextView value_one_text;
    int mGearScore;
    TextView TEST_GearScoreText;
    int mSubType;

    public Card(Context context) {
        super(context);
    }
    public Card(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public Card(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void copy(Card card){

        this.mValueOne = card.getValueOne();
        this.mIdDrawable = card.getIdDrawable();
        Picasso.with(getContext()).load(mIdDrawable).placeholder(R.color.color_black).into(imageView);
/*
        this.imageView.setImageResource(mIdDrawable);
*/
        this.mNameText.setText(card.mNameText.getText());
        this.mNameText.setVisibility(card.mNameText.getVisibility());
        this.value_one_text.setText(card.value_one_text.getText());
        this.value_one_text.setVisibility(card.value_one_text.getVisibility());
        this.type = card.getType();
        this.mGearScore = card.getGearScore();
        this.TEST_GearScoreText.setText(String.format("%d", mGearScore));
    }

    void open() {
        this.mNameText.setVisibility(View.VISIBLE);
        Picasso.with(getContext()).load(mIdDrawable).placeholder(R.color.color_black).into(imageView);
/*
        this.imageView.setImageResource(mIdDrawable);
*/
    }

    void close(int card_back) {

        this.mNameText.setVisibility(View.INVISIBLE);
        this.value_one_text.setVisibility(View.INVISIBLE);
        Picasso.with(getContext()).load(card_back).placeholder(R.color.color_black).into(imageView);
/*
        this.imageView.setImageResource(card_back);
*/
        this.mIdDrawable = card_back;
    }

    boolean isClose(){
        return mIdDrawable == card_back;
    }

    int getType(){
        return type;
    }
    void setType(int type){
        this.type = type;
    }

    int getValueOne(){
        return mValueOne;
    }
    void setValueOne(int hp){
        this.mValueOne = hp;
    }

    void setValueOneText(int hp){
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

    int getIdDrawable(){
        return mIdDrawable;
    }
    void setIdDrawable(int id_drawable){
        this.mIdDrawable = id_drawable;
    }

    public int getGearScore() {
        return mGearScore;
    }
    public void setGearScore(int gearScore) {
        mGearScore = gearScore;
        TEST_GearScoreText.setText(String.format("%d", mGearScore));
    }

    public int getSubType() {
        return mSubType;
    }
    public void setSubType(byte subType) {
        mSubType = subType;
    }
}
