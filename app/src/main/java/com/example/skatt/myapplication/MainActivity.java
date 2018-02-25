package com.example.skatt.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

public class MainActivity extends AppCompatActivity {

    int mTopGearScoreWeaponOrShieldInInventory;
    ArrayList<Integer> mGearScoreWeaponOrShieldInInventory = new ArrayList<>(6);
    int mGearScore;
    int money;
    int money_bank = 0;
    int hp_max = 30;
    int hp = hp_max;
    Card_Table mCardTableTarget;
    Card_Table[] mCardsTable = new Card_Table[8];
    Card_Inventory[] loot = new Card_Inventory[3];
    Card_Inventory[] inventory = new Card_Inventory[4];
    Card_Hand hand_one;
    Card_Hand hand_two;
    ImageView card_center;
    ConstraintLayout button_start;
    ConstraintLayout button_continue;
    ConstraintLayout trade_zone;

    ImageView shadow;
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
    private int mChanceVendor;
    private int mChanceHalt;
    private int mChanceWeaponOrShield;
    private int mChanceFood;
    private int mChanceSpell;

    AnimatorSet card_reset_column_right = new AnimatorSet();
    AnimatorSet card_reset_row_top = new AnimatorSet();
    AnimatorSet card_reset_row_center = new AnimatorSet();
    AnimatorSet card_reset_row_bottom = new AnimatorSet();

    AnimatorSet card_animation_right = new AnimatorSet();
    AnimatorSet card_animation_get_left = new AnimatorSet();

    AnimatorSet card_animation_down = new AnimatorSet();
    AnimatorSet card_animation_get_top = new AnimatorSet();

    AnimatorSet card_animation_up = new AnimatorSet();
    AnimatorSet card_animation_get_bottom = new AnimatorSet();

    AnimatorSet openCardTable = new AnimatorSet();

    View.OnLongClickListener on_long_click = on_long_click();
    private int mChanceChest;

