package com.example.skatt.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

public class MainActivity extends AppCompatActivity {

    int money;
    int hp;
    Card[] cards;
    ImageView card_center;
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

    int card_animation_duration = 1000;

    AnimatorSet card_reset_column_right = new AnimatorSet();
    AnimatorSet card_reset_column_center = new AnimatorSet();
    AnimatorSet card_reset_column_left = new AnimatorSet();
    AnimatorSet card_reset_row_top = new AnimatorSet();
    AnimatorSet card_reset_row_center = new AnimatorSet();
    AnimatorSet card_reset_row_bottom = new AnimatorSet();

    AnimatorSet card_animation_click_right = new AnimatorSet();
    AnimatorSet card_animation_left = new AnimatorSet();
    AnimatorSet card_animation_get_right = new AnimatorSet();

    AnimatorSet card_animation_click_left = new AnimatorSet();
    AnimatorSet card_animation_right = new AnimatorSet();
    AnimatorSet card_animation_get_left = new AnimatorSet();

    AnimatorSet card_animation_click_top = new AnimatorSet();
    AnimatorSet card_animation_down = new AnimatorSet();
    AnimatorSet card_animation_get_top = new AnimatorSet();

    AnimatorSet card_animation_click_bottom = new AnimatorSet();
    AnimatorSet card_animation_up = new AnimatorSet();
    AnimatorSet card_animation_get_bottom = new AnimatorSet();

    boolean isAnimate = false;

    AnimatorListenerAdapter animator_listener_adapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            isAnimate = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            isAnimate = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        money_text = findViewById(R.id.money_text);
        hp_text = findViewById(R.id.hp_text);

        hp_view = findViewById(R.id.hp);

        hp_bar = findViewById(R.id.hp_bar);
        hp_bar.getDrawable().setLevel(10000);

        card_center = findViewById(R.id.card_view_5);
        cards = new Card[8];
        for (int i = 0; i < 8; i++) {
            cards[i] = new Card();
        }

        cards[0].layout = findViewById(R.id.card_table_1);
        cards[1].layout = findViewById(R.id.card_table_2);
        cards[2].layout = findViewById(R.id.card_table_3);
        cards[3].layout = findViewById(R.id.card_table_4);
        cards[4].layout = findViewById(R.id.card_table_6);
        cards[5].layout = findViewById(R.id.card_table_7);
        cards[6].layout = findViewById(R.id.card_table_8);
        cards[7].layout = findViewById(R.id.card_table_9);

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

        cards[0].damage_text = findViewById(R.id.card_damage_1);
        cards[1].damage_text = findViewById(R.id.card_damage_2);
        cards[2].damage_text = findViewById(R.id.card_damage_3);
        cards[3].damage_text = findViewById(R.id.card_damage_4);
        cards[4].damage_text = findViewById(R.id.card_damage_6);
        cards[5].damage_text = findViewById(R.id.card_damage_7);
        cards[6].damage_text = findViewById(R.id.card_damage_8);
        cards[7].damage_text = findViewById(R.id.card_damage_9);

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
    public void onWindowFocusChanged(boolean hasFocus) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        money_text_size = displayMetrics.heightPixels * money_text_size_constant;
        money_text.setTextSize(COMPLEX_UNIT_PX, money_text_size);

        card_name_text_size = (float) (Math.sqrt(Math.pow(cards[0].imageView.getWidth(), 2.0) +
                Math.pow(cards[0].imageView.getHeight(), 2.0)) * card_name_text_size_constant);
        card_hp_and_damage_text_size = (float) (Math.sqrt(Math.pow(cards[0].imageView.getWidth(), 2.0) +
                Math.pow(cards[0].imageView.getHeight(), 2.0)) * card_hp_and_damage_text_size_constant);
        for (int i = 0; i < 8; i++) {
            cards[i].name_text.setTextSize(COMPLEX_UNIT_PX, card_name_text_size);
            cards[i].hp_text.setTextSize(COMPLEX_UNIT_PX, card_hp_and_damage_text_size);
            cards[i].damage_text.setTextSize(COMPLEX_UNIT_PX, card_hp_and_damage_text_size);
        }

        hp_text_size = (float) (Math.sqrt(Math.pow(hp_view.getWidth(), 2.0) +
                Math.pow(hp_view.getHeight(), 2.0)) * hp_text_size_constant);
        hp_text.setTextSize(COMPLEX_UNIT_PX, hp_text_size);

        int[] card_5_coordinates = new int[2];
        int[] card_6_coordinates = new int[2];
        int[] card_8_coordinates = new int[2];
        int[] card_1_coordinates = new int[2];
        int[] card_2_coordinates = new int[2];
        int[] card_3_coordinates = new int[2];
        int[] card_4_coordinates = new int[2];
        int[] card_7_coordinates = new int[2];
        int[] card_9_coordinates = new int[2];

