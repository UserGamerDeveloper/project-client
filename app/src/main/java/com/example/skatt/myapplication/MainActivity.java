package com.example.skatt.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Random;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

public class MainActivity extends AppCompatActivity {

    int money;
    int money_bank = 0;
    int hp_max = 30;
    int hp = hp_max;
    Card_Mob mob_target;
    Card_Mob[] cards = new Card_Mob[8];
    Card_Inventory[] loot = new Card_Inventory[3];
    Card_Inventory[] inventory = new Card_Inventory[4];
    Card_Hand hand_one;
    Card_Hand hand_two;
    ImageView card_center;
    boolean mShowingBack = false;
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

    int card_animation_duration = 500;
    private int duration = 500;

    AnimatorSet card_reset_column_right = new AnimatorSet();
    AnimatorSet card_reset_column_center = new AnimatorSet();
    AnimatorSet card_reset_column_left = new AnimatorSet();
    AnimatorSet card_reset_row_top = new AnimatorSet();
    AnimatorSet card_reset_row_center = new AnimatorSet();
    AnimatorSet card_reset_row_bottom = new AnimatorSet();

    AnimatorSet card_6_animation_click_mob = new AnimatorSet();
    AnimatorSet card_6_animation_next = new AnimatorSet();
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

    AnimatorSet card_column_center_copy = new AnimatorSet();
    AnimatorSet card_start_rotate = new AnimatorSet();
    AnimatorSet card_start_rotate_back = new AnimatorSet();
    AnimatorSet card_start_rotate_front = new AnimatorSet();

    Animator card_6_animatoin_rotate_front;
    Animator card_6_animatoin_rotate_back;

    View.OnLongClickListener on_long_click = on_long_click();

    View.OnLongClickListener on_long_click() {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, v, 0);
                v.setVisibility(View.INVISIBLE);
                return true;
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

    View.OnDragListener on_drop = Create_On_Drop();