    View.OnLongClickListener on_long_click() {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (target_swap.getType() == Inventory_Type.FOOD) {
                    Change_HP(target_swap.Get_Value_One());
                    inventory_item_count--;
                    ChangeGearScore(-target_swap.getGearScore());
                    Inventory_Sort();
                    Loot_Get();
                    Log.d("Target_Reset", "use food");
                    Target_Reset();
                    return true;
                }
                if(target_swap.slot_type == Slot_Type.HAND){
                    Card_Hand card_hand = (Card_Hand) target_swap;
                    if (card_hand.Get_Id_Drawable()!=card_hand.hand_drawable){
                        if (inventory_item_count<inventory_item_max_count){
                            inventory[inventory_item_count].Copy(card_hand);
                            inventory[inventory_item_count].setVisibility(View.VISIBLE);
                            inventory_item_count++;
                            ChangeGearScore(getChangeGearScoreAfterReplace(null,0));
                            Set_Hand(card_hand);
                        }
                        else{
                            trade_window.setVisibility(View.VISIBLE);
                            trade_window_text.setText("Нет места в инвентаре.");
                            trade_window_ok.setVisibility(View.VISIBLE);
                        }
                        Log.d("Target_Reset", "hand take off");
                        Target_Reset();
                    }
                    return true;
                }
                return true;
            }
        };
    }

    View.OnTouchListener card_move = card_move();
    View.OnTouchListener card_move() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_MOVE==event.getAction()){
                    if (event.getHistorySize()==1){
                        if (event.getY()<event.getHistoricalY(0)) {
                            ClipData data = ClipData.newPlainText("", "");
                            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(target_swap);
                            target_swap.startDrag(data, shadowBuilder, target_swap, 0);
                            target_swap.setVisibility(View.INVISIBLE);
                            Log.d("INVISIBLE", "onTouch: ");
                        }
                    }
                }
                return false;
            }
        };
    }

    void Change_HP(int hp_delta) {

        hp += hp_delta;
        if (hp < hp_max) {
            hp_text.setText(String.valueOf(hp));
            hp_bar_drawable.setLevel(10000 * hp / hp_max);
        }
        else {
            hp = hp_max;
            hp_text.setText(String.valueOf(hp));
            hp_bar_drawable.setLevel(10000);
        }
    }

    void Inventory_Sort() {

        target_swap.setVisibility(View.VISIBLE);
        byte id = target_swap.slot_id;
        for (; id < inventory_item_count; id++) {
            inventory[id].Copy(inventory[id + 1]);
        }
        inventory[id].setVisibility(View.GONE);
    }

    View.OnDragListener mHandOnDropListener = mHandOnDropListener();

    View.OnDragListener mHandOnDropListener() {
        return new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if (!event.getResult() && event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                    return true;
                }
                if (!event.getResult() && event.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                    target_swap.post(new Runnable() {
                        @Override
                        public void run() {
                            target_swap.setVisibility(View.VISIBLE);
                        }
                    });
                    return false;
                }
                if (event.getAction() == DragEvent.ACTION_DROP) {
                    Card_Hand target_swap_two = ((Card_Hand) v);
                    if (target_swap_two.slot_type!=target_swap.slot_type) {
                        if (target_swap.getType() == Inventory_Type.SHIELD || target_swap.getType() ==
                                Inventory_Type.WEAPON) {
                            if(target_swap_two.durability_text.getVisibility()==View.VISIBLE){
                                inventory_temp.Copy(target_swap_two);
                                target_swap_two.Copy(target_swap);
                                target_swap.Copy(inventory_temp);
                                target_swap.setVisibility(View.VISIBLE);
                            }
                            else{
                                ChangeGearScore(getChangeGearScoreAfterReplace(0,null));
                                target_swap_two.Copy(target_swap);
                                target_swap_two.durability_text.setVisibility(View.VISIBLE);
                                target_swap_two.durability_image.setVisibility(View.VISIBLE);
                                inventory_item_count--;
                                Inventory_Sort();
                                Loot_Get();
                            }
                            Log.d("Target_Reset", "hand swap");
                            Target_Reset();
                            return true;
                        }
                    }
                }
                return false;
            }
        };
    }

    View.OnDragListener mLootOnDropListener = mLootOnDropListener();

    View.OnDragListener mLootOnDropListener() {
        return new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if (!event.getResult() && event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                    return true;
                }
                if (!event.getResult() && event.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                    target_swap.post(new Runnable() {
                        @Override
                        public void run() {
                            target_swap.setVisibility(View.VISIBLE);
                        }
                    });
                    return false;
                }
                if (event.getAction() == DragEvent.ACTION_DROP) {
                    Card_Inventory target_swap_two = ((Card_Inventory) v);
                    if (target_swap.slot_type == Slot_Type.INVENTORY) {
                        if ((target_swap_two.getType() == Inventory_Type.WEAPON ||
                                target_swap_two.getType() == Inventory_Type.SHIELD) &&
                                (target_swap.getType() == Inventory_Type.WEAPON ||
                                        target_swap.getType() == Inventory_Type.SHIELD))
                        {
                            ChangeGearScore(getChangeGearScoreAfterReplace(target_swap.getGearScore(),target_swap_two.getGearScore()));
                        }
                        else{
                            if (target_swap.getType() == Inventory_Type.WEAPON ||
                                    target_swap.getType() == Inventory_Type.SHIELD)
                            {
                                ChangeGearScore(getChangeGearScoreAfterReplace(target_swap.getGearScore(),null));
                                ChangeGearScore(target_swap_two.getGearScore());
                            }
                            else{
                                if (target_swap_two.getType() == Inventory_Type.WEAPON ||
                                        target_swap_two.getType() == Inventory_Type.SHIELD)
                                {
                                    ChangeGearScore(getChangeGearScoreAfterReplace(null,target_swap_two.getGearScore()));
                                    ChangeGearScore(-target_swap.getGearScore());
                                }
                                else{
                                    ChangeGearScore(target_swap_two.getGearScore()-target_swap.getGearScore());
                                }
                            }
                        }
                        inventory_temp.Copy(target_swap_two);
                        target_swap_two.Copy(target_swap);
                        target_swap.Copy(inventory_temp);
                        target_swap.setVisibility(View.VISIBLE);
                        Log.d("Target_Reset", "loot swap");
                        Target_Reset();
                        return true;
                    }
                    else {
                        if (target_swap_two.getType() == Inventory_Type.WEAPON ||
                                        target_swap_two.getType() == Inventory_Type.SHIELD)
                        {
                            if(((Card_Hand)target_swap).durability_text.getVisibility()==View.VISIBLE){
                                        ChangeGearScore(getChangeGearScoreAfterReplace(target_swap.getGearScore(),target_swap_two.getGearScore()));
                                        inventory_temp.Copy(target_swap_two);
                                        target_swap_two.Copy(target_swap);
                                        target_swap.Copy(inventory_temp);
                            }
                            else{
                                        ChangeGearScore(getChangeGearScoreAfterReplace(target_swap.getGearScore(),target_swap_two.getGearScore()));
                                        target_swap.Copy(target_swap_two);
                                        ((Card_Hand)target_swap).durability_text.setVisibility(View.VISIBLE);
                                        ((Card_Hand)target_swap).durability_image.setVisibility(View.VISIBLE);
                                        target_swap_two.setVisibility(View.GONE);
                                        loot_count--;
                                        Try_Continue();
                            }
                            target_swap.setVisibility(View.VISIBLE);
                            Log.d("Target_Reset", "loot swap");
                            Target_Reset();
                            return true;
                        }
                    }
                }
                return false;
            }
        };
    }

    View.OnDragListener mTableOnDropListener = mTableOnDropListener();

    View.OnDragListener mTableOnDropListener() {
        return new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {

                if (!event.getResult() && event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                    return true;
                }
                if (!event.getResult() && event.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                    target_swap.post(new Runnable() {
                        @Override
                        public void run() {
                            target_swap.setVisibility(View.VISIBLE);
                        }
                    });
                    return false;
                }
                if (event.getAction() == DragEvent.ACTION_DROP) {
                        if (target_swap.getType() == Inventory_Type.FOOD) {
                            Change_HP(target_swap.Get_Value_One());
                            ChangeGearScore(-target_swap.getGearScore());
                            inventory_item_count--;
                            Inventory_Sort();
                            Loot_Get();
                            Log.d("Target_Reset", "use food");
                            Target_Reset();
                            return true;
                        }
                }

                return false;
            }
        };
    }

    private int getChangeGearScoreAfterReplace(Object remove, Object add) {
        if (remove!=null){
            mGearScoreWeaponOrShieldInInventory.remove(Integer.valueOf((int)remove));
        }
        if (add!=null){
            mGearScoreWeaponOrShieldInInventory.add((int)add);
        }
        int listSize = mGearScoreWeaponOrShieldInInventory.size();
        Integer[] a = new Integer[listSize];
        mGearScoreWeaponOrShieldInInventory.toArray(a);
        Arrays.sort(a);
        for (Integer b:a) {
            Log.d("mGearScorelist", String.valueOf(b));
        }
        int b = a[listSize-1]+a[listSize-2]-mTopGearScoreWeaponOrShieldInInventory;
        mTopGearScoreWeaponOrShieldInInventory = a[listSize-1]+a[listSize-2];
        return b;
    }

    byte halt_health = 1;
    AnimatorSet card_6_animation_click_vendor = new AnimatorSet();
    View.OnClickListener setTargetListener = setTarget();

    View.OnClickListener setTarget() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_animate) {
                    is_animate = true;
                    Log.d("is_animate", String.valueOf(is_animate));
                    mCardTableTarget = (Card_Table) v;
                    if(mCardTableTarget.getType()== CardTableType.MOB){
                        mCardTableTarget.bringToFront();
                        mCardTableTarget.getTargetAnimation().start();
                        mCardTableTarget.setOnClickListener(damag_listener);
                    }
                    if (mCardTableTarget.getType()==CardTableType.VENDOR){
                        if(mCardTableTarget.getSubType()== CardTableSubType.TRADER){
                            shadow.bringToFront();
                            mCardTableTarget.bringToFront();
/*
                        card_6_animation_click_vendor.start();
*/
                            Picasso.with(getBaseContext()).load(R.drawable.navik_torgovca).into(trade_skill_image);
                            trade_skill_image.setOnClickListener(on_click_vendor_skill);
                            for (byte i = 0; i<loot_max_count;i++){
                                trade_item[i].Change(db_open_helper,random, mGearScore);
                                trade_item[i].Open();
                                trade_item[i].setVisibility(View.VISIBLE);
                                trade_cost[i].setText(String.format("%d", trade_item[i].Get_Cost()));
                                trade_cost[i].setVisibility(View.VISIBLE);
                                trade_cost_image[i].setVisibility(View.VISIBLE);
                            }
                            trade_skill.setVisibility(View.VISIBLE);
                            trade_zone.setVisibility(View.VISIBLE);
                            table.setOnDragListener(null);
                        }
                        if (mCardTableTarget.getSubType()== CardTableSubType.BLACKSMITH){
                            shadow.bringToFront();
                            mCardTableTarget.bringToFront();
/*
                        card_6_animation_click_vendor.start();
*/
                            Picasso.with(getBaseContext()).load(R.drawable.navik_kuznecaa).into(trade_skill_image);
                            trade_skill_image.setOnClickListener(null);
                            for (byte i = 0; i<loot_max_count;i++){
                                trade_item[i].Change(db_open_helper,random, mGearScore, random.nextInt(2));
                                trade_item[i].Open();
                                trade_item[i].setVisibility(View.VISIBLE);
                                trade_item[i].Set_Durability(10);
                                trade_cost[i].setText(String.format("%d", trade_item[i].Get_Cost()));
                                trade_cost[i].setVisibility(View.VISIBLE);
                                trade_cost_image[i].setVisibility(View.VISIBLE);
                            }
                            trade_skill.setVisibility(View.VISIBLE);
                            trade_zone.setVisibility(View.VISIBLE);
                            table.setOnDragListener(null);
                        }
                        if (mCardTableTarget.getSubType()== CardTableSubType.INNKEEPER){
                            shadow.bringToFront();
                            mCardTableTarget.bringToFront();
/*
                        card_6_animation_click_vendor.start();
*/
                            Picasso.with(getBaseContext()).load(R.drawable.navik_traktirshika).into(trade_skill_image);
                            trade_skill_image.setOnClickListener(on_click_innkeeper_skill);
                            for (byte i = 0; i<loot_max_count;i++){
                                trade_item[i].Change(db_open_helper,random, mGearScore, Inventory_Type.FOOD);
                                trade_item[i].Open();
                                trade_item[i].setVisibility(View.VISIBLE);
                                trade_cost[i].setText(String.format("%d", trade_item[i].Get_Cost()));
                                trade_cost[i].setVisibility(View.VISIBLE);
                                trade_cost_image[i].setVisibility(View.VISIBLE);
                            }
                            trade_skill.setVisibility(View.VISIBLE);
                            trade_zone.setVisibility(View.VISIBLE);
                            table.setOnDragListener(null);
                        }
                    }
                    if (mCardTableTarget.getType()== CardTableType.HALT){
                        shadow.bringToFront();
                        mCardTableTarget.bringToFront();
                        mCardTableTarget.getTargetAnimation().start();
                        Change_HP(halt_health);
                        mCardTableTarget.getChangeAnimation().start();
                    }
                    if (mCardTableTarget.getType()== CardTableType.CHEST){
                        shadow.bringToFront();
                        mCardTableTarget.bringToFront();
                        mCardTableTarget.getTargetAnimation().start();
                        mCardTableTarget.getCloseAnimation().start();
                    }
                }
            }
        };
    }

    View.OnClickListener card_trade_click = card_trade_click();
    ConstraintLayout trade_window;
    Card_Inventory trade_target;
    View.OnClickListener card_trade_click() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trade_window.setVisibility(View.VISIBLE);
                if (money>=((Card_Inventory)v).Get_Cost()&&inventory_item_count< inventory_item_max_count){
                    trade_window_no.setVisibility(View.VISIBLE);
                    trade_window_yes.setVisibility(View.VISIBLE);
                    trade_window_yes.setOnClickListener(card_trade_buy_click_yes);
                    trade_target = (Card_Inventory)v;
                    trade_window_text.setText(String.format("Купить карту за %d золотых?", trade_target.Get_Cost()));
                }
                else{
                    trade_window_ok.setVisibility(View.VISIBLE);
                    if (!(inventory_item_count < inventory_item_max_count)){
                        trade_window_text.setText("Нет места в инвентаре.");
                    }
                    else{
                        trade_window_text.setText("Недостаточно золота.");
                    }
                }
            }
        };
    }

    boolean is_first_click = true;
    Card_Inventory target_swap;
    Card_Inventory_Temp inventory_temp = new Card_Inventory_Temp();
    AnimatorListenerAdapter target_on_animation_end = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            Delete_OnClickListener_Before_Anomation_Target_Reset();
        }

        @Override
        public void onAnimationEnd(Animator animation) {

            for (byte i = 0; i < inventory_item_max_count; i++) {
                inventory[i].setOnClickListener(mInventoryOnClickSwap);
            }
            for (int i = 0; i < loot_max_count; i++) {
                loot[i].setOnClickListener(mInventoryOnClickSwap);
            }
            hand_one.setOnClickListener(mInventoryOnClickSwap);
            hand_two.setOnClickListener(mInventoryOnClickSwap);

            target_swap.setOnLongClickListener(on_long_click);
            target_swap.setOnTouchListener(card_move);
        }
    };
    AnimatorListenerAdapter on_target_off_animation = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            Delete_OnClickListener_Before_Anomation_Target_Reset();
            target_swap.setOnLongClickListener(null);
            target_swap.setOnTouchListener(null);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            for (byte i = 0; i < inventory_item_max_count; i++) {
                inventory[i].setOnClickListener(mInventoryOnClickSwap);
            }
            hand_one.setOnClickListener(mInventoryOnClickSwap);
            hand_two.setOnClickListener(mInventoryOnClickSwap);
        }
    };
    Animator target_on_animation;
    Animator target_off_animation;
    View.OnClickListener mInventoryOnClickSwap = mInventoryOnClickSwap();

    View.OnClickListener mInventoryOnClickSwap() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_first_click) {
                    target_swap = (Card_Inventory) v;
                    if (target_swap.slot_type == Slot_Type.HAND) {
                        target_on_animation = ObjectAnimator.ofFloat(target_swap, View.TRANSLATION_Y, 0f, -hand_animation_delta);
                        target_off_animation = ObjectAnimator.ofFloat(target_swap, View.TRANSLATION_Y, -hand_animation_delta, 0f);
                    }
                    if (target_swap.slot_type == Slot_Type.LOOT) {
                        target_on_animation = ObjectAnimator.ofFloat(target_swap, View.TRANSLATION_Y, 0f, -loot_animation_delta);
                        target_off_animation = ObjectAnimator.ofFloat(target_swap, View.TRANSLATION_Y, -loot_animation_delta, 0f);

                    }
                    if (target_swap.slot_type == Slot_Type.INVENTORY) {
                        target_on_animation = ObjectAnimator.ofFloat(target_swap, View.TRANSLATION_Y, 0f, -inventory_animation_delta);
                        target_off_animation = ObjectAnimator.ofFloat(target_swap, View.TRANSLATION_Y, -inventory_animation_delta, 0f);
                    }

                    Log.d("target on", String.valueOf(target_swap.name_text.getText()));
                    is_first_click = false;

                    target_off_animation.addListener(on_target_off_animation);
                    target_on_animation.addListener(target_on_animation_end);
                    target_on_animation.start();

                    if (trade_skill.getVisibility()==View.VISIBLE && mCardTableTarget.getSubType()== CardTableSubType.BLACKSMITH&&
                            (target_swap.getType()==Inventory_Type.SHIELD||target_swap.getType()==Inventory_Type.WEAPON)){
                        trade_skill_image.setOnClickListener(on_click_blacksmith_skill);
                    }
                }
                else {
                    Card_Inventory target_swap_two = (Card_Inventory) v;
                    if (!target_swap.equals(target_swap_two) ) {
                        if (target_swap_two.slot_type!=target_swap.slot_type) {
                            if (target_swap_two.slot_type == Slot_Type.HAND) {
                                Card_Hand targetSwapTwoHand = (Card_Hand) v;
                                if (target_swap.getType() == Inventory_Type.SHIELD || target_swap.getType() ==
                                        Inventory_Type.WEAPON) {
                                    if(targetSwapTwoHand.durability_text.getVisibility()==View.VISIBLE){
                                        inventory_temp.Copy(targetSwapTwoHand);
                                        targetSwapTwoHand.Copy(target_swap);
                                        target_swap.Copy(inventory_temp);
                                    }
                                    else{
                                        ChangeGearScore(getChangeGearScoreAfterReplace(0,null));
                                        targetSwapTwoHand.Copy(target_swap);
                                        targetSwapTwoHand.durability_text.setVisibility(View.VISIBLE);
                                        targetSwapTwoHand.durability_image.setVisibility(View.VISIBLE);
                                        inventory_item_count--;
                                        Inventory_Sort();
                                        Loot_Get();
                                    }
                                    Log.d("Target_Reset", "hand swap click");
                                    Target_Reset();
                                }
                            }
                            if (target_swap_two.slot_type == Slot_Type.LOOT) {
                                if (target_swap.slot_type == Slot_Type.INVENTORY) {
                                    if ((target_swap_two.getType() == Inventory_Type.WEAPON ||
                                            target_swap_two.getType() == Inventory_Type.SHIELD) &&
                                            (target_swap.getType() == Inventory_Type.WEAPON ||
                                                    target_swap.getType() == Inventory_Type.SHIELD))
                                    {
                                        ChangeGearScore(getChangeGearScoreAfterReplace(target_swap.getGearScore(),target_swap_two.getGearScore()));
                                    }
                                    else{
                                        if (target_swap.getType() == Inventory_Type.WEAPON ||
                                                target_swap.getType() == Inventory_Type.SHIELD)
                                        {
                                            ChangeGearScore(getChangeGearScoreAfterReplace(target_swap.getGearScore(),null));
                                            ChangeGearScore(target_swap_two.getGearScore());
                                        }
                                        else{
                                            if (target_swap_two.getType() == Inventory_Type.WEAPON ||
                                                    target_swap_two.getType() == Inventory_Type.SHIELD)
                                            {
                                                ChangeGearScore(getChangeGearScoreAfterReplace(null,target_swap_two.getGearScore()));
                                                ChangeGearScore(-target_swap.getGearScore());
                                            }
                                            else{
                                                ChangeGearScore(target_swap_two.getGearScore()-target_swap.getGearScore());
                                            }
                                        }
                                    }
                                    inventory_temp.Copy(target_swap_two);
                                    target_swap_two.Copy(target_swap);
                                    target_swap.Copy(inventory_temp);
                                    Log.d("Target_Reset", "loot swap click");
                                    Target_Reset();
                                }
                                else {
                                    if (target_swap_two.getType() == Inventory_Type.WEAPON ||
                                            target_swap_two.getType() == Inventory_Type.SHIELD)
                                    {
                                        if(((Card_Hand)target_swap).durability_text.getVisibility()==View.VISIBLE){
                                            ChangeGearScore(getChangeGearScoreAfterReplace(target_swap.getGearScore(),target_swap_two.getGearScore()));
                                            inventory_temp.Copy(target_swap_two);
                                            target_swap_two.Copy(target_swap);
                                            target_swap.Copy(inventory_temp);
                                        }
                                        else{
                                            ChangeGearScore(getChangeGearScoreAfterReplace(target_swap.getGearScore(),target_swap_two.getGearScore()));
                                            target_swap.Copy(target_swap_two);
                                            ((Card_Hand)target_swap).durability_text.setVisibility(View.VISIBLE);
                                            ((Card_Hand)target_swap).durability_image.setVisibility(View.VISIBLE);
                                            target_swap_two.setVisibility(View.GONE);
                                            loot_count--;
                                            Try_Continue();
                                        }
                                        Log.d("Target_Reset", "loot swap click");
                                        Target_Reset();
                                    }
                                }
                            }
                        }
                    }
                    else {
                        Log.d("Target_Reset", "target off");
                        Target_Reset();
                    }
                }
            }
        };
    }

    private void Delete_OnClickListener_Before_Anomation_Target_Reset() {
        for (byte i = 0; i < inventory_item_max_count; i++) {
            inventory[i].setOnClickListener(null);
        }
        for (int i = 0; i < loot_max_count; i++) {
            loot[i].setOnClickListener(null);
        }
        hand_one.setOnClickListener(null);
        hand_two.setOnClickListener(null);
    }

    private void Target_Reset() {
        if (target_swap!=null){
            target_off_animation.start();
            Log.d("target off", (String) target_swap.name_text.getText());
        }
        trade_skill_image.setOnClickListener(null);
        target_swap = null;
        is_first_click = true;
    }

    private void ChangeGearScore(int delta) {
        mGearScore += delta;
        mGearScoreText.setText(String.format("%d", mGearScore));
    }

    int loot_count;
    byte loot_id;
    byte inventory_item_count = 0;
    final byte inventory_item_max_count = 4;
    byte loot_max_count = 3;
    View.OnClickListener damag_listener = Create_Damag_Listener();

    View.OnClickListener Create_Damag_Listener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCardTableTarget.Set_Value_One(mCardTableTarget.Get_Value_One() - ((hand_one.getType() == Inventory_Type.WEAPON) ?
                        hand_one.Get_Value_One() : 0) - ((hand_two.getType() == Inventory_Type.WEAPON) ?
                        hand_two.Get_Value_One() : 0));
                mCardTableTarget.Set_Value_One_text(mCardTableTarget.Get_Value_One());

                HP_Calculation();

                if (hp < 1) {
                    Game_Reset();
                    return;
                }

                if(hand_one.durability_text.getVisibility()==View.VISIBLE){
                    hand_one.Set_Durability(hand_one.Get_Durability()-1);
                    if (hand_one.Get_Durability()<1){
                        ChangeGearScore(getChangeGearScoreAfterReplace(hand_one.getGearScore(),0));
                        Set_Hand(hand_one);
                    }
                    else{
                        hand_one.Set_Durability_Text(hand_one.Get_Durability());
                    }
                }

                if(hand_two.durability_text.getVisibility()==View.VISIBLE){
                    hand_two.Set_Durability(hand_two.Get_Durability()-1);
                    if (hand_two.Get_Durability()<1){
                        ChangeGearScore(getChangeGearScoreAfterReplace(hand_two.getGearScore(),0));
                        Set_Hand(hand_two);
                    }
                    else{
                        hand_two.Set_Durability_Text(hand_two.Get_Durability());
                    }
                }

                if (mCardTableTarget.Get_Value_One() < 1) {

                    mCardTableTarget.setOnClickListener(null);

                    mCardTableTarget.Set_Value_One_text(0);
                    money += mCardTableTarget.Get_Money();
                    money_text.setText(String.valueOf(money));

                    mCardTableTarget.getCloseAnimation().start();
                }
            }

            private void HP_Calculation() {
                int mob_damage = mCardTableTarget.Get_Damage();
                if(mob_damage>0&&hand_one.getType() == Inventory_Type.SHIELD){
                    if (hand_one.Get_Value_One() < mob_damage){
                        mob_damage -= hand_one.Get_Value_One();
                    }
                    else{
                        mob_damage = 0;
                    }
                }
                if(mob_damage>0&&hand_two.getType() == Inventory_Type.SHIELD){
                    if (hand_two.Get_Value_One() < mob_damage){
                        mob_damage -= hand_two.Get_Value_One();
                    }
                    else{
                        mob_damage = 0;
                    }
                }
                if (mob_damage>0){
                    Change_HP(-mob_damage);
                }
            }
        };
    }

    boolean is_loot_enable = false;
    private void Loot_Get() {

        while (((loot_count > 0) && (inventory_item_count < inventory_item_max_count))) {
            if(loot[loot_id].getVisibility()==View.VISIBLE) {
                inventory[inventory_item_count].Copy(loot[loot_id]);
                loot[loot_id].setVisibility(View.GONE);
                inventory[inventory_item_count].setVisibility(View.VISIBLE);
                inventory_item_count++;
                if (loot[loot_id].getType()==Inventory_Type.WEAPON ||
                        loot[loot_id].getType()==Inventory_Type.SHIELD)
                {
                    ChangeGearScore(getChangeGearScoreAfterReplace(null,loot[loot_id].getGearScore()));
                }
                else{
                    ChangeGearScore(loot[loot_id].getGearScore());
                }
                loot_count--;
            }
            loot_id++;
        }

        Try_Continue();
    }

    private void Try_Continue() {
        if (loot_count < 1 && is_loot_enable) {
            on_Click_Button_Continue(null);
        }
    }

    boolean is_animate;
    private final int card_back = R.drawable.card_back;
    private final int cardCenterBack = R.drawable.perekrestok;
    Random random = new Random();

    DB_Open_Helper db_open_helper = new DB_Open_Helper(MainActivity.this);
    SQLiteDatabase data_base;

    Drawable hp_bar_drawable;

    AnimatorSet card_6_animation_reset = new AnimatorSet();

    ConstraintLayout table;

    final class Slot_Type {

        final static byte LOOT = 0;
        final static byte HAND = 1;
        final static byte INVENTORY = 2;

        private Slot_Type() {
        }
    }

    View.OnClickListener card_trade_click_ok = card_trade_click_ok();
    View.OnClickListener card_trade_click_ok() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trade_window.setVisibility(View.GONE);
                trade_window_no.setVisibility(View.GONE);
                trade_window_yes.setVisibility(View.GONE);
                trade_window_ok.setVisibility(View.GONE);
            }
        };
    }

    View.OnClickListener card_trade_buy_click_yes = card_trade_buy_click_yes();
    View.OnClickListener card_trade_buy_click_yes() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trade_window.setVisibility(View.GONE);
                trade_window_no.setVisibility(View.GONE);
                trade_window_yes.setVisibility(View.GONE);
                trade_target.setVisibility(View.INVISIBLE);
                trade_cost[trade_target.slot_id].setVisibility(View.INVISIBLE);
                trade_cost_image[trade_target.slot_id].setVisibility(View.INVISIBLE);
                inventory[inventory_item_count].Copy(trade_target);
                inventory[inventory_item_count].setVisibility(View.VISIBLE);
                inventory_item_count++;
                ChangeGearScore(trade_target.getGearScore());
                Money_Change(-trade_target.Get_Cost());
            }
        };
    }

    View.OnClickListener card_trade_sell_click_yes = card_trade_sell_click_yes();
    View.OnClickListener card_trade_sell_click_yes() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trade_window.setVisibility(View.GONE);
                trade_window_no.setVisibility(View.GONE);
                trade_window_yes.setVisibility(View.GONE);
                target_swap.setVisibility(View.INVISIBLE);
                ChangeGearScore(-target_swap.getGearScore());
                inventory_item_count--;
                Inventory_Sort();
                Money_Change(target_swap.Get_Cost());
                Target_Reset();
            }
        };
    }

    View.OnDragListener on_sell = On_Sell();
    View.OnDragListener On_Sell() {
        return new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {

/*
                if (!event.getResult() && event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                    return true;
                }
*/
                if (event.getAction() == DragEvent.ACTION_DROP) {
                    target_swap.setVisibility(View.VISIBLE);
                    if (target_swap.Get_Id_Drawable()!=R.drawable.kulak_levo||
                            target_swap.Get_Id_Drawable()!=R.drawable.kulak_pravo){
                        trade_window.setVisibility(View.VISIBLE);
                        trade_window_no.setVisibility(View.VISIBLE);
                        trade_window_yes.setVisibility(View.VISIBLE);
                        trade_window_yes.setOnClickListener(card_trade_sell_click_yes);
                        trade_window_text.setText(String.format("Продать карту за %d золотых?", target_swap.Get_Cost()));
                    }
                }
                return true;
            }
        };
    }

    byte cost_vendor_skill = 1;
    View.OnClickListener on_click_vendor_skill = on_click_skill();
    View.OnClickListener on_click_skill() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trade_window.setVisibility(View.VISIBLE);
                if (money>=cost_vendor_skill){
                    trade_window_no.setVisibility(View.VISIBLE);
                    trade_window_yes.setVisibility(View.VISIBLE);
                    trade_window_yes.setOnClickListener(on_click_vendor_skill_yes);
                    trade_window_text.setText(String.format("Обновить ассортимент торговца за %d золотых?", cost_vendor_skill));
                }
                else{
                    trade_window_ok.setVisibility(View.VISIBLE);
                    trade_window_text.setText(String.format("Недостаточно %d золота.", cost_vendor_skill-money));
                }
            }
        };
    }
    View.OnClickListener on_click_vendor_skill_yes = on_click_vendor_skill_yes();
    View.OnClickListener on_click_vendor_skill_yes() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trade_window.setVisibility(View.GONE);
                trade_window_no.setVisibility(View.GONE);
                trade_window_yes.setVisibility(View.GONE);
                Money_Change(-cost_vendor_skill);
                for (byte i = 0; i<loot_max_count;i++){
                    trade_item[i].Change(db_open_helper,random, mGearScore);
                    trade_item[i].Open();
                    trade_item[i].setVisibility(View.VISIBLE);
                    trade_cost[i].setText(String.format("%d", trade_item[i].Get_Cost()));
                    trade_cost[i].setVisibility(View.VISIBLE);
                    trade_cost_image[i].setVisibility(View.VISIBLE);
                }
            }
        };
    }

    View.OnClickListener on_click_blacksmith_skill = on_click_blacksmith_skill();
    View.OnClickListener on_click_blacksmith_skill() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trade_window.setVisibility(View.VISIBLE);
                if (money>=cost_vendor_skill){
                    trade_window_no.setVisibility(View.VISIBLE);
                    trade_window_yes.setVisibility(View.VISIBLE);
                    trade_window_yes.setOnClickListener(on_click_blacksmith_skill_yes);
                    trade_window_text.setText(String.format("Починитm предмет за %d золотых?", cost_vendor_skill));
                }
                else{
                    trade_window_ok.setVisibility(View.VISIBLE);
                    trade_window_text.setText(String.format("Недостаточно %d золота.", cost_vendor_skill-money));
                }
            }
        };
    }
    View.OnClickListener on_click_blacksmith_skill_yes = on_click_blacksmith_skill_yes();
    View.OnClickListener on_click_blacksmith_skill_yes() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trade_window.setVisibility(View.GONE);
                trade_window_no.setVisibility(View.GONE);
                trade_window_yes.setVisibility(View.GONE);
                Money_Change(-cost_vendor_skill);
                target_swap.Set_Durability(10);
                if (target_swap.slot_type==Slot_Type.HAND){
                    ((Card_Hand)target_swap).Set_Durability_Text(10);
                }
                Target_Reset();
            }
        };
    }

    View.OnClickListener on_click_innkeeper_skill = on_click_innkeeper_skill();
    View.OnClickListener on_click_innkeeper_skill() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trade_window.setVisibility(View.VISIBLE);
                if (money>=cost_vendor_skill){
                    trade_window_no.setVisibility(View.VISIBLE);
                    trade_window_yes.setVisibility(View.VISIBLE);
                    trade_window_yes.setOnClickListener(on_click_innkeeper_skill_yes);
                    trade_window_text.setText(String.format("Отдохнуть и восстановить здоровье за %d золотых?", cost_vendor_skill));
                }
                else{
                    trade_window_ok.setVisibility(View.VISIBLE);
                    trade_window_text.setText(String.format("Недостаточно %d золота.", cost_vendor_skill-money));
                }
            }
        };
    }
    View.OnClickListener on_click_innkeeper_skill_yes = on_click_innkeeper_skill_yes();
    View.OnClickListener on_click_innkeeper_skill_yes() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trade_window.setVisibility(View.GONE);
                trade_window_no.setVisibility(View.GONE);
                trade_window_yes.setVisibility(View.GONE);
                Money_Change(-cost_vendor_skill);
                Change_HP(hp_max);
                trade_skill_image.setOnClickListener(null);
            }
        };
    }

    void Money_Change(int delta){
        money += delta;
        money_text.setText(String.format("%d", money));
    }

    TextView[] trade_cost = new TextView[3];
    ImageView[] trade_cost_image = new ImageView[3];
    Card_Inventory[] trade_item = new Card_Inventory[3];
    ConstraintLayout trade_skill;
    TextView trade_window_text;
    ImageView trade_window_ok;
    ImageView trade_window_yes;
    ImageView trade_window_no;
    ImageView trade_skill_image;
    TextView mGearScoreText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGearScoreText = findViewById(R.id.gearScore);

        table = findViewById(R.id.table);
        table.setOnDragListener(mTableOnDropListener);

        trade_skill = findViewById(R.id.trade_skill);
        trade_zone = findViewById(R.id.trade_zone);
        trade_zone.setOnDragListener(on_sell);
        trade_window = findViewById(R.id.trade_window);
        trade_window_text = findViewById(R.id.trade_window_text);
        trade_window_ok = findViewById(R.id.trade_window_ok);
        trade_window_ok.setOnClickListener(card_trade_click_ok);
        trade_window_ok.setVisibility(View.GONE);
        trade_window_yes = findViewById(R.id.trade_window_yes);
        trade_window_yes.setVisibility(View.GONE);
        trade_window_no = findViewById(R.id.trade_window_no);
        trade_window_no.setOnClickListener(card_trade_click_ok);
        trade_window_no.setVisibility(View.GONE);
        trade_skill_image = findViewById(R.id.trade_skill_image);
        trade_skill_image.setOnClickListener(on_click_vendor_skill);

        shadow = findViewById(R.id.shadow);

        button_start = findViewById(R.id.button_start_layout);
        button_continue = findViewById(R.id.button_continue_layout);
        button_continue.setVisibility(View.GONE);

        money_text = findViewById(R.id.money_text);
        hp_text = findViewById(R.id.hp_text);

        hp_view = findViewById(R.id.hp);

        hp_bar = findViewById(R.id.hp_bar);

        hp_bar_drawable = hp_bar.getDrawable();
        hp_bar_drawable.setLevel(10000 * hp / hp_max);

        card_center = findViewById(R.id.card_view_5);

        mCardsTable[0] = findViewById(R.id.card_table_1);
        mCardsTable[1] = findViewById(R.id.card_table_2);
        mCardsTable[2] = findViewById(R.id.card_table_3);
        mCardsTable[3] = findViewById(R.id.card_table_4);
        mCardsTable[4] = findViewById(R.id.card_table_6);
        mCardsTable[5] = findViewById(R.id.card_table_7);
        mCardsTable[6] = findViewById(R.id.card_table_8);
        mCardsTable[7] = findViewById(R.id.card_table_9);

        mCardsTable[0].imageView = findViewById(R.id.card_view_1);
        mCardsTable[1].imageView = findViewById(R.id.card_view_2);
        mCardsTable[2].imageView = findViewById(R.id.card_view_3);
        mCardsTable[3].imageView = findViewById(R.id.card_view_4);
        mCardsTable[4].imageView = findViewById(R.id.card_view_6);
        mCardsTable[5].imageView = findViewById(R.id.card_view_7);
        mCardsTable[6].imageView = findViewById(R.id.card_view_8);
        mCardsTable[7].imageView = findViewById(R.id.card_view_9);

        mCardsTable[0].name_text = findViewById(R.id.card_name_1);
        mCardsTable[1].name_text = findViewById(R.id.card_name_2);
        mCardsTable[2].name_text = findViewById(R.id.card_name_3);
        mCardsTable[3].name_text = findViewById(R.id.card_name_4);
        mCardsTable[4].name_text = findViewById(R.id.card_name_6);
        mCardsTable[5].name_text = findViewById(R.id.card_name_7);
        mCardsTable[6].name_text = findViewById(R.id.card_name_8);
        mCardsTable[7].name_text = findViewById(R.id.card_name_9);

        mCardsTable[0].value_one_text = findViewById(R.id.card_hp_1);
        mCardsTable[1].value_one_text = findViewById(R.id.card_hp_2);
        mCardsTable[2].value_one_text = findViewById(R.id.card_hp_3);
        mCardsTable[3].value_one_text = findViewById(R.id.card_hp_4);
        mCardsTable[4].value_one_text = findViewById(R.id.card_hp_6);
        mCardsTable[5].value_one_text = findViewById(R.id.card_hp_7);
        mCardsTable[6].value_one_text = findViewById(R.id.card_hp_8);
        mCardsTable[7].value_one_text = findViewById(R.id.card_hp_9);

        mCardsTable[0].value_two_text = findViewById(R.id.card_damage_1);
        mCardsTable[1].value_two_text = findViewById(R.id.card_damage_2);
        mCardsTable[2].value_two_text = findViewById(R.id.card_damage_3);
        mCardsTable[3].value_two_text = findViewById(R.id.card_damage_4);
        mCardsTable[4].value_two_text = findViewById(R.id.card_damage_6);
        mCardsTable[5].value_two_text = findViewById(R.id.card_damage_7);
        mCardsTable[6].value_two_text = findViewById(R.id.card_damage_8);
        mCardsTable[7].value_two_text = findViewById(R.id.card_damage_9);

        loot[0] = findViewById(R.id.card_loot_0);
        loot[1] = findViewById(R.id.card_loot_1);
        loot[2] = findViewById(R.id.card_loot_2);

        loot[0].setOnDragListener(mLootOnDropListener);
        loot[1].setOnDragListener(mLootOnDropListener);
        loot[2].setOnDragListener(mLootOnDropListener);

        loot[0].slot_type = Slot_Type.LOOT;
        loot[1].slot_type = Slot_Type.LOOT;
        loot[2].slot_type = Slot_Type.LOOT;

        loot[0].imageView = findViewById(R.id.card_loot_0_view);
        loot[1].imageView = findViewById(R.id.card_loot_1_view);
        loot[2].imageView = findViewById(R.id.card_loot_2_view);

        loot[0].name_text = findViewById(R.id.card_loot_0_name);
        loot[1].name_text = findViewById(R.id.card_loot_1_name);
        loot[2].name_text = findViewById(R.id.card_loot_2_name);

        loot[0].value_one_text = findViewById(R.id.card_loot_0_value);
        loot[1].value_one_text = findViewById(R.id.card_loot_1_value);
        loot[2].value_one_text = findViewById(R.id.card_loot_2_value);

        trade_item[0] = findViewById(R.id.card_trade_0);
        trade_item[1] = findViewById(R.id.card_trade_1);
        trade_item[2] = findViewById(R.id.card_trade_2);

        trade_item[0].imageView = findViewById(R.id.card_trade_0_view);
        trade_item[1].imageView = findViewById(R.id.card_trade_1_view);
        trade_item[2].imageView = findViewById(R.id.card_trade_2_view);

        trade_item[0].name_text = findViewById(R.id.card_trade_0_name);
        trade_item[1].name_text = findViewById(R.id.card_trade_1_name);
        trade_item[2].name_text = findViewById(R.id.card_trade_2_name);

        trade_item[0].value_one_text = findViewById(R.id.card_trade_0_value);
        trade_item[1].value_one_text = findViewById(R.id.card_trade_1_value);
        trade_item[2].value_one_text = findViewById(R.id.card_trade_2_value);

        trade_item[0].slot_id = 0;
        trade_item[1].slot_id = 1;
        trade_item[2].slot_id = 2;

        trade_item[0].setOnClickListener(card_trade_click);
        trade_item[1].setOnClickListener(card_trade_click);
        trade_item[2].setOnClickListener(card_trade_click);

        trade_cost[0] = findViewById(R.id.cost_0_text);
        trade_cost[1] = findViewById(R.id.cost_1_text);
        trade_cost[2] = findViewById(R.id.cost_2_text);
        trade_cost_image[0] = findViewById(R.id.cost_0_view);
        trade_cost_image[1] = findViewById(R.id.cost_1_view);
        trade_cost_image[2] = findViewById(R.id.cost_2_view);

        hand_one = findViewById(R.id.card_hand_left);
        hand_two = findViewById(R.id.card_hand_right);
        hand_one.slot_type = Slot_Type.HAND;
        hand_two.slot_type = Slot_Type.HAND;
        hand_one.imageView = findViewById(R.id.card_hand_leftview);
        hand_two.imageView = findViewById(R.id.card_hand_right_view);
        hand_one.name_text = findViewById(R.id.card_hand_left_name);
        hand_two.name_text = findViewById(R.id.card_hand_right_name);
        hand_one.value_one_text = findViewById(R.id.card_hand_left_value);
        hand_two.value_one_text = findViewById(R.id.card_hand_right_value);
        hand_one.setOnDragListener(mHandOnDropListener);
        hand_two.setOnDragListener(mHandOnDropListener);
        hand_one.durability_text = findViewById(R.id.hand_one_durability_text);
        hand_two.durability_text = findViewById(R.id.hand_two_durability_text);
        hand_one.durability_image = findViewById(R.id.hand_one_durability_image);
        hand_two.durability_image = findViewById(R.id.hand_two_durability_image);

        inventory[0] = findViewById(R.id.card_inventory_0);
        inventory[1] = findViewById(R.id.card_inventory_1);
        inventory[2] = findViewById(R.id.card_inventory_2);
        inventory[3] = findViewById(R.id.card_inventory_3);

        inventory[0].slot_type = Slot_Type.INVENTORY;
        inventory[1].slot_type = Slot_Type.INVENTORY;
        inventory[2].slot_type = Slot_Type.INVENTORY;
        inventory[3].slot_type = Slot_Type.INVENTORY;

        inventory[0].imageView = findViewById(R.id.card_inventory_0_view);
        inventory[1].imageView = findViewById(R.id.card_inventory_1_view);
        inventory[2].imageView = findViewById(R.id.card_inventory_2_view);
        inventory[3].imageView = findViewById(R.id.card_inventory_3_view);

        inventory[0].name_text = findViewById(R.id.card_inventory_0_name);
        inventory[1].name_text = findViewById(R.id.card_inventory_1_name);
        inventory[2].name_text = findViewById(R.id.card_inventory_2_name);
        inventory[3].name_text = findViewById(R.id.card_inventory_3_name);

        inventory[0].value_one_text = findViewById(R.id.card_inventory_0_value);
        inventory[1].value_one_text = findViewById(R.id.card_inventory_1_value);
        inventory[2].value_one_text = findViewById(R.id.card_inventory_2_value);
        inventory[3].value_one_text = findViewById(R.id.card_inventory_3_value);

        mCardsTable[0].TEST_GearScoreText = findViewById(R.id.card1GearScore);
        mCardsTable[1].TEST_GearScoreText = findViewById(R.id.card2GearScore);
        mCardsTable[2].TEST_GearScoreText = findViewById(R.id.card3GearScore);
        mCardsTable[3].TEST_GearScoreText = findViewById(R.id.card4GearScore);
        mCardsTable[4].TEST_GearScoreText = findViewById(R.id.card6GearScore);
        mCardsTable[5].TEST_GearScoreText = findViewById(R.id.card7GearScore);
        mCardsTable[6].TEST_GearScoreText = findViewById(R.id.card8GearScore);
        mCardsTable[7].TEST_GearScoreText = findViewById(R.id.card9GearScore);

        loot[0].TEST_GearScoreText = findViewById(R.id.loot0GearScore);
        loot[1].TEST_GearScoreText = findViewById(R.id.loot1GearScore);
        loot[2].TEST_GearScoreText = findViewById(R.id.loot2GearScore);

        trade_item[0].TEST_GearScoreText = findViewById(R.id.cardTrade0GearScore);
        trade_item[1].TEST_GearScoreText = findViewById(R.id.cardTrade1GearScore);
        trade_item[2].TEST_GearScoreText = findViewById(R.id.cardTrade2GearScore);

        hand_one.TEST_GearScoreText = findViewById(R.id.cardHandLeftGearScore);
        hand_two.TEST_GearScoreText = findViewById(R.id.cardHandRightGearScore);

        inventory[0].TEST_GearScoreText = findViewById(R.id.cardInventory0GearScore);
        inventory[1].TEST_GearScoreText = findViewById(R.id.cardInventory1GearScore);
        inventory[2].TEST_GearScoreText = findViewById(R.id.cardInventory2GearScore);
        inventory[3].TEST_GearScoreText = findViewById(R.id.cardInventory3GearScore);

        loot[0].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.loot0GearScoreMob);
        loot[1].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.loot1GearScoreMob);
        loot[2].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.loot2GearScoreMob);

        trade_item[0].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardTrade0GearScoreMob);
        trade_item[1].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardTrade1GearScoreMob);
        trade_item[2].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardTrade2GearScoreMob);

        hand_one.TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardHandLeftGearScoreMob);
        hand_two.TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardHandRightGearScoreMob);

        inventory[0].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardInventory0GearScoreMob);
        inventory[1].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardInventory1GearScoreMob);
        inventory[2].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardInventory2GearScoreMob);
        inventory[3].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardInventory3GearScoreMob);

        Game_Load();

    }

    private void Game_Load() {
        SQLiteDatabase data_base = db_open_helper.getReadableDatabase();

        String[] column_name = {
                DB_Open_Helper.sChanceVendor,
                DB_Open_Helper.sChanceHalt,
                DB_Open_Helper.sChanceWeaponOrShield,
                DB_Open_Helper.sChanceFood,
                DB_Open_Helper.sChanceSpell,
                DB_Open_Helper.sChanceChest
        };

        Cursor cursor = data_base.query(
                DB_Open_Helper.sTableTest,
                column_name,
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        mChanceVendor = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.sChanceVendor)
        );
        mChanceHalt = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.sChanceHalt)
        );
        mChanceWeaponOrShield = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.sChanceWeaponOrShield)
        );
        mChanceFood = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.sChanceFood)
        );
        mChanceSpell = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.sChanceSpell)
        );
        mChanceChest = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.sChanceSpell)
        );
        cursor.close();

        for (byte i = 0; i < 8; i++) {
            mCardsTable[i].setIdInArray(i);
            mCardsTable[i].name_text.setVisibility(View.INVISIBLE);
            mCardsTable[i].value_two_text.setVisibility(View.INVISIBLE);
            mCardsTable[i].value_one_text.setVisibility(View.INVISIBLE);
            mCardsTable[i].imageView.setImageResource(card_back);
            mCardsTable[i].Set_Id_Drawable(card_back);
            mCardsTable[i].imageView.setVisibility(View.INVISIBLE);
        }
        card_center.setVisibility(View.INVISIBLE);

        loot[1].setVisibility(View.GONE);
        loot[2].setVisibility(View.GONE);

        inventory_item_count = 1;
        inventory[0].Set_Value_One(1);
        inventory[0].id_drawable = R.drawable.yablachko;
        inventory[0].name_text.setText("Яблоко");
        inventory[0].value_one_text.setText(" 1");
        Picasso.with(getBaseContext()).load(R.drawable.yablachko).into(inventory[0].imageView);