        cards[0].imageView.getLocationOnScreen(card_1_coordinates);
        cards[1].imageView.getLocationOnScreen(card_2_coordinates);
        cards[2].imageView.getLocationOnScreen(card_3_coordinates);
        cards[3].imageView.getLocationOnScreen(card_4_coordinates);
        card_center.getLocationOnScreen(card_5_coordinates);
        cards[4].imageView.getLocationOnScreen(card_6_coordinates);
        cards[5].imageView.getLocationOnScreen(card_7_coordinates);
        cards[6].imageView.getLocationOnScreen(card_8_coordinates);
        cards[7].imageView.getLocationOnScreen(card_9_coordinates);

        int card_distance_between_Y = card_5_coordinates[1] - card_8_coordinates[1];
        int card_distance_between_X = card_6_coordinates[0] - card_5_coordinates[0];
        int card_coordinates_animation_top_start = -card_8_coordinates[1]-cards[0].imageView.getHeight();
        int card_coordinates_animation_bottom_start = displayMetrics.heightPixels+cards[0].imageView.getHeight()-card_8_coordinates[1];
        int card_coordinates_animation_right_start = displayMetrics.widthPixels+cards[0].imageView.getWidth()-card_6_coordinates[0];
        int card_coordinates_animation_left_start = -card_4_coordinates[0]-cards[0].imageView.getWidth();

        Log.d("+", String.valueOf(card_5_coordinates[0] - card_6_coordinates[0]));

        card_animation_left.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(cards[0].layout, View.TRANSLATION_X, 0f, card_coordinates_animation_left_start),
                ObjectAnimator.ofFloat(cards[3].layout, View.TRANSLATION_X, 0f, card_coordinates_animation_left_start),
                ObjectAnimator.ofFloat(cards[5].layout, View.TRANSLATION_X, 0f, card_coordinates_animation_left_start),