    View.OnDragListener Create_On_Drop() {
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
                    if (v.equals(table)) {
                        if (target_swap.Get_Type() == Inventory_Type.FOOD) {
                            Change_HP(target_swap.Get_Value_One());
                            inventory_item_count--;
                            Inventory_Sort();
                            Loot_Get();
                            Target_Reset();
                            return true;
                        }
                    }
                    else {
                        Card_Inventory target_swap_two = ((Card_Inventory) v);
                        if (target_swap_two.slot_type!=target_swap.slot_type) {
                            if (target_swap_two.slot_type == Slot_Type.HAND) {
                                if (target_swap.Get_Type() == Inventory_Type.SHIELD || target_swap.Get_Type() ==
                                        Inventory_Type.WEAPON) {
                                    if(((Card_Hand)target_swap_two).durability_text.getVisibility()==View.VISIBLE){
                                            inventory_temp.Copy(target_swap_two);
                                            target_swap_two.Copy(target_swap);
                                            target_swap.Copy(inventory_temp);
                                            target_swap.setVisibility(View.VISIBLE);
                                    }
                                    else{
                                            if(target_swap.slot_type==Slot_Type.INVENTORY){
                                                target_swap_two.Copy(target_swap);
                                                ((Card_Hand)target_swap_two).durability_text.setVisibility(View.VISIBLE);
                                                ((Card_Hand)target_swap_two).durability_image.setVisibility(View.VISIBLE);
                                                inventory_item_count--;
                                                Inventory_Sort();
                                                Loot_Get();
                                            }
                                            else{
                                                target_swap_two.Copy(target_swap);
                                                ((Card_Hand)target_swap_two).durability_text.setVisibility(View.VISIBLE);
                                                ((Card_Hand)target_swap_two).durability_image.setVisibility(View.VISIBLE);
                                                target_swap.setVisibility(View.GONE);
                                                loot_count--;
                                                Try_Continue();
                                            }
                                    }
                                    Target_Reset();
                                    return true;
                                }
                                else {
                                    target_swap.setVisibility(View.VISIBLE);
                                    return false;
                                }
                            }
                            if (target_swap_two.slot_type == Slot_Type.LOOT) {
                                if (target_swap.slot_type != Slot_Type.HAND) {
                                    inventory_temp.Copy(target_swap_two);
                                    target_swap_two.Copy(target_swap);
                                    target_swap.Copy(inventory_temp);
                                    target_swap.setVisibility(View.VISIBLE);
                                    Target_Reset();
                                    return true;
                                }
                                else {
                                    if (target_swap_two.Get_Type() == Inventory_Type.WEAPON ||
                                            target_swap_two.Get_Type() == Inventory_Type.SHIELD) {
                                        if(((Card_Hand)target_swap).durability_text.getVisibility()==View.VISIBLE){
                                                inventory_temp.Copy(target_swap_two);
                                                target_swap_two.Copy(target_swap);
                                                target_swap.Copy(inventory_temp);
                                        }
                                        else{
                                            target_swap.Copy(target_swap_two);
                                            ((Card_Hand)target_swap).durability_text.setVisibility(View.VISIBLE);
                                            ((Card_Hand)target_swap).durability_image.setVisibility(View.VISIBLE);
                                            target_swap_two.setVisibility(View.GONE);
                                            loot_count--;
                                            Try_Continue();
                                        }
                                        target_swap.setVisibility(View.VISIBLE);
                                        Target_Reset();
                                        return true;
                                    }
                                    else {
                                        target_swap.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                }
                            }
                        }
                        else{
                            target_swap.setVisibility(View.VISIBLE);
                            return false;
                        }
                    }
                }
                return false;
            }
        };
    }

    byte halt_health = 1;
    AnimatorSet card_6_animation_click_vendor = new AnimatorSet();
    View.OnClickListener on_click_card_6 = on_click_card_6();

    View.OnClickListener on_click_card_6() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_animate) {
                    is_animate = true;
                    mob_target = (Card_Mob) v;
                    if(mob_target.Get_Type()==Card_Table_Type.MOB){
                        mob_target.bringToFront();
                        card_6_animation_click_mob.start();
                        mob_target.setOnClickListener(damag_listener);
                    }
                    if(mob_target.Get_Type()==Card_Table_Type.VENDOR){
                        shadow.bringToFront();
                        mob_target.bringToFront();
                        card_6_animation_click_vendor.start();
                        Picasso.with(getBaseContext()).load(R.drawable.navik_torgovca).into(trade_skill_image);
                        trade_skill_image.setOnClickListener(on_click_vendor_skill);
                        for (byte i = 0; i<loot_max_count;i++){
                            trade_item[i].Change(db_open_helper,random);
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
                    if (mob_target.Get_Type()==Card_Table_Type.BLACKSMITH){
                        shadow.bringToFront();
                        mob_target.bringToFront();
                        card_6_animation_click_vendor.start();
                        Picasso.with(getBaseContext()).load(R.drawable.navik_kuznecaa).into(trade_skill_image);
                        trade_skill_image.setOnClickListener(on_click_blacksmith_skill);
                        for (byte i = 0; i<loot_max_count;i++){
                            trade_item[i].Change(db_open_helper,random, random.nextInt(2));
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
                    if (mob_target.Get_Type()==Card_Table_Type.INNKEEPER){
                        shadow.bringToFront();
                        mob_target.bringToFront();
                        card_6_animation_click_vendor.start();
                        Picasso.with(getBaseContext()).load(R.drawable.navik_traktirshika).into(trade_skill_image);
                        trade_skill_image.setOnClickListener(on_click_innkeeper_skill);
                        for (byte i = 0; i<loot_max_count;i++){
                            trade_item[i].Change(db_open_helper,random, Inventory_Type.FOOD);
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
                    if (mob_target.Get_Type()==Card_Table_Type.QUEST_BOARD){
                        shadow.bringToFront();
                        mob_target.bringToFront();
                        card_6_animation_click_vendor.start();
                        Picasso.with(getBaseContext()).load(R.drawable.navik_doska_objavleniy).into(trade_skill_image);
                        trade_skill_image.setOnClickListener(on_click_innkeeper_skill);
                        for (byte i = 0; i<loot_max_count;i++){
                            trade_item[i].Change(db_open_helper,random, Inventory_Type.FOOD);
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
                    if (mob_target.Get_Type()==Card_Table_Type.HALT){
                        shadow.bringToFront();
                        mob_target.bringToFront();
                        card_6_animation_click_mob.start();
                        Change_HP(halt_health);
                        card_6_animation_next.start();
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
    AnimatorListenerAdapter target_animation_end = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            for (byte i = 0; i < inventory_item_max_count; i++) {
                inventory[i].setOnClickListener(inventory_swap);
            }
            for (int i = 0; i < loot_max_count; i++) {
                loot[i].setOnClickListener(inventory_swap);
            }
            hand_one.setOnClickListener(inventory_swap);
            hand_two.setOnClickListener(inventory_swap);
        }
    };
    Animator target_on_animation;
    Animator target_off_animation;
    View.OnClickListener inventory_swap = Create_Inventory_Swap();

    View.OnClickListener Create_Inventory_Swap() {
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
                    target_swap.setOnLongClickListener(on_long_click);
                    is_first_click = false;

                    target_off_animation.addListener(target_animation_end);
                    target_on_animation.addListener(target_animation_end);
                    target_on_animation.start();

                    Delete_OnClickListener_Before_Anomation_Target_Reset();
                }
                else {
                    Card_Inventory target_swap_two = (Card_Inventory) v;
                    if (!target_swap.equals(target_swap_two) ) {
                        if (target_swap_two.slot_type!=target_swap.slot_type) {
                            if (target_swap_two.slot_type == Slot_Type.HAND) {
                                if (target_swap.Get_Type() == Inventory_Type.SHIELD || target_swap.Get_Type() ==
                                        Inventory_Type.WEAPON) {
                                    if(((Card_Hand)target_swap_two).durability_text.getVisibility()==View.VISIBLE){
                                        inventory_temp.Copy(target_swap_two);
                                        target_swap_two.Copy(target_swap);
                                        target_swap.Copy(inventory_temp);
                                    }
                                    else{
                                        if(target_swap.slot_type==Slot_Type.INVENTORY){
                                            target_swap_two.Copy(target_swap);
                                            ((Card_Hand)target_swap_two).durability_text.setVisibility(View.VISIBLE);
                                            ((Card_Hand)target_swap_two).durability_image.setVisibility(View.VISIBLE);
                                            inventory_item_count--;
                                            Inventory_Sort();
                                            Loot_Get();
                                        }
                                        else{
                                            target_swap_two.Copy(target_swap);
                                            ((Card_Hand)target_swap_two).durability_text.setVisibility(View.VISIBLE);
                                            ((Card_Hand)target_swap_two).durability_image.setVisibility(View.VISIBLE);
                                            target_swap.setVisibility(View.GONE);
                                            loot_count--;
                                            Try_Continue();
                                        }
                                    }
                                    Target_Reset();
                                    Delete_OnClickListener_Before_Anomation_Target_Reset();
                                }
                            }
                            if (target_swap_two.slot_type == Slot_Type.LOOT) {
                                if (target_swap.slot_type != Slot_Type.HAND) {
                                    inventory_temp.Copy(target_swap_two);
                                    target_swap_two.Copy(target_swap);
                                    target_swap.Copy(inventory_temp);
                                    Target_Reset();
                                    Delete_OnClickListener_Before_Anomation_Target_Reset();
                                }
                                else {
                                    if (target_swap_two.Get_Type() == Inventory_Type.WEAPON ||
                                            target_swap_two.Get_Type() == Inventory_Type.SHIELD) {
                                        if(((Card_Hand)target_swap).durability_text.getVisibility()==View.VISIBLE){
                                            inventory_temp.Copy(target_swap_two);
                                            target_swap_two.Copy(target_swap);
                                            target_swap.Copy(inventory_temp);
                                        }
                                        else{
                                            target_swap.Copy(target_swap_two);
                                            ((Card_Hand)target_swap).durability_text.setVisibility(View.VISIBLE);
                                            ((Card_Hand)target_swap).durability_image.setVisibility(View.VISIBLE);
                                            target_swap_two.setVisibility(View.GONE);
                                            loot_count--;
                                            Try_Continue();
                                        }
                                        Target_Reset();
                                        Delete_OnClickListener_Before_Anomation_Target_Reset();
                                    }
                                }
                            }
                        }
                    }
                    else {
                        Target_Reset();
                        Delete_OnClickListener_Before_Anomation_Target_Reset();
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
            target_swap.setOnLongClickListener(null);
            Log.d("target off", (String) target_swap.name_text.getText());
        }

        target_swap = null;
/*
        for (int i = 0; i < loot_max_count; i++) {
            loot[i].setOnClickListener(null);
        }
*/
/*
        hand_one.setOnClickListener(null);
        hand_two.setOnClickListener(null);
*/
        is_first_click = true;
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

                mob_target.Set_Value_One(mob_target.Get_Value_One() - ((hand_one.Get_Type() == Inventory_Type.WEAPON) ?
                        hand_one.Get_Value_One() : 0) - ((hand_two.Get_Type() == Inventory_Type.WEAPON) ?
                        hand_two.Get_Value_One() : 0));
                mob_target.Set_Value_One_text(mob_target.Get_Value_One());

                HP_Calculation();

                if (hp < 1) {
                    Game_Reset();
                    return;
                }

                if(hand_one.durability_text.getVisibility()==View.VISIBLE){
                    hand_one.Set_Durability(hand_one.Get_Durability()-1);
                    if (hand_one.Get_Durability()<1){

                        hand_one.name_text.setText("Кулак");
                        hand_one.Set_Type(Inventory_Type.WEAPON);
                        hand_one.Set_Value_One(1);
                        hand_one.Set_Value_One_text(1);
                        hand_one.Set_Id_Drawable(R.drawable.kulak_levo);
                        Picasso.with(getBaseContext()).load(R.drawable.kulak_levo).into(hand_one.imageView);
/*
                        hand_one.imageView.setImageResource(R.drawable.kulak_levo);
*/
                        hand_one.durability_text.setVisibility(View.INVISIBLE);
                        hand_one.durability_image.setVisibility(View.INVISIBLE);
                    }
                    else{
                        hand_one.Set_Durability_Text(hand_one.Get_Durability());
                    }
                }

                if(hand_two.durability_text.getVisibility()==View.VISIBLE){
                    hand_two.Set_Durability(hand_two.Get_Durability()-1);
                    if (hand_two.Get_Durability()<1){

                        hand_two.name_text.setText("Кулак");
                        hand_two.Set_Type(Inventory_Type.WEAPON);
                        hand_two.Set_Value_One(1);
                        hand_two.Set_Value_One_text(1);
                        hand_two.Set_Id_Drawable(R.drawable.kulak_pravo);
                        Picasso.with(getBaseContext()).load(R.drawable.kulak_pravo).into(hand_two.imageView);
/*
                        hand_two.imageView.setImageResource(R.drawable.kulak_pravo);
*/
                        hand_two.durability_text.setVisibility(View.INVISIBLE);
                        hand_two.durability_image.setVisibility(View.INVISIBLE);
                    }
                    else{
                        hand_two.Set_Durability_Text(hand_two.Get_Durability());
                    }
                }

                if (mob_target.Get_Value_One() < 1) {

                    mob_target.Set_Value_One_text(0);
                    money += mob_target.Get_Money();
                    money_text.setText(String.valueOf(money));
                    cards[4].setOnClickListener(on_click_card_6);

                    card_6_rotate.start();

                    shadow.bringToFront();

                    loot_count = random.nextInt(4);
/*
                    loot_count = 3;
*/
                    for (int i = 0; i < loot_count; i++) {
                        loot[i].bringToFront();
                        loot[i].Change(db_open_helper, random);
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
            }

            private void HP_Calculation() {
                int mob_damage = mob_target.Get_Damage();
                if(mob_damage>0&&hand_one.Get_Type() == Inventory_Type.SHIELD){
                    if (hand_one.Get_Value_One() < mob_damage){
                        mob_damage -= hand_one.Get_Value_One();
                    }
                    else{
                        mob_damage = 0;
                    }
                }
                if(mob_damage>0&&hand_two.Get_Type() == Inventory_Type.SHIELD){
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

    Random random = new Random();

    DB_Open_Helper db_open_helper = new DB_Open_Helper(MainActivity.this);
    SQLiteDatabase data_base;

    AnimatorListenerAdapter animator_listener_adapter_end = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            is_animate = false;
        }
    };
    AnimatorListenerAdapter animator_listener_adapter_walt_start_animation = new AnimatorListenerAdapter() {

        @Override
        public void onAnimationStart(Animator animation) {

            if (animation.equals(card_animation_get_right)) {

                cards[2].Close(card_back);
                cards[4].Close(card_back);
                cards[7].Close(card_back);
            }

            if (animation.equals(card_start_rotate)) {

                if (cards[1].Is_Close(card_back)) {
                    cards[1].Change(data_base, db_open_helper, random);
                }

                if (cards[3].Is_Close(card_back)) {
                    cards[3].Change(data_base, db_open_helper, random);
                }

                if (cards[4].Is_Close(card_back)) {
                    cards[4].Change(data_base, db_open_helper, random);
                }

                if (cards[6].Is_Close(card_back)) {
                    cards[6].Change(data_base, db_open_helper, random);
                }
            }
        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }
    };
    AnimatorListenerAdapter animator_listener_adapter_walt_end_animation = new AnimatorListenerAdapter() {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            if (animation.equals(card_animation_left)) {

                cards[0].Copy(cards[1]);
                cards[3].Close(card_back);
                cards[5].Copy(cards[6]);
            }

            if (animation.equals(card_column_center_copy)) {

                cards[1].Copy(cards[2]);
                cards[6].Copy(cards[7]);
            }

            if (animation.equals(card_6_animatoin_rotate_front)) {

                cards[4].Close(card_back);
            }

            if (animation.equals(card_start_rotate_back)) {
                cards[1].Open();
                cards[3].Open();
                cards[4].Open();
                cards[6].Open();
            }
        }
    };
    private AnimatorSet card_6_animation_increase = new AnimatorSet();
    private Animator card_6_animation_movement;

    Drawable hp_bar_drawable;

    private AnimatorSet card_6_animation_decrease = new AnimatorSet();
    private AnimatorSet card_6_animation_increase_back = new AnimatorSet();

    private Animator card_6_animation_movement_back_x;
    AnimatorSet card_6_animation_reset = new AnimatorSet();
    private AnimatorSet card_6_rotate = new AnimatorSet();

    ConstraintLayout table;

    final class Slot_Type {

        final static byte LOOT = 0;
        final static byte HAND = 1;
        final static byte INVENTORY = 2;
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

    View.OnClickListener card_trade_buy_click_yes = card_trade_click_yes();
    View.OnClickListener card_trade_click_yes() {
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
                    trade_item[i].Change(db_open_helper,random);
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
                    trade_window_text.setText(String.format("Починить руки за %d золотых?", cost_vendor_skill));
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
                hand_one.Set_Durability(10);
                hand_one.Set_Durability_Text(10);
                hand_two.Set_Durability(10);
                hand_two.Set_Durability_Text(10);
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        table = findViewById(R.id.table);
        table.setOnDragListener(on_drop);

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

        DB_Open_Helper.count_item_in_type.put(Inventory_Type.WEAPON, 5);
        DB_Open_Helper.count_item_in_type.put(Inventory_Type.SHIELD, 5);
        DB_Open_Helper.count_item_in_type.put(Inventory_Type.FOOD, 5);

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

        cards[0] = findViewById(R.id.card_table_1);
        cards[1] = findViewById(R.id.card_table_2);
        cards[2] = findViewById(R.id.card_table_3);
        cards[3] = findViewById(R.id.card_table_4);
        cards[4] = findViewById(R.id.card_table_6);
        cards[5] = findViewById(R.id.card_table_7);
        cards[6] = findViewById(R.id.card_table_8);
        cards[7] = findViewById(R.id.card_table_9);

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

        cards[0].value_one_text = findViewById(R.id.card_hp_1);
        cards[1].value_one_text = findViewById(R.id.card_hp_2);
        cards[2].value_one_text = findViewById(R.id.card_hp_3);
        cards[3].value_one_text = findViewById(R.id.card_hp_4);
        cards[4].value_one_text = findViewById(R.id.card_hp_6);
        cards[5].value_one_text = findViewById(R.id.card_hp_7);
        cards[6].value_one_text = findViewById(R.id.card_hp_8);
        cards[7].value_one_text = findViewById(R.id.card_hp_9);

        cards[0].value_two_text = findViewById(R.id.card_damage_1);
        cards[1].value_two_text = findViewById(R.id.card_damage_2);
        cards[2].value_two_text = findViewById(R.id.card_damage_3);
        cards[3].value_two_text = findViewById(R.id.card_damage_4);
        cards[4].value_two_text = findViewById(R.id.card_damage_6);
        cards[5].value_two_text = findViewById(R.id.card_damage_7);
        cards[6].value_two_text = findViewById(R.id.card_damage_8);
        cards[7].value_two_text = findViewById(R.id.card_damage_9);

        loot[0] = findViewById(R.id.card_loot_0);
        loot[1] = findViewById(R.id.card_loot_1);
        loot[2] = findViewById(R.id.card_loot_2);

        loot[0].setOnDragListener(on_drop);
        loot[1].setOnDragListener(on_drop);
        loot[2].setOnDragListener(on_drop);

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
        hand_one.setOnDragListener(on_drop);
        hand_two.setOnDragListener(on_drop);
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

        Game_Load();

/*
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id, new CardFrontFragment())
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

    private void Game_Load() {

        for (int i = 0; i < 8; i++) {
            cards[i].name_text.setVisibility(View.INVISIBLE);
            cards[i].value_two_text.setVisibility(View.INVISIBLE);
            cards[i].value_one_text.setVisibility(View.INVISIBLE);
            cards[i].imageView.setImageResource(card_back);
            cards[i].Set_Id_Drawable(card_back);
            cards[i].imageView.setVisibility(View.INVISIBLE);
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

        hand_one.Set_Value_One(1);
        hand_one.id_drawable = R.drawable.kulak_levo;
        hand_one.name_text.setText("Кулак");
        hand_one.Set_Type(Inventory_Type.WEAPON);
        hand_one.value_one_text.setText(" 1");
        Picasso.with(getBaseContext()).load(R.drawable.kulak_levo).into(hand_one.imageView);
/*
        hand_one.imageView.setImageResource(R.drawable.fist);
*/
        hand_one.setOnClickListener(null);
        hand_one.durability_text.setVisibility(View.INVISIBLE);
        hand_one.durability_image.setVisibility(View.INVISIBLE);

        hand_two.Set_Value_One(1);
        hand_two.id_drawable = R.drawable.kulak_pravo;
        hand_two.name_text.setText("Кулак");
        hand_two.Set_Type(Inventory_Type.WEAPON);
        hand_two.value_one_text.setText(" 1");
        Picasso.with(getBaseContext()).load(R.drawable.kulak_pravo).into(hand_two.imageView);
/*
        hand_two.imageView.setImageResource(R.drawable.fist);
*/
        hand_two.setOnClickListener(null);
        hand_two.durability_text.setVisibility(View.INVISIBLE);
        hand_two.durability_image.setVisibility(View.INVISIBLE);

        inventory[0].slot_id = 0;
        for (byte i = 1; i < inventory_item_max_count; i++) {
            inventory[i].setVisibility(View.GONE);
            inventory[i].slot_id = i;
        }

        hp = hp_max;

        hp_text.setText(String.valueOf(hp));
        hp_bar_drawable.setLevel(10000 * hp / hp_max);

        is_animate = true;

        cards[4].setOnClickListener(on_click_card_6);

        money_text.setText(String.valueOf(money_bank));

        shadow.setVisibility(View.VISIBLE);
        shadow.setAlpha(0f);
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

        data_base = db_open_helper.getWritableDatabase();
        data_base.close();

        Set_Text_Size();

        Set_Animators();
    }

    private void Set_Text_Size() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int card_height = cards[0].imageView.getHeight();
        int card_width = cards[0].imageView.getWidth();

        money_text_size = displayMetrics.heightPixels * money_text_size_constant;
        money_text.setTextSize(COMPLEX_UNIT_PX, money_text_size);

        card_name_text_size = (float) (Math.sqrt(Math.pow(card_width, 2.0) +
                Math.pow(card_height, 2.0)) * card_name_text_size_constant);
        card_hp_and_damage_text_size = (float) (Math.sqrt(Math.pow(card_width, 2.0) +
                Math.pow(card_height, 2.0)) * card_hp_and_damage_text_size_constant);
        for (int i = 0; i < 8; i++) {
            cards[i].name_text.setTextSize(COMPLEX_UNIT_PX, card_name_text_size);
            cards[i].value_one_text.setTextSize(COMPLEX_UNIT_PX, card_hp_and_damage_text_size);
            cards[i].value_two_text.setTextSize(COMPLEX_UNIT_PX, card_hp_and_damage_text_size);
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
    Animator shadow_show;
    Animator shadow_hide;
    private void Set_Animators() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int card_height = cards[0].imageView.getHeight();
        int card_width = cards[0].imageView.getWidth();

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
        cards[6].imageView.getLocationOnScreen(card_8_coordinates);
        cards[3].imageView.getLocationOnScreen(card_4_coordinates);
        cards[4].imageView.getLocationOnScreen(card_6_coordinates);

        ConstraintLayout table_layout = findViewById(R.id.table);
        int card_distance_between_Y = card_5_coordinates[1] - card_8_coordinates[1];
        int card_distance_between_X = card_5_coordinates[0] - card_4_coordinates[0];
        int card_coordinates_animation_top_start = -card_8_coordinates[1] - card_height;
        int card_coordinates_animation_bottom_start = table_layout.getHeight() + card_height - card_8_coordinates[1];
        int card_coordinates_animation_right_start = table_layout.getWidth() + card_width - card_4_coordinates[0];
        int card_coordinates_animation_left_start = -card_4_coordinates[0] - card_width;

        shadow_show  = ObjectAnimator.ofFloat(shadow, View.ALPHA, 0f, .5f);
        shadow_hide = ObjectAnimator.ofFloat(shadow, View.ALPHA, .5f, 0f);
        card_6_animation_click_vendor.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(cards[4], View.TRANSLATION_X, 0f, card_4_coordinates[0]-card_6_coordinates[0]),
                ObjectAnimator.ofFloat(cards[4], View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                shadow_show
        );

        card_animation_left.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(cards[0], View.TRANSLATION_X, 0f, card_coordinates_animation_left_start),
                ObjectAnimator.ofFloat(cards[3], View.TRANSLATION_X, 0f, card_coordinates_animation_left_start),
                ObjectAnimator.ofFloat(cards[5], View.TRANSLATION_X, 0f, card_coordinates_animation_left_start),

                ObjectAnimator.ofFloat(cards[1], View.TRANSLATION_X, 0f, -card_distance_between_X),
                ObjectAnimator.ofFloat(cards[2], View.TRANSLATION_X, 0f, -card_distance_between_X),
                ObjectAnimator.ofFloat(cards[4], View.TRANSLATION_X, 0f, -card_distance_between_X),
                ObjectAnimator.ofFloat(cards[6], View.TRANSLATION_X, 0f, -card_distance_between_X),
                ObjectAnimator.ofFloat(cards[7], View.TRANSLATION_X, 0f, -card_distance_between_X),
                ObjectAnimator.ofFloat(card_center, View.TRANSLATION_X, 0f, -card_distance_between_X)
        );

        card_animation_right.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(cards[2], View.TRANSLATION_X, 0f, card_coordinates_animation_right_start),
                ObjectAnimator.ofFloat(cards[4], View.TRANSLATION_X, 0f, card_coordinates_animation_right_start),
                ObjectAnimator.ofFloat(cards[7], View.TRANSLATION_X, 0f, card_coordinates_animation_right_start),

                ObjectAnimator.ofFloat(cards[0], View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(cards[1], View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(cards[3], View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(cards[5], View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(cards[6], View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(card_center, View.TRANSLATION_X, 0f, card_distance_between_X)
        );

        card_animation_down.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(cards[0], View.TRANSLATION_Y, 0f, card_coordinates_animation_bottom_start),
                ObjectAnimator.ofFloat(cards[1], View.TRANSLATION_Y, 0f, card_coordinates_animation_bottom_start),
                ObjectAnimator.ofFloat(cards[2], View.TRANSLATION_Y, 0f, card_coordinates_animation_bottom_start),

                ObjectAnimator.ofFloat(cards[3], View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(cards[4], View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(cards[5], View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(cards[6], View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(cards[7], View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(card_center, View.TRANSLATION_Y, 0f, card_distance_between_Y)
        );

        card_animation_up.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(cards[5], View.TRANSLATION_Y, 0f, card_coordinates_animation_top_start),
                ObjectAnimator.ofFloat(cards[6], View.TRANSLATION_Y, 0f, card_coordinates_animation_top_start),
                ObjectAnimator.ofFloat(cards[7], View.TRANSLATION_Y, 0f, card_coordinates_animation_top_start),

                ObjectAnimator.ofFloat(cards[1], View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(cards[2], View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(cards[3], View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(cards[4], View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(cards[0], View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(card_center, View.TRANSLATION_Y, 0f, -card_distance_between_Y)
        );

        card_animation_get_top.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(cards[5], View.TRANSLATION_Y, card_coordinates_animation_top_start, 0f),
                ObjectAnimator.ofFloat(cards[6], View.TRANSLATION_Y, card_coordinates_animation_top_start, 0f),
                ObjectAnimator.ofFloat(cards[7], View.TRANSLATION_Y, card_coordinates_animation_top_start, 0f)
        );

        card_animation_get_bottom.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(cards[0], View.TRANSLATION_Y, card_coordinates_animation_bottom_start, 0f),
                ObjectAnimator.ofFloat(cards[1], View.TRANSLATION_Y, card_coordinates_animation_bottom_start, 0f),
                ObjectAnimator.ofFloat(cards[2], View.TRANSLATION_Y, card_coordinates_animation_bottom_start, 0f)
        );

        card_animation_get_left.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(cards[0], View.TRANSLATION_X, card_coordinates_animation_left_start, 0f),
                ObjectAnimator.ofFloat(cards[3], View.TRANSLATION_X, card_coordinates_animation_left_start, 0f),
                ObjectAnimator.ofFloat(cards[5], View.TRANSLATION_X, card_coordinates_animation_left_start, 0f)
        );

        card_animation_get_right.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(cards[2], View.TRANSLATION_X, card_coordinates_animation_right_start, 0f),
                ObjectAnimator.ofFloat(cards[4], View.TRANSLATION_X, card_coordinates_animation_right_start, 0f),
                ObjectAnimator.ofFloat(cards[7], View.TRANSLATION_X, card_coordinates_animation_right_start, 0f)
        );

        card_column_center_copy.playTogether(
                ObjectAnimator.ofFloat(cards[1], View.TRANSLATION_X, -card_5_coordinates[0] - card_width, -card_5_coordinates[0] - card_width),
                ObjectAnimator.ofFloat(card_center, View.TRANSLATION_X, -card_5_coordinates[0] - card_width, -card_5_coordinates[0] - card_width),
                ObjectAnimator.ofFloat(cards[6], View.TRANSLATION_X, -card_5_coordinates[0] - card_width, -card_5_coordinates[0] - card_width)
        );

        Set_Card_Animators_Reset();

        card_start_rotate_back.setDuration(duration).playTogether(
                ObjectAnimator.ofFloat(cards[4], View.ROTATION_Y, 0f, 90f),
                ObjectAnimator.ofFloat(cards[3], View.ROTATION_Y, 0f, 90f),
                ObjectAnimator.ofFloat(cards[6], View.ROTATION_Y, 0f, 90f),
                ObjectAnimator.ofFloat(cards[1], View.ROTATION_Y, 0f, 90f)
        );

        card_start_rotate_front.setDuration(duration).playTogether(
                ObjectAnimator.ofFloat(cards[4], View.ROTATION_Y, -90f, 0f),
                ObjectAnimator.ofFloat(cards[3], View.ROTATION_Y, -90f, 0f),
                ObjectAnimator.ofFloat(cards[6], View.ROTATION_Y, -90f, 0f),
                ObjectAnimator.ofFloat(cards[1], View.ROTATION_Y, -90f, 0f)
        );

        card_start_rotate_back.addListener(animator_listener_adapter_walt_end_animation);

        card_start_rotate.playSequentially(
                card_start_rotate_back,
                card_start_rotate_front
        );

        card_6_animatoin_rotate_front = ObjectAnimator.ofFloat(cards[4], View.ROTATION_Y, 0f, 90f);
        card_6_animatoin_rotate_back = ObjectAnimator.ofFloat(cards[4], View.ROTATION_Y, -90f, 0f);

        card_6_animatoin_rotate_front.addListener(animator_listener_adapter_walt_end_animation);
        card_animation_left.addListener(animator_listener_adapter_walt_end_animation);
        card_column_center_copy.addListener(animator_listener_adapter_walt_end_animation);
        card_animation_get_right.addListener(animator_listener_adapter_walt_start_animation);
        card_start_rotate.addListener(animator_listener_adapter_end);
        card_start_rotate.addListener(animator_listener_adapter_walt_start_animation);

        card_6_animation_increase.playTogether(
                ObjectAnimator.ofFloat(cards[4], View.SCALE_X, 1f, 3f),
                ObjectAnimator.ofFloat(cards[4], View.SCALE_Y, 1f, 3f)
        );
        card_6_animation_increase_back.playTogether(
                ObjectAnimator.ofFloat(cards[4], View.SCALE_X, 3f, 1f),
                ObjectAnimator.ofFloat(cards[4], View.SCALE_Y, 3f, 1f)
        );
        card_6_animation_movement = ObjectAnimator.ofFloat(cards[4], View.TRANSLATION_X, 0f, -card_distance_between_X);
        card_6_animation_movement_back_x = ObjectAnimator.ofFloat(cards[4], View.TRANSLATION_X, -card_distance_between_X, 0f);
        Animator card_6_animation_movement_back_y = ObjectAnimator.ofFloat(cards[4], View.TRANSLATION_Y, 0F, 0f);

        card_6_animation_click_mob.setDuration(duration).playTogether(
                shadow_show,
                card_6_animation_increase,
                card_6_animation_movement
        );

        card_6_animation_decrease.setDuration(duration).playTogether(
                shadow_hide,
                card_6_animation_increase_back,
                card_6_animation_movement_back_x,
                card_6_animation_movement_back_y
        );

        card_6_rotate.playSequentially(
                card_6_animatoin_rotate_front,
                card_6_animatoin_rotate_back
        );

        card_6_animation_next.playSequentially(
                card_6_animation_decrease,
                card_animation_left,
                card_reset_column_left,
                card_column_center_copy,
                card_reset_column_center,
                card_animation_get_right,
                card_start_rotate
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

        card_6_animation_next.addListener(animator_listener_adapter_end);
        card_animation_click_left.addListener(animator_listener_adapter_end);
        card_animation_click_top.addListener(animator_listener_adapter_end);
        card_animation_click_bottom.addListener(animator_listener_adapter_end);
    }

    private void Set_Card_Animators_Reset() {
        card_reset_row_top.playTogether(
                ObjectAnimator.ofFloat(cards[5], View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(cards[6], View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(cards[7], View.TRANSLATION_Y, 0f, 0f)
        );

        card_reset_row_center.playTogether(
                ObjectAnimator.ofFloat(cards[3], View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(card_center, View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(cards[4], View.TRANSLATION_Y, 0f, 0f)
        );

        card_reset_row_bottom.playTogether(
                ObjectAnimator.ofFloat(cards[0], View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(cards[1], View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(cards[2], View.TRANSLATION_Y, 0f, 0f)
        );

        card_reset_column_right.playTogether(
                ObjectAnimator.ofFloat(cards[2], View.TRANSLATION_X, 0f, 0f),
                ObjectAnimator.ofFloat(cards[4], View.TRANSLATION_X, 0f, 0f),
                ObjectAnimator.ofFloat(cards[7], View.TRANSLATION_X, 0f, 0f)
        );

        card_reset_column_left.playTogether(
                ObjectAnimator.ofFloat(cards[0], View.TRANSLATION_X, 0f, 0f),
                ObjectAnimator.ofFloat(cards[3], View.TRANSLATION_X, 0f, 0f),
                ObjectAnimator.ofFloat(cards[5], View.TRANSLATION_X, 0f, 0f)
        );

        card_reset_column_center.playTogether(
                ObjectAnimator.ofFloat(cards[1], View.TRANSLATION_X, 0f, 0f),
                ObjectAnimator.ofFloat(card_center, View.TRANSLATION_X, 0f, 0f),
                ObjectAnimator.ofFloat(cards[6], View.TRANSLATION_X, 0f, 0f)
        );

        card_6_animation_reset.playTogether(
                ObjectAnimator.ofFloat(cards[4], View.TRANSLATION_X, 0f, 0f),
                ObjectAnimator.ofFloat(cards[4], View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(cards[4], View.SCALE_X, 1f, 1f),
                ObjectAnimator.ofFloat(cards[4], View.SCALE_Y, 1f, 1f)
        );
    }

    public void on_Click_Card_Left(View view) {

        if (!is_animate) {
            card_animation_click_left.start();
        }
    }

    public void on_Click_Card_Top(View view) {

        if (!is_animate) {
            card_animation_click_top.start();
        }
    }

    public void on_Click_Card_Bottom(View view) {

        if (!is_animate) {
            card_animation_click_bottom.start();
        }
    }

    public void on_Click_Button_Start(View view) {

        for (int i = 0; i < 8; i++) {
            cards[i].imageView.setVisibility(View.VISIBLE);
        }
        card_center.setVisibility(View.VISIBLE);

        for (byte i = 0; i < inventory_item_max_count; i++) {
            inventory[i].setOnClickListener(inventory_swap);
        }
        for (int i = 0; i < loot_max_count; i++) {
            loot[i].setOnClickListener(inventory_swap);
        }
        hand_one.setOnClickListener(inventory_swap);
        hand_two.setOnClickListener(inventory_swap);

        button_start.setVisibility(View.GONE);

        money_text.setText(String.valueOf(money));

        is_animate = true;
        card_start_rotate.start();
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
        button_continue.setVisibility(View.GONE);
        card_6_animation_next.start();
    }

    public void On_Click_Trade_Exit(View view){
        card_6_animation_next.start();
        trade_skill.setVisibility(View.GONE);
        trade_zone.setVisibility(View.GONE);
        table.setOnDragListener(on_drop);
    }
}