/*
        inventory[0].imageView.setImageResource(R.drawable.yablachko);
*/
        inventory[0].Set_Type(Inventory_Type.FOOD);
        inventory[0].setOnClickListener(null);
        inventory[0].cost = 1;
        inventory[0].setGearScore(1);
        ChangeGearScore(1);

        hand_one.hand_drawable = R.drawable.kulak_levo;
        hand_two.hand_drawable = R.drawable.kulak_pravo;
        hand_one.setOnClickListener(null);
        hand_two.setOnClickListener(null);
        mGearScoreWeaponOrShieldInInventory.add(0);
        mGearScoreWeaponOrShieldInInventory.add(0);
        ChangeGearScore(getChangeGearScoreAfterReplace(0,0));
        Set_Hand(hand_one);
        ChangeGearScore(getChangeGearScoreAfterReplace(0,0));
        Set_Hand(hand_two);

        inventory[0].slot_id = 0;
        for (byte i = 1; i < inventory_item_max_count; i++) {
            inventory[i].setVisibility(View.GONE);
            inventory[i].slot_id = i;
        }

        hp = hp_max;

        hp_text.setText(String.valueOf(hp));
        hp_bar_drawable.setLevel(10000 * hp / hp_max);

        is_animate = true;
        Log.d("is_animate", String.valueOf(is_animate));

        mCardsTable[1].setOnClickListener(setTargetListener);
        mCardsTable[3].setOnClickListener(setTargetListener);
        mCardsTable[4].setOnClickListener(setTargetListener);
        mCardsTable[6].setOnClickListener(setTargetListener);

        money_text.setText(String.valueOf(money_bank));

        shadow.setVisibility(View.VISIBLE);
        shadow.setAlpha(0f);
    }

    private void Set_Hand(Card_Hand hand) {
        hand.setGearScore(0);
        hand.TEST_MOB_GEARSCORE_TEXT.setText(0+"");
        hand.Set_Value_One(1);
        hand.name_text.setText("Кулак");
        hand.id_drawable = hand.hand_drawable;
        hand.Set_Type(Inventory_Type.WEAPON);
        hand.value_one_text.setText(" 1");
        Picasso.with(getBaseContext()).load(hand.hand_drawable).into(hand.imageView);
/*
        hand_one.imageView.setImageResource(R.drawable.fist);
*/
        hand.durability_text.setVisibility(View.INVISIBLE);
        hand.durability_image.setVisibility(View.INVISIBLE);
    }

    private void Game_Reset() {

        money_bank += money;
        money = 0;

        Game_Load();

        card_6_animation_reset.start();

        button_start.setVisibility(View.VISIBLE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

/*
        data_base = db_open_helper.getWritableDatabase();
        data_base.close();

        Set_Text_Size();

        Set_Animators();
*/
    }

    private void Set_Text_Size() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int card_height = mCardsTable[0].imageView.getHeight();
        int card_width = mCardsTable[0].imageView.getWidth();

        money_text_size = displayMetrics.heightPixels * money_text_size_constant;
        money_text.setTextSize(COMPLEX_UNIT_PX, money_text_size);

        card_name_text_size = (float) (Math.sqrt(Math.pow(card_width, 2.0) +
                Math.pow(card_height, 2.0)) * card_name_text_size_constant);
        card_hp_and_damage_text_size = (float) (Math.sqrt(Math.pow(card_width, 2.0) +
                Math.pow(card_height, 2.0)) * card_hp_and_damage_text_size_constant);
        for (int i = 0; i < 8; i++) {
            mCardsTable[i].name_text.setTextSize(COMPLEX_UNIT_PX, card_name_text_size);
            mCardsTable[i].value_one_text.setTextSize(COMPLEX_UNIT_PX, card_hp_and_damage_text_size);
            mCardsTable[i].value_two_text.setTextSize(COMPLEX_UNIT_PX, card_hp_and_damage_text_size);
        }

        float card_inventory_value_one_text_size = (float) (Math.sqrt(Math.pow(inventory[0].imageView.getWidth(), 2.0) +
                Math.pow(inventory[0].imageView.getHeight(), 2.0)) * card_hp_and_damage_text_size_constant);
        float card_inventory_name_text_size = (float) (Math.sqrt(Math.pow(inventory[0].imageView.getWidth(), 2.0) +
                Math.pow(inventory[0].imageView.getHeight(), 2.0)) * card_name_text_size_constant);
        for (int i = 0; i < inventory_item_max_count; i++) {
            inventory[i].name_text.setTextSize(COMPLEX_UNIT_PX, card_inventory_name_text_size);
            inventory[i].value_one_text.setTextSize(COMPLEX_UNIT_PX, card_inventory_value_one_text_size);
        }

        float card_loot_value_one_text_size = (float) (Math.sqrt(Math.pow(loot[0].imageView.getWidth(), 2.0) +
                Math.pow(loot[0].imageView.getHeight(), 2.0)) * card_hp_and_damage_text_size_constant);
        float card_loot_name_text_size = (float) (Math.sqrt(Math.pow(loot[0].imageView.getWidth(), 2.0) +
                Math.pow(loot[0].imageView.getHeight(), 2.0)) * card_name_text_size_constant);
        for (int i = 0; i < loot_max_count; i++) {
            loot[i].name_text.setTextSize(COMPLEX_UNIT_PX, card_loot_name_text_size);
            loot[i].value_one_text.setTextSize(COMPLEX_UNIT_PX, card_loot_value_one_text_size);
        }
        Log.d(""+card_loot_value_one_text_size, ""+card_loot_name_text_size);
        loot[0].setVisibility(View.GONE);

        float card_hand_value_one_text_size = (float) (Math.sqrt(Math.pow(hand_one.imageView.getWidth(), 2.0) +
                Math.pow(hand_one.imageView.getHeight(), 2.0)) * card_hp_and_damage_text_size_constant);
        float card_hand_name_text_size = (float) (Math.sqrt(Math.pow(hand_one.imageView.getWidth(), 2.0) +
                Math.pow(hand_one.imageView.getHeight(), 2.0)) * card_name_text_size_constant);
            hand_one.name_text.setTextSize(COMPLEX_UNIT_PX, card_hand_name_text_size);
            hand_one.value_one_text.setTextSize(COMPLEX_UNIT_PX, card_hand_value_one_text_size);
        hand_two.name_text.setTextSize(COMPLEX_UNIT_PX, card_hand_name_text_size);
        hand_two.value_one_text.setTextSize(COMPLEX_UNIT_PX, card_hand_value_one_text_size);


        hp_text_size = (float) (Math.sqrt(Math.pow(hp_view.getWidth(), 2.0) +
                Math.pow(hp_view.getHeight(), 2.0)) * hp_text_size_constant);
        hp_text.setTextSize(COMPLEX_UNIT_PX, hp_text_size);
    }

    int inventory_animation_delta;
    int hand_animation_delta;
    float loot_animation_delta;
    private void Set_Animators() {

        float cardTableIncreaseAnimationValue = 3f;
        int duration = 500;
        int card_animation_duration = 500;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int card_height = mCardsTable[0].imageView.getHeight();
        int card_width = mCardsTable[0].imageView.getWidth();

        int[] card_5_coordinates = new int[2];
        int[] card_8_coordinates = new int[2];
        int[] card_4_coordinates = new int[2];
        int[] card_6_coordinates = new int[2];

        int[] card_inventory_coordinates = new int[2];
        int[] card_hand_coordinates = new int[2];
        int[] card_loot_coordinates = new int[2];
        int[] guideline_inventory_top_coordinates = new int[2];

        inventory[0].imageView.getLocationOnScreen(card_inventory_coordinates);
        hand_one.imageView.getLocationOnScreen(card_hand_coordinates);
        loot[0].imageView.getLocationOnScreen(card_loot_coordinates);
        findViewById(R.id.guideline_inventory_top).getLocationOnScreen(guideline_inventory_top_coordinates);

        inventory_animation_delta = card_inventory_coordinates[1] + inventory[0].imageView.getHeight() -
                displayMetrics.heightPixels;
        hand_animation_delta = card_hand_coordinates[1] + hand_one.imageView.getHeight() -
                guideline_inventory_top_coordinates[1];
        loot_animation_delta = displayMetrics.heightPixels * 0.05f;

        card_center.getLocationOnScreen(card_5_coordinates);
        mCardsTable[6].imageView.getLocationOnScreen(card_8_coordinates);
        mCardsTable[3].imageView.getLocationOnScreen(card_4_coordinates);
        mCardsTable[4].imageView.getLocationOnScreen(card_6_coordinates);

        ConstraintLayout table_layout = findViewById(R.id.table);
        int card_distance_between_Y = card_5_coordinates[1] - card_8_coordinates[1];
        int card_distance_between_X = card_5_coordinates[0] - card_4_coordinates[0];
        int card_coordinates_animation_top_start = -card_8_coordinates[1] - card_height;
        int card_coordinates_animation_bottom_start = table_layout.getHeight() + card_height - card_8_coordinates[1];
        int card_coordinates_animation_right_start = table_layout.getWidth() + card_width - card_4_coordinates[0];
        int card_coordinates_animation_left_start = -card_4_coordinates[0] - card_width;

        //region General Animation
        AnimatorSet openCardTableRotateBack = new AnimatorSet();
        openCardTableRotateBack.setDuration(duration).playTogether(
                ObjectAnimator.ofFloat(mCardsTable[4], View.ROTATION_Y, 0f, 90f),
                ObjectAnimator.ofFloat(mCardsTable[3], View.ROTATION_Y, 0f, 90f),
                ObjectAnimator.ofFloat(mCardsTable[6], View.ROTATION_Y, 0f, 90f),
                ObjectAnimator.ofFloat(mCardsTable[1], View.ROTATION_Y, 0f, 90f)
        );
        AnimatorListenerAdapter openCardTableRotateBackListener = new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mCardsTable[1].Open();
                mCardsTable[3].Open();
                mCardsTable[4].Open();
                mCardsTable[6].Open();
            }
        };
        openCardTableRotateBack.addListener(openCardTableRotateBackListener);
        AnimatorSet openCardTableRotateFront = new AnimatorSet();
        openCardTableRotateFront.setDuration(duration).playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.ROTATION_Y,
                        -90f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.ROTATION_Y,
                        -90f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.ROTATION_Y,
                        -90f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.ROTATION_Y,
                        -90f,
                        0f
                )
        );
        openCardTable.playSequentially(
                openCardTableRotateBack,
                openCardTableRotateFront
        );
        AnimatorListenerAdapter openCardTableListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                spawn(mCardsTable[1]);
                spawn(mCardsTable[3]);
                spawn(mCardsTable[4]);
                spawn(mCardsTable[6]);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                is_animate = false;
                Log.d("is_animate", String.valueOf(is_animate));
            }
        };
        openCardTable.addListener(openCardTableListener);

        Animator shadowShow  = ObjectAnimator.ofFloat(
                shadow,
                View.ALPHA,
                0f,
                .5f
        );

        Animator shadowHide = ObjectAnimator.ofFloat(
                shadow,
                View.ALPHA,
                .5f,
                0f
        );

        AnimatorSet columnCenterCardTableReset = new AnimatorSet();
        columnCenterCardTableReset.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        card_center,
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.TRANSLATION_X,
                        0f,
                        0f
                )
        );

        AnimatorSet rowCenterCardTableReset = new AnimatorSet();
        columnCenterCardTableReset.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.TRANSLATION_Y,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        card_center,
                        View.TRANSLATION_Y,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.TRANSLATION_Y,
                        0f,
                        0f
                )
        );

        AnimatorListenerAdapter cardTableCloseListener = new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mCardTableTarget.Close(card_back);
            }
        };
        AnimatorListenerAdapter cardTableCloseBackAnimationListener = new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                loot();
            }
        };
        //endregion
        //region a
        card_6_animation_click_vendor.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(mCardsTable[4], View.TRANSLATION_X, 0f, card_4_coordinates[0]-card_6_coordinates[0]),
                ObjectAnimator.ofFloat(mCardsTable[4], View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                shadowShow
        );

        card_animation_right.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(mCardsTable[2], View.TRANSLATION_X, 0f, card_coordinates_animation_right_start),
                ObjectAnimator.ofFloat(mCardsTable[4], View.TRANSLATION_X, 0f, card_coordinates_animation_right_start),
                ObjectAnimator.ofFloat(mCardsTable[7], View.TRANSLATION_X, 0f, card_coordinates_animation_right_start),

                ObjectAnimator.ofFloat(mCardsTable[0], View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(mCardsTable[1], View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(mCardsTable[3], View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(mCardsTable[5], View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(mCardsTable[6], View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(card_center, View.TRANSLATION_X, 0f, card_distance_between_X)
        );

        card_animation_down.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(mCardsTable[0], View.TRANSLATION_Y, 0f, card_coordinates_animation_bottom_start),
                ObjectAnimator.ofFloat(mCardsTable[1], View.TRANSLATION_Y, 0f, card_coordinates_animation_bottom_start),
                ObjectAnimator.ofFloat(mCardsTable[2], View.TRANSLATION_Y, 0f, card_coordinates_animation_bottom_start),

                ObjectAnimator.ofFloat(mCardsTable[3], View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(mCardsTable[4], View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(mCardsTable[5], View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(mCardsTable[6], View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(mCardsTable[7], View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(card_center, View.TRANSLATION_Y, 0f, card_distance_between_Y)
        );

        card_animation_up.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(mCardsTable[5], View.TRANSLATION_Y, 0f, card_coordinates_animation_top_start),
                ObjectAnimator.ofFloat(mCardsTable[6], View.TRANSLATION_Y, 0f, card_coordinates_animation_top_start),
                ObjectAnimator.ofFloat(mCardsTable[7], View.TRANSLATION_Y, 0f, card_coordinates_animation_top_start),

                ObjectAnimator.ofFloat(mCardsTable[1], View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(mCardsTable[2], View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(mCardsTable[3], View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(mCardsTable[4], View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(mCardsTable[0], View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(card_center, View.TRANSLATION_Y, 0f, -card_distance_between_Y)
        );

        card_animation_get_top.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(mCardsTable[5], View.TRANSLATION_Y, card_coordinates_animation_top_start, 0f),
                ObjectAnimator.ofFloat(mCardsTable[6], View.TRANSLATION_Y, card_coordinates_animation_top_start, 0f),
                ObjectAnimator.ofFloat(mCardsTable[7], View.TRANSLATION_Y, card_coordinates_animation_top_start, 0f)
        );

        card_animation_get_bottom.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(mCardsTable[0], View.TRANSLATION_Y, card_coordinates_animation_bottom_start, 0f),
                ObjectAnimator.ofFloat(mCardsTable[1], View.TRANSLATION_Y, card_coordinates_animation_bottom_start, 0f),
                ObjectAnimator.ofFloat(mCardsTable[2], View.TRANSLATION_Y, card_coordinates_animation_bottom_start, 0f)
        );

        card_animation_get_left.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(mCardsTable[0], View.TRANSLATION_X, card_coordinates_animation_left_start, 0f),
                ObjectAnimator.ofFloat(mCardsTable[3], View.TRANSLATION_X, card_coordinates_animation_left_start, 0f),
                ObjectAnimator.ofFloat(mCardsTable[5], View.TRANSLATION_X, card_coordinates_animation_left_start, 0f)
        );

        Set_Card_Animators_Reset();
//endregion
        //region Animation Right Card Table
        AnimatorSet rightCardTableIncreaseAnimation = new AnimatorSet();
        rightCardTableIncreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.SCALE_X,
                        1f,
                        cardTableIncreaseAnimationValue
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.SCALE_Y,
                        1f,
                        cardTableIncreaseAnimationValue
                )
        );
        Animator rightCardTableMoveAnimation = ObjectAnimator.ofFloat(
                mCardsTable[4],
                View.TRANSLATION_X,
                0f,
                -card_distance_between_X
        );
        mCardsTable[4].getTargetAnimation().setDuration(duration).playTogether(
                shadowShow,
                rightCardTableIncreaseAnimation,
                rightCardTableMoveAnimation
        );
        Animator rightCardTableCloseFrontAnimation = ObjectAnimator.ofFloat(
                mCardsTable[4],
                View.ROTATION_Y,
                0f,
                90f
        );
        rightCardTableCloseFrontAnimation.addListener(cardTableCloseListener);
        Animator rightCardTableCloseBackAnimation = ObjectAnimator.ofFloat(
                mCardsTable[4],
                View.ROTATION_Y,
                -90f,
                0f
        );
        rightCardTableCloseBackAnimation.addListener(cardTableCloseBackAnimationListener);
        mCardsTable[4].getCloseAnimation().playSequentially(
                rightCardTableCloseFrontAnimation,
                rightCardTableCloseBackAnimation
        );
        AnimatorSet rightCardTableDicreaseAnimation = new AnimatorSet();
        rightCardTableDicreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.SCALE_X,
                        cardTableIncreaseAnimationValue,
                        1f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.SCALE_Y,
                        cardTableIncreaseAnimationValue,
                        1f
                )
        );
        Animator rightCardTableMoveBackAnimation = ObjectAnimator.ofFloat(
                mCardsTable[4],
                View.TRANSLATION_X,
                -card_distance_between_X,
                0f
        );
        AnimatorSet rightCardTableTargetResetAnimation = new AnimatorSet();
        rightCardTableTargetResetAnimation.setDuration(duration).playTogether(
                shadowHide,
                rightCardTableDicreaseAnimation,
                rightCardTableMoveBackAnimation
        );

        AnimatorSet cardTableMoveToLeft = new AnimatorSet();
        cardTableMoveToLeft.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[0],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[5],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[2],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[7],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        card_center,
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                )
        );
        AnimatorListenerAdapter cardTableMoveToLeftListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationEnd(Animator animation) {
                    mCardsTable[0].Copy(mCardsTable[1]);
                    mCardsTable[3].Close(cardCenterBack);
                    mCardsTable[5].Copy(mCardsTable[6]);
            }
        };
        cardTableMoveToLeft.addListener(cardTableMoveToLeftListener);

        AnimatorSet columnLeftCardTableResetAnimation = new AnimatorSet();
        columnLeftCardTableResetAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[0],
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[5],
                        View.TRANSLATION_X,
                        0f,
                        0f
                )
        );
        AnimatorListenerAdapter columnLeftCardTableResetAnimationListener = new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mCardsTable[1].Copy(mCardsTable[2]);
                mCardsTable[6].Copy(mCardsTable[7]);
            }
        };
        columnLeftCardTableResetAnimation.addListener(columnLeftCardTableResetAnimationListener);

        AnimatorSet getColumnRightAnimation = new AnimatorSet();
        getColumnRightAnimation.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[2],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[7],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                )
        );
        AnimatorListenerAdapter getColumnRightAnimationListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                    mCardsTable[2].Close(card_back);
                    mCardsTable[4].Close(card_back);
                    mCardsTable[7].Close(card_back);
            }
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        };
        getColumnRightAnimation.addListener(getColumnRightAnimationListener);

        mCardsTable[4].getChangeAnimation().playSequentially(
                rightCardTableTargetResetAnimation,
                cardTableMoveToLeft,
                columnLeftCardTableResetAnimation,
                columnCenterCardTableReset,
                getColumnRightAnimation,
                openCardTable
        );
        //endregion
        //region Animation Bottom Card Table
        AnimatorSet mBottomCardTableIncreaseAnimation = new AnimatorSet();
        mBottomCardTableIncreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.SCALE_X,
                        1f,
                        cardTableIncreaseAnimationValue
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.SCALE_Y,
                        1f,
                        cardTableIncreaseAnimationValue
                )
        );
        AnimatorSet mBottomCardTableDicreaseAnimation = new AnimatorSet();
        mBottomCardTableDicreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.SCALE_X,
                        cardTableIncreaseAnimationValue,
                        1f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.SCALE_Y,
                        cardTableIncreaseAnimationValue,
                        1f
                )
        );
        Animator card_2_animation_movement = ObjectAnimator.ofFloat(
                mCardsTable[1],
                View.TRANSLATION_Y,
                0f,
                -card_distance_between_Y
        );
        mCardsTable[1].getTargetAnimation().setDuration(duration).playTogether(
                shadowShow,
                mBottomCardTableIncreaseAnimation,
                card_2_animation_movement
        );

        Animator bottomCardTableCloseFrontAnimation = ObjectAnimator.ofFloat(
                mCardsTable[1],
                View.ROTATION_Y,
                0f,
                90f
        );
        bottomCardTableCloseFrontAnimation.addListener(cardTableCloseListener);
        Animator bottomCardTableCloseBackAnimation = ObjectAnimator.ofFloat(
                mCardsTable[1],
                View.ROTATION_Y,
                -90f,
                0f
        );
        bottomCardTableCloseBackAnimation.addListener(cardTableCloseBackAnimationListener);
        mCardsTable[1].getCloseAnimation().playSequentially(
                bottomCardTableCloseFrontAnimation,
                bottomCardTableCloseBackAnimation
        );

        AnimatorSet bottomCardTableDicreaseAnimation = new AnimatorSet();
        bottomCardTableDicreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.SCALE_X,
                        cardTableIncreaseAnimationValue,
                        1f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.SCALE_Y,
                        cardTableIncreaseAnimationValue,
                        1f
                )
        );
        Animator bottomCardTableMoveBackAnimation = ObjectAnimator.ofFloat(
                mCardsTable[1],
                View.TRANSLATION_Y,
                -card_distance_between_Y,
                0f
        );

        AnimatorSet bottomCardTableTargetResetAnimation = new AnimatorSet();
        bottomCardTableTargetResetAnimation.setDuration(duration).playTogether(
                shadowHide,
                bottomCardTableDicreaseAnimation,
                bottomCardTableMoveBackAnimation
        );

        AnimatorSet cardTableMoveToUp = new AnimatorSet();
        cardTableMoveToUp.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[0],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[5],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[2],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[7],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        card_center,
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                )
        );
        AnimatorListenerAdapter cardTableMoveToUpListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationEnd(Animator animation) {
                mCardsTable[5].Copy(mCardsTable[3]);
                mCardsTable[6].Close(cardCenterBack);
                mCardsTable[7].Copy(mCardsTable[4]);
            }
        };
        cardTableMoveToUp.addListener(cardTableMoveToUpListener);

        AnimatorSet rowTopCardTableResetAnimation = new AnimatorSet();
        rowTopCardTableResetAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[0],
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[5],
                        View.TRANSLATION_X,
                        0f,
                        0f
                )
        );
        AnimatorListenerAdapter rowTopCardTableResetAnimationListener = new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mCardsTable[3].Copy(mCardsTable[0]);
                mCardsTable[4].Copy(mCardsTable[2]);
            }
        };
        rowTopCardTableResetAnimation.addListener(rowTopCardTableResetAnimationListener);

        AnimatorSet getRowBottomAnimation = new AnimatorSet();
        getRowBottomAnimation.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[2],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[7],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                )
        );
        AnimatorListenerAdapter getRowBottomAnimationListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mCardsTable[0].Close(card_back);
                mCardsTable[1].Close(card_back);
                mCardsTable[2].Close(card_back);
            }
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        };
        getRowBottomAnimation.addListener(getRowBottomAnimationListener);

        mCardsTable[1].getChangeAnimation().playSequentially(
                bottomCardTableTargetResetAnimation,
                cardTableMoveToUp,
                rowTopCardTableResetAnimation,
                columnCenterCardTableReset,
                getRowBottomAnimation,
                openCardTable
        );
        //endregion
        //region Animation Left Card Table
        AnimatorSet mLeftCardTableIncreaseAnimation = new AnimatorSet();
        mLeftCardTableIncreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.SCALE_X,
                        1f,
                        cardTableIncreaseAnimationValue
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.SCALE_Y,
                        1f,
                        cardTableIncreaseAnimationValue
                )
        );
        AnimatorSet mLeftCardTableDicreaseAnimation = new AnimatorSet();
        mLeftCardTableDicreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.SCALE_X,
                        cardTableIncreaseAnimationValue,
                        1f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.SCALE_Y,
                        cardTableIncreaseAnimationValue,
                        1f
                )
        );
        Animator card_4_animation_movement = ObjectAnimator.ofFloat(
                mCardsTable[3],
                View.TRANSLATION_X,
                0f,
                card_distance_between_X
        );
        mCardsTable[3].getTargetAnimation().setDuration(duration).playTogether(
                shadowShow,
                mLeftCardTableIncreaseAnimation,
                card_4_animation_movement
        );

        Animator leftCardTableCloseFrontAnimation = ObjectAnimator.ofFloat(
                mCardsTable[3],
                View.ROTATION_Y,
                0f,
                90f
        );
        leftCardTableCloseFrontAnimation.addListener(cardTableCloseListener);
        Animator leftCardTableCloseBackAnimation = ObjectAnimator.ofFloat(
                mCardsTable[3],
                View.ROTATION_Y,
                -90f,
                0f
        );
        leftCardTableCloseBackAnimation.addListener(cardTableCloseBackAnimationListener);
        mCardsTable[3].getCloseAnimation().playSequentially(
                leftCardTableCloseFrontAnimation,
                leftCardTableCloseBackAnimation
        );

        AnimatorSet leftCardTableDicreaseAnimation = new AnimatorSet();
        leftCardTableDicreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.SCALE_X,
                        cardTableIncreaseAnimationValue,
                        1f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.SCALE_Y,
                        cardTableIncreaseAnimationValue,
                        1f
                )
        );
        Animator leftCardTableMoveBackAnimation = ObjectAnimator.ofFloat(
                mCardsTable[3],
                View.TRANSLATION_X,
                card_distance_between_X,
                0f
        );

        AnimatorSet leftCardTableTargetResetAnimation = new AnimatorSet();
        leftCardTableTargetResetAnimation.setDuration(duration).playTogether(
                shadowHide,
                leftCardTableDicreaseAnimation,
                leftCardTableMoveBackAnimation
        );

        AnimatorSet cardTableMoveToRight = new AnimatorSet();
        cardTableMoveToRight.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[0],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[5],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[2],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[7],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        card_center,
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                )
        );
        AnimatorListenerAdapter cardTableMoveToRightListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationEnd(Animator animation) {
                mCardsTable[2].Copy(mCardsTable[1]);
                mCardsTable[4].Close(cardCenterBack);
                mCardsTable[7].Copy(mCardsTable[6]);
            }
        };
        cardTableMoveToRight.addListener(cardTableMoveToRightListener);

        AnimatorSet columnRingtCardTableResetAnimation = new AnimatorSet();
        columnRingtCardTableResetAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[0],
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[5],
                        View.TRANSLATION_X,
                        0f,
                        0f
                )
        );
        AnimatorListenerAdapter columnRingtCardTableResetAnimationListener = new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mCardsTable[1].Copy(mCardsTable[0]);
                mCardsTable[6].Copy(mCardsTable[5]);
            }
        };
        columnRingtCardTableResetAnimation.addListener(columnRingtCardTableResetAnimationListener);

        AnimatorSet getColumnLeftAnimation = new AnimatorSet();
        getColumnLeftAnimation.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[2],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[7],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                )
        );
        AnimatorListenerAdapter getColumnLeftAnimationListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mCardsTable[0].Close(card_back);
                mCardsTable[3].Close(card_back);
                mCardsTable[5].Close(card_back);
            }
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        };
        getColumnLeftAnimation.addListener(getColumnLeftAnimationListener);

        mCardsTable[3].getChangeAnimation().playSequentially(
                leftCardTableTargetResetAnimation,
                cardTableMoveToRight,
                columnRingtCardTableResetAnimation,
                columnCenterCardTableReset,
                getColumnLeftAnimation,
                openCardTable
        );
        //endregion
        //region Animation Top Card Table
        AnimatorSet mTopCardTableIncreaseAnimation = new AnimatorSet();
        mTopCardTableIncreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.SCALE_X,
                        1f,
                        cardTableIncreaseAnimationValue
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.SCALE_Y,
                        1f,
                        cardTableIncreaseAnimationValue
                )
        );
        AnimatorSet mTopCardTableDicreaseAnimation = new AnimatorSet();
        mTopCardTableDicreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.SCALE_X,
                        cardTableIncreaseAnimationValue,
                        1f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.SCALE_Y,
                        cardTableIncreaseAnimationValue,
                        1f
                )
        );
        Animator card_8_animation_movement = ObjectAnimator.ofFloat(
                mCardsTable[6],
                View.TRANSLATION_Y,
                0f,
                card_distance_between_Y
        );
        mCardsTable[6].getTargetAnimation().setDuration(duration).playTogether(
                shadowShow,
                mTopCardTableIncreaseAnimation,
                card_8_animation_movement
        );

        Animator topCardTableCloseFrontAnimation = ObjectAnimator.ofFloat(
                mCardsTable[6],
                View.ROTATION_Y,
                0f,
                90f
        );
        topCardTableCloseFrontAnimation.addListener(cardTableCloseListener);
        Animator topCardTableCloseBackAnimation = ObjectAnimator.ofFloat(
                mCardsTable[6],
                View.ROTATION_Y,
                -90f,
                0f
        );
        topCardTableCloseBackAnimation.addListener(cardTableCloseBackAnimationListener);
        mCardsTable[6].getCloseAnimation().playSequentially(
                topCardTableCloseFrontAnimation,
                topCardTableCloseBackAnimation
        );

        AnimatorSet topCardTableDicreaseAnimation = new AnimatorSet();
        topCardTableDicreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.SCALE_X,
                        cardTableIncreaseAnimationValue,
                        1f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.SCALE_Y,
                        cardTableIncreaseAnimationValue,
                        1f
                )
        );
        Animator topCardTableMoveBackAnimation = ObjectAnimator.ofFloat(
                mCardsTable[6],
                View.TRANSLATION_Y,
                card_distance_between_Y,
                0f
        );

        AnimatorSet topCardTableTargetResetAnimation = new AnimatorSet();
        topCardTableTargetResetAnimation.setDuration(duration).playTogether(
                shadowHide,
                topCardTableDicreaseAnimation,
                topCardTableMoveBackAnimation
        );

        AnimatorSet cardTableMoveToDown = new AnimatorSet();
        cardTableMoveToDown.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[0],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[5],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[2],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[7],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        card_center,
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                )
        );
        AnimatorListenerAdapter cardTableMoveToDownListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationEnd(Animator animation) {
                mCardsTable[0].Copy(mCardsTable[3]);
                mCardsTable[1].Close(cardCenterBack);
                mCardsTable[2].Copy(mCardsTable[4]);
            }
        };
        cardTableMoveToDown.addListener(cardTableMoveToDownListener);

        AnimatorSet rowBottomCardTableResetAnimation = new AnimatorSet();
        rowBottomCardTableResetAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[0],
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[5],
                        View.TRANSLATION_X,
                        0f,
                        0f
                )
        );
        AnimatorListenerAdapter rowBottomCardTableResetAnimationListener = new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mCardsTable[3].Copy(mCardsTable[5]);
                mCardsTable[4].Copy(mCardsTable[7]);
            }
        };
        rowBottomCardTableResetAnimation.addListener(rowBottomCardTableResetAnimationListener);

        AnimatorSet getRowTopAnimation = new AnimatorSet();
        getRowTopAnimation.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[2],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[7],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                )
        );
        AnimatorListenerAdapter getRowTopAnimationListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mCardsTable[5].Close(card_back);
                mCardsTable[6].Close(card_back);
                mCardsTable[7].Close(card_back);
            }
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        };
        getRowTopAnimation.addListener(getRowTopAnimationListener);

        mCardsTable[6].getChangeAnimation().playSequentially(
                topCardTableTargetResetAnimation,
                cardTableMoveToDown,
                rowBottomCardTableResetAnimation,
                columnCenterCardTableReset,
                getRowTopAnimation,
                openCardTable
        );
        //endregion
    }
    private void Set_Card_Animators_Reset() {
        card_reset_row_top.playTogether(
                ObjectAnimator.ofFloat(mCardsTable[5], View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(mCardsTable[6], View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(mCardsTable[7], View.TRANSLATION_Y, 0f, 0f)
        );

        card_reset_row_center.playTogether(
                ObjectAnimator.ofFloat(mCardsTable[3], View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(card_center, View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(mCardsTable[4], View.TRANSLATION_Y, 0f, 0f)
        );

        card_reset_row_bottom.playTogether(
                ObjectAnimator.ofFloat(mCardsTable[0], View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(mCardsTable[1], View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(mCardsTable[2], View.TRANSLATION_Y, 0f, 0f)
        );

        card_reset_column_right.playTogether(
                ObjectAnimator.ofFloat(mCardsTable[2], View.TRANSLATION_X, 0f, 0f),
                ObjectAnimator.ofFloat(mCardsTable[4], View.TRANSLATION_X, 0f, 0f),
                ObjectAnimator.ofFloat(mCardsTable[7], View.TRANSLATION_X, 0f, 0f)
        );

        card_6_animation_reset.playTogether(
                ObjectAnimator.ofFloat(mCardsTable[4], View.TRANSLATION_X, 0f, 0f),
                ObjectAnimator.ofFloat(mCardsTable[4], View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(mCardsTable[4], View.SCALE_X, 1f, 1f),
                ObjectAnimator.ofFloat(mCardsTable[4], View.SCALE_Y, 1f, 1f)
        );
    }

    private void loot() {
        shadow.bringToFront();

        int[] typeLoot = new int[3];
        if (mCardTableTarget.getType()==CardTableType.MOB){
            loot_count = 0;
            if (random.nextInt(mChanceWeaponOrShield)==0){
                typeLoot[loot_count]=random.nextInt(2);
                loot_count++;
            }
            if (random.nextInt(mChanceFood)==0){
                typeLoot[loot_count]= Inventory_Type.FOOD;
                loot_count++;
            }
            if (random.nextInt(mChanceSpell)==0){
                typeLoot[loot_count]=Inventory_Type.SPELL;
                loot_count++;
            }
        }
        else{
            loot_count = random.nextInt(3)+1;
            for (byte i=0;i<loot_count;i++){
                typeLoot[i]=random.nextInt(4);
            }
        }
        for (int i = 0; i < loot_count; i++) {
            loot[i].bringToFront();
            loot[i].Change(db_open_helper, random, mCardTableTarget.getGearScore(),typeLoot[i]);
            loot[i].setVisibility(View.VISIBLE);
            loot[i].Open();
        }

        is_loot_enable = true;
        loot_id = 0;
        Loot_Get();

        if (loot_count > 0) {
            button_continue.bringToFront();
            button_continue.setVisibility(View.VISIBLE);
        }
    }

    private void spawn(Card_Table cardTable) {
        if (cardTable.Is_Close()) {
            if (random.nextInt(mChanceChest)==0){
                cardTable.Change(db_open_helper, 8);
                cardTable.Set_Money(0);
                cardTable.setGearScore(mGearScore);
            }
            else{
                if (random.nextInt(mChanceVendor)==0){
                    cardTable.Change(db_open_helper, random.nextInt(3));
                }
                else{
                    if (random.nextInt(mChanceHalt)==0){
                        cardTable.Change(db_open_helper, 7);
                    }
                    else{
                        cardTable.Change(db_open_helper, random, mGearScore);
                    }
                }
            }
        }
    }

    public void on_Click_Button_Start(View view) {
        data_base = db_open_helper.getWritableDatabase();
        data_base.close();
        Set_Text_Size();
        Set_Animators();

        Button_Start();
    }
    void Button_Start() {
        for (int i = 0; i < 8; i++) {
            mCardsTable[i].imageView.setVisibility(View.VISIBLE);
        }
        card_center.setVisibility(View.VISIBLE);

        for (byte i = 0; i < inventory_item_max_count; i++) {
            inventory[i].setOnClickListener(mInventoryOnClickSwap);
        }
/*
        for (int i = 0; i < loot_max_count; i++) {
            loot[i].setOnClickListener(mInventoryOnClickSwap);
        }
*/
        hand_one.setOnClickListener(mInventoryOnClickSwap);
        hand_two.setOnClickListener(mInventoryOnClickSwap);

        button_start.setVisibility(View.GONE);

        money_text.setText(String.valueOf(money));

        is_animate = true;
        Log.d("is_animate", String.valueOf(is_animate));
        openCardTable.start();
    }

    public void on_Click_Button_Continue(View view) {

        is_loot_enable = false;
        for (byte i = 0; i < loot_max_count; i++) {
            loot[i].Close(card_back);
            loot[i].setVisibility(View.GONE);
        }
        loot_count = 0;
        if (target_swap != null) {
            Target_Reset();
        }
        mCardTableTarget.getChangeAnimation().start();
        mCardTableTarget.setOnClickListener(setTargetListener);
        button_continue.setVisibility(View.GONE);
    }

    public void On_Click_Trade_Exit(View view){
        mCardTableTarget.getChangeAnimation().start();
        trade_skill.setVisibility(View.GONE);
        trade_zone.setVisibility(View.GONE);
        table.setOnDragListener(mTableOnDropListener);
    }

    public void onClickIconStats(View view){

    }

    public void onClickIconMenu(View view){
        if (findViewById(R.id.testMenu).getVisibility()==View.GONE){
            findViewById(R.id.testMenu).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.testMenu).setVisibility(View.GONE);
        }
    }

    public void onClickTESTButton(View view){

        EditText baseText = findViewById(R.id.TEST1);
        EditText idText = findViewById(R.id.id);
        EditText paramText = findViewById(R.id.parmetr);
        EditText valueText = findViewById(R.id.value);

       try {
            String base = baseText.getText().toString();
            String id = idText.getText().toString();
            String param = paramText.getText().toString();
            String value = valueText.getText().toString();
            Integer.parseInt(value);
            final String a = "0";
            final String b = "1";
            final String c = "2";
            String where = null;
            String[] arg = null;
            ContentValues values = new ContentValues();

            switch (base){
                case a:{
                    base = DB_Open_Helper.sTableTest;
                    break;
                }
                case b:{
                    Integer.parseInt(id);
                    base = DB_Open_Helper.table_mobs;
                    where = DB_Open_Helper.id +"=?";
                    arg = new String[]{id+""};
                    break;
                }
                case c:{
                    Integer.parseInt(id);
                    base = DB_Open_Helper.table_inventory;
                    where = DB_Open_Helper.id +"=?";
                    arg = new String[]{id+""};
                    break;
                }
                default:{
                    break;
                }
            }
            values.put(param, value);

            SQLiteDatabase data_base = db_open_helper.getWritableDatabase();

            data_base.update(
                    base,
                    values,
                    where,
                    arg
            );
            data_base.close();
        }
        catch (Exception e){
            baseText.setText("");
            idText.setText("");
            paramText.setText("");
            valueText.setText("");
        }

        baseText.setText("");
        idText.setText("");
        paramText.setText("");
        valueText.setText("");
    }
}