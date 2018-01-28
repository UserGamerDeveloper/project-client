package com.example.skatt.myapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class Custom_Text_View extends android.support.v7.widget.AppCompatTextView{
    private static final String TAG = "TextView";

    public Custom_Text_View(Context context) {
        super(context);
    }

    public Custom_Text_View(Context context, AttributeSet attrs) {
        super(context, attrs);
        Set_Font(context);
    }

    public Custom_Text_View(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Set_Font(context);
    }

    void Set_Font(Context context){
        setTypeface(Typeface.createFromAsset(context.getAssets(), "constantia_bold_italic.ttf"));
    }
/*
    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.Custom_Text_View);
        String customFont = a.getString(R.styleable.Custom_Text_View_customFont);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    public boolean setCustomFont(Context ctx, String asset) {
        Typeface tf = null;
        try {
            tf = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            Log.e(TAG, "Could not get typeface: "+e.getMessage());
            return false;
        }

        setTypeface(tf);
        return true;
    }
*/
}