                ObjectAnimator.ofFloat(cards[1].layout, View.TRANSLATION_X, 0f, -card_distance_between_X),
                ObjectAnimator.ofFloat(cards[2].layout, View.TRANSLATION_X, 0f, -card_distance_between_X),
                ObjectAnimator.ofFloat(cards[4].layout, View.TRANSLATION_X, 0f, -card_distance_between_X),
                ObjectAnimator.ofFloat(cards[6].layout, View.TRANSLATION_X, 0f, -card_distance_between_X),
                ObjectAnimator.ofFloat(cards[7].layout, View.TRANSLATION_X, 0f, -card_distance_between_X),
                ObjectAnimator.ofFloat(card_center, View.TRANSLATION_X, 0f, -card_distance_between_X)
        );

        card_animation_right.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(cards[2].layout, View.TRANSLATION_X, 0f, card_coordinates_animation_right_start),
                ObjectAnimator.ofFloat(cards[4].layout, View.TRANSLATION_X, 0f, card_coordinates_animation_right_start),
                ObjectAnimator.ofFloat(cards[7].layout, View.TRANSLATION_X, 0f, card_coordinates_animation_right_start),

                ObjectAnimator.ofFloat(cards[0].layout, View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(cards[1].layout, View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(cards[3].layout, View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(cards[5].layout, View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(cards[6].layout, View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(card_center, View.TRANSLATION_X, 0f, card_distance_between_X)
        );

        card_animation_down.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(cards[0].layout, View.TRANSLATION_Y, 0f, card_coordinates_animation_bottom_start),
                ObjectAnimator.ofFloat(cards[1].layout, View.TRANSLATION_Y, 0f, card_coordinates_animation_bottom_start),
                ObjectAnimator.ofFloat(cards[2].layout, View.TRANSLATION_Y, 0f, card_coordinates_animation_bottom_start),

                ObjectAnimator.ofFloat(cards[3].layout, View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(cards[4].layout, View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(cards[5].layout, View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(cards[6].layout, View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(cards[7].layout, View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(card_center, View.TRANSLATION_Y, 0f, card_distance_between_Y)
        );

        card_animation_up.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(cards[5].layout, View.TRANSLATION_Y, 0f, card_coordinates_animation_top_start),
                ObjectAnimator.ofFloat(cards[6].layout, View.TRANSLATION_Y, 0f, card_coordinates_animation_top_start),
                ObjectAnimator.ofFloat(cards[7].layout, View.TRANSLATION_Y, 0f, card_coordinates_animation_top_start),

                ObjectAnimator.ofFloat(cards[1].layout, View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(cards[2].layout, View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(cards[3].layout, View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(cards[4].layout, View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(cards[0].layout, View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(card_center, View.TRANSLATION_Y, 0f, -card_distance_between_Y)
        );

        card_animation_get_top.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(cards[5].layout, View.TRANSLATION_Y, card_coordinates_animation_top_start, 0f),
                ObjectAnimator.ofFloat(cards[6].layout, View.TRANSLATION_Y, card_coordinates_animation_top_start, 0f),
                ObjectAnimator.ofFloat(cards[7].layout, View.TRANSLATION_Y, card_coordinates_animation_top_start, 0f)
        );

        card_animation_get_bottom.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(cards[0].layout, View.TRANSLATION_Y, card_coordinates_animation_bottom_start, 0f),
                ObjectAnimator.ofFloat(cards[1].layout, View.TRANSLATION_Y, card_coordinates_animation_bottom_start, 0f),
                ObjectAnimator.ofFloat(cards[2].layout, View.TRANSLATION_Y, card_coordinates_animation_bottom_start, 0f)
        );

        card_animation_get_left.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(cards[0].layout, View.TRANSLATION_X, card_coordinates_animation_left_start, 0f),
                ObjectAnimator.ofFloat(cards[3].layout, View.TRANSLATION_X, card_coordinates_animation_left_start, 0f),
                ObjectAnimator.ofFloat(cards[5].layout, View.TRANSLATION_X, card_coordinates_animation_left_start, 0f)
        );

        card_animation_get_right.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(cards[2].layout, View.TRANSLATION_X, card_coordinates_animation_right_start, 0f),
                ObjectAnimator.ofFloat(cards[4].layout, View.TRANSLATION_X, card_coordinates_animation_right_start, 0f),
                ObjectAnimator.ofFloat(cards[7].layout, View.TRANSLATION_X, card_coordinates_animation_right_start, 0f)
        );

        card_reset_row_top.playTogether(
                ObjectAnimator.ofFloat(cards[5].layout, View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(cards[6].layout, View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(cards[7].layout, View.TRANSLATION_Y, 0f, 0f)
        );

        card_reset_row_center.playTogether(
                ObjectAnimator.ofFloat(cards[3].layout, View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(card_center, View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(cards[4].layout, View.TRANSLATION_Y, 0f, 0f)
        );

        card_reset_row_bottom.playTogether(
                ObjectAnimator.ofFloat(cards[0].layout, View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(cards[1].layout, View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(cards[2].layout, View.TRANSLATION_Y, 0f, 0f)
        );

        card_reset_column_right.playTogether(
                ObjectAnimator.ofFloat(cards[2].layout, View.TRANSLATION_X, 0f, 0f),
                ObjectAnimator.ofFloat(cards[4].layout, View.TRANSLATION_X, 0f, 0f),
                ObjectAnimator.ofFloat(cards[7].layout, View.TRANSLATION_X, 0f, 0f)
        );

        card_reset_column_left.playTogether(
                ObjectAnimator.ofFloat(cards[0].layout, View.TRANSLATION_X, 0f, 0f),
                ObjectAnimator.ofFloat(cards[3].layout, View.TRANSLATION_X, 0f, 0f),
                ObjectAnimator.ofFloat(cards[5].layout, View.TRANSLATION_X, 0f, 0f)
        );

        card_reset_column_center.playTogether(
                ObjectAnimator.ofFloat(cards[1].layout, View.TRANSLATION_X, 0f, 0f),
                ObjectAnimator.ofFloat(card_center, View.TRANSLATION_X, 0f, 0f),
                ObjectAnimator.ofFloat(cards[6].layout, View.TRANSLATION_X, 0f, 0f)
        );

        card_animation_click_right.playSequentially(
                card_animation_left,
                card_reset_column_left,
                card_reset_column_center,
                card_animation_get_right
        );

        card_animation_click_left.playSequentially(
                card_animation_right,
                card_reset_column_right,
                card_reset_column_center,
                card_animation_get_left
        );

        card_animation_click_top.playSequentially(
                card_animation_down,
                card_reset_row_bottom,
                card_reset_row_center,
                card_animation_get_top
        );

        card_animation_click_bottom.playSequentially(
                card_animation_up,
                card_reset_row_top,
                card_reset_row_center,
                card_animation_get_bottom
        );

        card_animation_click_right.addListener(animator_listener_adapter);
        card_animation_click_left.addListener(animator_listener_adapter);
        card_animation_click_top.addListener(animator_listener_adapter);
        card_animation_click_bottom.addListener(animator_listener_adapter);
    }

    public void on_Click_Card_Right(View view) {

        if(!isAnimate){
            card_animation_click_right.start();
        }
    }

    public void on_Click_Card_Left(View view) {

        if(!isAnimate){
            card_animation_click_left.start();
        }
    }

    public void on_Click_Card_Top(View view) {

        if(!isAnimate){
            card_animation_click_top.start();
        }
    }

    public void on_Click_Card_Bottom(View view) {

        if(!isAnimate){
            card_animation_click_bottom.start();
        }
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