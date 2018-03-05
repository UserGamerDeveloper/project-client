package com.example.skatt.myapplication;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogWindow {
    ConstraintLayout mLayout;
    TextView mText;
    ImageView mButtonOk;
    ImageView mButtonYes;
    ImageView mButtonNo;

    public void setLayout(View layout) {
        mLayout = (ConstraintLayout)layout;
    }
    public void setText(View text) {
        mText = (TextView)text;
    }
    void setButtonOk(View buttonOk) {
        mButtonOk = (ImageView)buttonOk;
        mButtonOk.setVisibility(View.GONE);
        mButtonOk.setOnClickListener(getOnClickButtonOkListener());
    }
    void setButtonYes(View buttonYes) {
        mButtonYes = (ImageView)buttonYes;
        mButtonYes.setVisibility(View.GONE);
    }
    void setButtonNo(View buttonNo) {
        mButtonNo = (ImageView) buttonNo;
        mButtonNo.setVisibility(View.GONE);
        mButtonNo.setOnClickListener(getOnClickButtonNoListener());
    }

    private View.OnClickListener getOnClickButtonOkListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.setVisibility(View.GONE);
                mButtonOk.setVisibility(View.GONE);
            }
        };
    }
    private View.OnClickListener getOnClickButtonNoListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        };
    }

    void close(){
        mLayout.setVisibility(View.GONE);
        mButtonYes.setVisibility(View.GONE);
        mButtonNo.setVisibility(View.GONE);
    }

    void openInfo(String text){
        mLayout.setVisibility(View.VISIBLE);
        mText.setText(text);
        mButtonOk.setVisibility(View.VISIBLE);
    }
    void openDialog(String text, View.OnClickListener onClickButtonYesListener){
        mLayout.setVisibility(View.VISIBLE);
        mButtonNo.setVisibility(View.VISIBLE);
        mButtonYes.setVisibility(View.VISIBLE);
        mButtonYes.setOnClickListener(onClickButtonYesListener);
        mText.setText(text);
    }
}
