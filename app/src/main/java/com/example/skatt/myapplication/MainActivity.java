package com.example.skatt.myapplication;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

public class MainActivity extends AppCompatActivity {

    int money;
    int hp;
    Card[] cards;
    boolean mShowingBack = false;
    ImageView hp_view;
    ImageView hp_bar;
    TextView money_text;
    TextView hp_text;
    float money_text_size;
    float money_text_size_constant = 0.0496f;
    float card_name_text_size;
    float card_hp_and_damage_text_size;
    float card_name_text_size_constant = 0.060464f;
    float card_hp_and_damage_text_size_constant = 0.102033f;
    float hp_text_size;
    float hp_text_size_constant = 0.154202f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        money_text = findViewById(R.id.money_text);
        hp_text = findViewById(R.id.hp_text);

        hp_view = findViewById(R.id.hp);

        hp_bar = findViewById(R.id.hp_bar);
        hp_bar.getDrawable().setLevel(10000);

        cards = new Card[8];
        for (int i = 0; i < 8; i++) {
            cards[i] = new Card();
        }

        cards[0].imageView = findViewById(R.id.card_view_1);
        cards[1].imageView = findViewById(R.id.card_view_2);
        cards[2].imageView = findViewById(R.id.card_view_3);
        cards[3].imageView = findViewById(R.id.card_view_4);
        cards[4].imageView = findViewById(R.id.card_view_6);
        cards[5].imageView = findViewById(R.id.card_view_7);
        cards[6].imageView = findViewById(R.id.card_view_8);
        cards[7].imageView = findViewById(R.id.card_view_9);

        cards[0].name_text = findViewById(R.id.card_name_1);
        cards[1].name_text = findViewById(R.id.card_name_2);
        cards[2].name_text = findViewById(R.id.card_name_3);
        cards[3].name_text = findViewById(R.id.card_name_4);
        cards[4].name_text = findViewById(R.id.card_name_6);
        cards[5].name_text = findViewById(R.id.card_name_7);
        cards[6].name_text = findViewById(R.id.card_name_8);
        cards[7].name_text = findViewById(R.id.card_name_9);

        cards[0].hp_text = findViewById(R.id.card_hp_1);
        cards[1].hp_text = findViewById(R.id.card_hp_2);
        cards[2].hp_text = findViewById(R.id.card_hp_3);
        cards[3].hp_text = findViewById(R.id.card_hp_4);
        cards[4].hp_text = findViewById(R.id.card_hp_6);
        cards[5].hp_text = findViewById(R.id.card_hp_7);
        cards[6].hp_text = findViewById(R.id.card_hp_8);
        cards[7].hp_text = findViewById(R.id.card_hp_9);

        cards[0].damege_text = findViewById(R.id.card_damage_1);
        cards[1].damege_text = findViewById(R.id.card_damage_2);
        cards[2].damege_text = findViewById(R.id.card_damage_3);
        cards[3].damege_text = findViewById(R.id.card_damage_4);
        cards[4].damege_text = findViewById(R.id.card_damage_6);
        cards[5].damege_text = findViewById(R.id.card_damage_7);
        cards[6].damege_text = findViewById(R.id.card_damage_8);
        cards[7].damege_text = findViewById(R.id.card_damage_9);

/*
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.layout, new CardFrontFragment())
                    .commit();
        }
*/

/*        ConstraintLayout llBottomSheet = (ConstraintLayout) findViewById(R.id.bottom_sheet);
        TopSheetBehavior bottomSheetBehavior = TopSheetBehavior.from(llBottomSheet);

        bottomSheetBehavior.setTopSheetCallback(new TopSheetBehavior.TopSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });*/
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        money_text_size = displayMetrics.heightPixels*money_text_size_constant;
        money_text.setTextSize(COMPLEX_UNIT_PX, money_text_size);

        Log.d("+", String.valueOf(money_text_size));

        card_name_text_size = (float) (Math.sqrt(Math.pow(cards[0].imageView.getWidth(), 2.0) +
                Math.pow(cards[0].imageView.getHeight(), 2.0)) * card_name_text_size_constant);
        card_hp_and_damage_text_size = (float) (Math.sqrt(Math.pow(cards[0].imageView.getWidth(), 2.0) +
                Math.pow(cards[0].imageView.getHeight(), 2.0)) * card_hp_and_damage_text_size_constant);
        for (int i = 0; i < 8; i++) {
            cards[i].name_text.setTextSize(COMPLEX_UNIT_PX, card_name_text_size);
            cards[i].hp_text.setTextSize(COMPLEX_UNIT_PX, card_hp_and_damage_text_size);
            cards[i].damege_text.setTextSize(COMPLEX_UNIT_PX, card_hp_and_damage_text_size);
        }
        Log.d("+", String.valueOf(card_hp_and_damage_text_size));

        hp_text_size = (float) (Math.sqrt(Math.pow(hp_view.getWidth(), 2.0) +
                Math.pow(hp_view.getHeight(), 2.0)) * hp_text_size_constant);
        hp_text.setTextSize(COMPLEX_UNIT_PX, hp_text_size);

        Log.d("+", String.valueOf(hp_text_size));
    }

    public void onCardClick(View view) {
        int[] a = new int[2];
        cards[0].imageView.getLocationOnScreen(a);
        int[] b = new int[2];
        cards[1].imageView.getLocationOnScreen(b);

        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, -500);
        anim.setDuration(6000);
        anim.setFillAfter(true);
        ConstraintLayout constraintLayout1 = findViewById(R.id.card_table_1);
        ConstraintLayout constraintLayout2 = findViewById(R.id.card_table_2);
        constraintLayout1.setY(constraintLayout1.getY() - 500);
    }

    public void Flip(View view) {
        if (mShowingBack) {

            mShowingBack = false;

            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_left_in,
                            R.animator.card_flip_left_out)
                    .replace(R.id.layout, new CardFrontFragment())
                    .commit();
        } else {

            // Flip to the back.

            mShowingBack = true;

            // Create and commit a new fragment transaction that adds the fragment for
            // the back of the card, uses custom animations, and is part of the fragment
            // manager's back stack.
            getFragmentManager()
                    .beginTransaction()

                    // Replace the default fragment animations with animator resources
                    // representing rotations when switching to the back of the card, as
                    // well as animator resources representing rotations when flipping
                    // back to the front (e.g. when the system Back button is pressed).
                    .setCustomAnimations(
                            R.animator.card_flip_right_in,
                            R.animator.card_flip_right_out)

                    // Replace any fragments currently in the container view with a
                    // fragment representing the next page (indicated by the
                    // just-incremented currentPage variable).
                    .replace(R.id.layout, new CardBackFragment())

                    // Commit the transaction.
                    .commit();
        }
    }
}
