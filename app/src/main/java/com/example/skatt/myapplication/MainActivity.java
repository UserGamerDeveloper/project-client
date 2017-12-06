package com.example.skatt.myapplication;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int money;
    int hp;
    Card[] cards;
    boolean mShowingBack = false;
    ImageView hp_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hp_bar = findViewById(R.id.hp_polosa);
        hp_bar.getDrawable().setLevel(10000);

        cards = new Card[9];
        for (int i = 0; i<9;i++){
            cards[i] = new Card();
        }

        cards[0].imageView = findViewById(R.id.card_view_1);
        cards[1].imageView = findViewById(R.id.card_view_2);
        cards[2].imageView = findViewById(R.id.card_view_3);
        cards[3].imageView = findViewById(R.id.card_view_4);
        cards[4].imageView = findViewById(R.id.card_view_5);
        cards[5].imageView = findViewById(R.id.card_view_6);
        cards[6].imageView = findViewById(R.id.card_view_7);
        cards[7].imageView = findViewById(R.id.card_view_8);
        cards[8].imageView = findViewById(R.id.card_view_9);

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

    public void onCardClick(View view){
        int[] a = new int[2];
        cards[0].imageView.getLocationOnScreen(a);
        int[] b = new int[2];
        cards[1].imageView.getLocationOnScreen(b);

        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, -500);
        anim.setDuration(6000);
        anim.setFillAfter(true);
        ConstraintLayout constraintLayout1 = findViewById(R.id.card_table_1);
        ConstraintLayout constraintLayout2 = findViewById(R.id.card_table_2);
        constraintLayout1.setY(constraintLayout1.getY()-500);
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
        }
        else{

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
