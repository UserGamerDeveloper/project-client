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
    int mHpMaxDefault=30;
    int mHpMax;
    int hp = mHpMax;
    Card_Table mCardTableTarget;
    Card_Table[] mCardsTable = new Card_Table[8];
    Card_Inventory[] loot = new Card_Inventory[3];
    Card_Inventory[] mInventory = new Card_Inventory[4];
    Card_Hand mHandOne;
    Card_Hand mHandTwo;
    ImageView card_center;
    ConstraintLayout button_start;
    ConstraintLayout button_continue;
    ConstraintLayout mTradeZone;
    ImageView mShadow;
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
    private int mChanceChest;
    byte mHaltHealth = (byte) mHpMax;
    boolean is_first_click = true;
    int loot_count;
    byte loot_id;
    byte mInventoryItemCount;
    final byte INVENTORYMAXCOUNT = 4;
    byte loot_max_count = 3;
    boolean is_loot_enable = false;
    boolean mIsAnimate;
    private final int card_back = R.drawable.card_back;
    private final int cardCenterBack = R.drawable.perekrestok;
    Random random = new Random();
    DB_Open_Helper db_open_helper = new DB_Open_Helper(MainActivity.this);
    SQLiteDatabase data_base;
    Drawable hp_bar_drawable;
    AnimatorSet card_6_animation_reset = new AnimatorSet();
    ConstraintLayout mTable;
    final class Slot_Type {

        final static byte LOOT = 0;
        final static byte HAND = 1;
        final static byte INVENTORY = 2;

        private Slot_Type() {
        }
    }
    int inventory_animation_delta;
    int hand_animation_delta;
    float loot_animation_delta;
    AnimatorSet card_6_animation_click_vendor = new AnimatorSet();
    Card_Inventory target_swap;
    Card_Inventory_Temp inventory_temp = new Card_Inventory_Temp();
    AnimatorListenerAdapter target_on_animation_end = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            deleteOnClickListenerBeforeAnomationTargetReset();
        }

        @Override
        public void onAnimationEnd(Animator animation) {

            for (byte i = 0; i < INVENTORYMAXCOUNT; i++) {
                mInventory[i].setOnClickListener(mInventoryOnClickSwap);
            }
            for (int i = 0; i < loot_max_count; i++) {
                loot[i].setOnClickListener(mInventoryOnClickSwap);
            }
            mHandOne.setOnClickListener(mInventoryOnClickSwap);
            mHandTwo.setOnClickListener(mInventoryOnClickSwap);

            target_swap.setOnLongClickListener(mOnLongClickListener);
            target_swap.setOnTouchListener(mCardMoveListener);
        }
    };
    AnimatorListenerAdapter on_target_off_animation = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            deleteOnClickListenerBeforeAnomationTargetReset();
            target_swap.setOnLongClickListener(null);
            target_swap.setOnTouchListener(null);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            for (byte i = 0; i < INVENTORYMAXCOUNT; i++) {
                mInventory[i].setOnClickListener(mInventoryOnClickSwap);
            }
            mHandOne.setOnClickListener(mInventoryOnClickSwap);
            mHandTwo.setOnClickListener(mInventoryOnClickSwap);
        }
    };
    Animator target_on_animation;
    Animator target_off_animation;
    Card_Inventory trade_target;
    TextView[] mTradeCost = new TextView[3];
    ImageView[] mTradeCostImage = new ImageView[3];
    Card_Inventory[] mTradeItem = new Card_Inventory[3];
    ConstraintLayout mTradeSkill;
    ImageView mTradeSkillImage;
    DialogWindow mDialogWindow = new DialogWindow();
    TextView mGearScoreText;
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

    View.OnLongClickListener mOnLongClickListener = mOnLongClickListener();

    View.OnLongClickListener mOnLongClickListener() {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (target_swap.getType() == Inventory_Type.FOOD) {
                    useFood();
                    return true;
                }
                if (target_swap.getType() == Inventory_Type.SPELL) {
                    useSpell();
                    return true;
                }
                if(target_swap.mSlotType == Slot_Type.HAND){
                    Card_Hand card_hand = (Card_Hand) target_swap;
                    if (card_hand.getIdDrawable()!=card_hand.hand_drawable){
                        if (mInventoryItemCount < INVENTORYMAXCOUNT){
                            mInventory[mInventoryItemCount].copy(card_hand);
                            mInventory[mInventoryItemCount].setVisibility(View.VISIBLE);
                            mInventoryItemCount++;
                            changeGearScore(getChangeGearScoreAfterReplace(null,0));
                            setHand(card_hand);
                        }
                        else{
                            mDialogWindow.openInfo("Нет места в инвентаре.");
                        }
                        Log.d("targetReset", "hand take off");
                        targetReset();
                    }
                    return true;
                }
                return true;
            }
        };
    }

    private void useFood() {
        changeHP(target_swap.getValueOne());
        mInventoryItemCount--;
        changeGearScore(-target_swap.getGearScore());
        inventorySort();
        lootGet();
        Log.d("targetReset", "use food");
        targetReset();
    }

    private void useSpell() {
        mobHPChange(-target_swap.getValueOne());
        changeGearScore(-target_swap.getGearScore());
        mInventoryItemCount--;
        inventorySort();
        lootGet();
        Log.d("targetReset", "use spell");
        targetReset();
        if (mCardTableTarget.getValueTwo() < 1) {
            mobDead();
        }
    }

    View.OnTouchListener mCardMoveListener = mCardMoveListener();
    View.OnTouchListener mCardMoveListener() {
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

    void changeHP(int hp_delta) {

        hp += hp_delta;
        if (hp < mHpMax) {
            hp_text.setText(String.valueOf(hp));
            hp_bar_drawable.setLevel(10000 * hp / mHpMax);
        }
        else {
            hp = mHpMax;
            hp_text.setText(String.valueOf(hp));
            hp_bar_drawable.setLevel(10000);
        }
    }

    void inventorySort() {

        target_swap.setVisibility(View.VISIBLE);
        byte id = target_swap.mSlotId;
        for (; id < mInventoryItemCount; id++) {
            mInventory[id].copy(mInventory[id + 1]);
        }
        mInventory[id].setVisibility(View.GONE);
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
                    if (target_swap_two.mSlotType !=target_swap.mSlotType) {
                        if (target_swap.getType() == Inventory_Type.SHIELD || target_swap.getType() ==
                                Inventory_Type.WEAPON) {
                            if(target_swap_two.durability_text.getVisibility()==View.VISIBLE){
                                inventory_temp.Copy(target_swap_two);
                                target_swap_two.copy(target_swap);
                                target_swap.copy(inventory_temp);
                                target_swap.setVisibility(View.VISIBLE);
                            }
                            else{
                                changeGearScore(getChangeGearScoreAfterReplace(0,null));
                                target_swap_two.copy(target_swap);
                                target_swap_two.durability_text.setVisibility(View.VISIBLE);
                                target_swap_two.durability_image.setVisibility(View.VISIBLE);
                                mInventoryItemCount--;
                                inventorySort();
                                lootGet();
                            }
                            Log.d("targetReset", "hand swap");
                            targetReset();
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
                    if (target_swap.mSlotType == Slot_Type.INVENTORY) {
                        if ((target_swap_two.getType() == Inventory_Type.WEAPON ||
                                target_swap_two.getType() == Inventory_Type.SHIELD) &&
                                (target_swap.getType() == Inventory_Type.WEAPON ||
                                        target_swap.getType() == Inventory_Type.SHIELD))
                        {
                            changeGearScore(getChangeGearScoreAfterReplace(target_swap.getGearScore(),target_swap_two.getGearScore()));
                        }
                        else{
                            if (target_swap.getType() == Inventory_Type.WEAPON ||
                                    target_swap.getType() == Inventory_Type.SHIELD)
                            {
                                changeGearScore(getChangeGearScoreAfterReplace(target_swap.getGearScore(),null));
                                changeGearScore(target_swap_two.getGearScore());
                            }
                            else{
                                if (target_swap_two.getType() == Inventory_Type.WEAPON ||
                                        target_swap_two.getType() == Inventory_Type.SHIELD)
                                {
                                    changeGearScore(getChangeGearScoreAfterReplace(null,target_swap_two.getGearScore()));
                                    changeGearScore(-target_swap.getGearScore());
                                }
                                else{
                                    changeGearScore(target_swap_two.getGearScore()-target_swap.getGearScore());
                                }
                            }
                        }
                        inventory_temp.Copy(target_swap_two);
                        target_swap_two.copy(target_swap);
                        target_swap.copy(inventory_temp);
                        target_swap.setVisibility(View.VISIBLE);
                        Log.d("targetReset", "loot swap");
                        targetReset();
                        return true;
                    }
                    else {
                        if (target_swap_two.getType() == Inventory_Type.WEAPON ||
                                        target_swap_two.getType() == Inventory_Type.SHIELD)
                        {
                            if(((Card_Hand)target_swap).durability_text.getVisibility()==View.VISIBLE){
                                        changeGearScore(getChangeGearScoreAfterReplace(target_swap.getGearScore(),target_swap_two.getGearScore()));
                                        inventory_temp.Copy(target_swap_two);
                                        target_swap_two.copy(target_swap);
                                        target_swap.copy(inventory_temp);
                            }
                            else{
                                        changeGearScore(getChangeGearScoreAfterReplace(target_swap.getGearScore(),target_swap_two.getGearScore()));
                                        target_swap.copy(target_swap_two);
                                        ((Card_Hand)target_swap).durability_text.setVisibility(View.VISIBLE);
                                        ((Card_Hand)target_swap).durability_image.setVisibility(View.VISIBLE);
                                        target_swap_two.setVisibility(View.GONE);
                                        loot_count--;
                                        tryContinue();
                            }
                            target_swap.setVisibility(View.VISIBLE);
                            Log.d("targetReset", "loot swap");
                            targetReset();
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
                        useFood();
                        return true;
                    }
                    if (target_swap.getType() == Inventory_Type.SPELL) {
                        useSpell();
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

    View.OnClickListener setTargetListener = setTarget();

    View.OnClickListener setTarget() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsAnimate) {
                    mIsAnimate = true;
                    Log.d("mIsAnimate", String.valueOf(mIsAnimate));
                    mCardTableTarget = (Card_Table) v;
                    if(mCardTableTarget.getType()== CardTableType.MOB){
                        mCardTableTarget.bringToFront();
                        mCardTableTarget.getTargetAnimation().start();
                        mCardTableTarget.setOnClickListener(mDamagListener);
                    }
                    if (mCardTableTarget.getType()==CardTableType.VENDOR){
                        card_6_animation_click_vendor.start();
                        if(mCardTableTarget.getSubType()== CardTableSubType.TRADER){
                            mShadow.bringToFront();
                            mCardTableTarget.bringToFront();
                            Picasso.with(getBaseContext()).load(R.drawable.navik_torgovca).into(mTradeSkillImage);
                            mTradeSkillImage.setOnClickListener(mOnClickVendorSkill);
                            for (byte i = 0; i<loot_max_count;i++){
                                mTradeItem[i].change(mStats, db_open_helper,random, mGearScore);
                                mTradeItem[i].open();
                                mTradeItem[i].setVisibility(View.VISIBLE);
                                mTradeCost[i].setText(String.format("%d", mTradeItem[i].getCost()));
                                mTradeCost[i].setVisibility(View.VISIBLE);
                                mTradeCostImage[i].setVisibility(View.VISIBLE);
                            }
                            mTradeSkill.setVisibility(View.VISIBLE);
                            mTradeZone.setVisibility(View.VISIBLE);
                            mTable.setOnDragListener(null);
                        }
                        if (mCardTableTarget.getSubType()== CardTableSubType.BLACKSMITH){
                            mShadow.bringToFront();
                            mCardTableTarget.bringToFront();
                            Picasso.with(getBaseContext()).load(R.drawable.navik_kuznecaa).into(mTradeSkillImage);
                            mTradeSkillImage.setOnClickListener(null);
                            for (byte i = 0; i<loot_max_count;i++){
                                mTradeItem[i].change(mStats, db_open_helper,random, mGearScore, random.nextInt(2));
                                mTradeItem[i].open();
                                mTradeItem[i].setVisibility(View.VISIBLE);
                                mTradeItem[i].setDurability(mTradeItem[i].getDurabilityMax());
                                mTradeCost[i].setText(String.format("%d", mTradeItem[i].getCost()));
                                mTradeCost[i].setVisibility(View.VISIBLE);
                                mTradeCostImage[i].setVisibility(View.VISIBLE);
                            }
                            mTradeSkill.setVisibility(View.VISIBLE);
                            mTradeZone.setVisibility(View.VISIBLE);
                            mTable.setOnDragListener(null);
                        }
                        if (mCardTableTarget.getSubType()== CardTableSubType.INNKEEPER){
                            mShadow.bringToFront();
                            mCardTableTarget.bringToFront();
                            Picasso.with(getBaseContext()).load(R.drawable.navik_traktirshika).into(mTradeSkillImage);
                            mTradeSkillImage.setOnClickListener(mOnClickInnkeeperSkill);
                            for (byte i = 0; i<loot_max_count;i++){
                                mTradeItem[i].change(mStats, db_open_helper,random, mGearScore, Inventory_Type.FOOD);
                                mTradeItem[i].open();
                                mTradeItem[i].setVisibility(View.VISIBLE);
                                mTradeCost[i].setText(String.format("%d", mTradeItem[i].getCost()));
                                mTradeCost[i].setVisibility(View.VISIBLE);
                                mTradeCostImage[i].setVisibility(View.VISIBLE);
                            }
                            mTradeSkill.setVisibility(View.VISIBLE);
                            mTradeZone.setVisibility(View.VISIBLE);
                            mTable.setOnDragListener(null);
                        }
                    }
                    if (mCardTableTarget.getType()== CardTableType.HALT){
                        mShadow.bringToFront();
                        mCardTableTarget.bringToFront();
                        mCardTableTarget.getTargetAnimation().start();
                        changeHP(mHpMax);
                        mCardTableTarget.getChangeAnimation().start();
                    }
                    if (mCardTableTarget.getType()== CardTableType.CHEST){
                        mShadow.bringToFront();
                        mCardTableTarget.bringToFront();
                        mCardTableTarget.getTargetAnimation().start();
                        mCardTableTarget.getCloseAnimation().start();
/*
                        moneyChange(mCardTableTarget.Get_Money());
*/
                    }
                }
            }
        };
    }

    View.OnClickListener mOnClickBuyCardListener = mOnClickBuyCardListener();
    View.OnClickListener mOnClickBuyCardListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (money>=((Card_Inventory)v).getCost()&& mInventoryItemCount < INVENTORYMAXCOUNT){
                    trade_target = (Card_Inventory)v;
                    mDialogWindow.openDialog(
                            String.format("Купить карту за %d золотых?", trade_target.getCost()),
                            mCardTradeBuyClickYes
                    );
                }
                else{
                    if (!(mInventoryItemCount < INVENTORYMAXCOUNT)){
                        mDialogWindow.openInfo("Нет места в инвентаре.");
                    }
                    else{
                        mDialogWindow.openInfo("Недостаточно золота.");
                    }
                }
            }
        };
    }

    View.OnClickListener mInventoryOnClickSwap = mInventoryOnClickSwap();

    View.OnClickListener mInventoryOnClickSwap() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_first_click) {
                    target_swap = (Card_Inventory) v;
                    if (target_swap.mSlotType == Slot_Type.HAND) {
                        target_on_animation = ObjectAnimator.ofFloat(target_swap, View.TRANSLATION_Y, 0f, -hand_animation_delta);
                        target_off_animation = ObjectAnimator.ofFloat(target_swap, View.TRANSLATION_Y, -hand_animation_delta, 0f);
                    }
                    if (target_swap.mSlotType == Slot_Type.LOOT) {
                        target_on_animation = ObjectAnimator.ofFloat(target_swap, View.TRANSLATION_Y, 0f, -loot_animation_delta);
                        target_off_animation = ObjectAnimator.ofFloat(target_swap, View.TRANSLATION_Y, -loot_animation_delta, 0f);

                    }
                    if (target_swap.mSlotType == Slot_Type.INVENTORY) {
                        target_on_animation = ObjectAnimator.ofFloat(target_swap, View.TRANSLATION_Y, 0f, -inventory_animation_delta);
                        target_off_animation = ObjectAnimator.ofFloat(target_swap, View.TRANSLATION_Y, -inventory_animation_delta, 0f);
                    }

                    Log.d("target on", String.valueOf(target_swap.mNameText.getText()));
                    is_first_click = false;

                    target_off_animation.addListener(on_target_off_animation);
                    target_on_animation.addListener(target_on_animation_end);
                    target_on_animation.start();

                    if (mTradeSkill.getVisibility()==View.VISIBLE && mCardTableTarget.getSubType()== CardTableSubType.BLACKSMITH&&
                            (target_swap.getType()==Inventory_Type.SHIELD||target_swap.getType()==Inventory_Type.WEAPON)){
                        mTradeSkillImage.setOnClickListener(mOnClickBlacksmithSkill);
                    }
                }
                else {
                    Card_Inventory target_swap_two = (Card_Inventory) v;
                    if (!target_swap.equals(target_swap_two) ) {
                        if (target_swap_two.mSlotType !=target_swap.mSlotType) {
                            if (target_swap_two.mSlotType == Slot_Type.HAND) {
                                Card_Hand targetSwapTwoHand = (Card_Hand) v;
                                if (target_swap.getType() == Inventory_Type.SHIELD || target_swap.getType() ==
                                        Inventory_Type.WEAPON) {
                                    if(targetSwapTwoHand.durability_text.getVisibility()==View.VISIBLE){
                                        inventory_temp.Copy(targetSwapTwoHand);
                                        targetSwapTwoHand.copy(target_swap);
                                        target_swap.copy(inventory_temp);
                                    }
                                    else{
                                        changeGearScore(getChangeGearScoreAfterReplace(0,null));
                                        targetSwapTwoHand.copy(target_swap);
                                        targetSwapTwoHand.durability_text.setVisibility(View.VISIBLE);
                                        targetSwapTwoHand.durability_image.setVisibility(View.VISIBLE);
                                        mInventoryItemCount--;
                                        inventorySort();
                                        lootGet();
                                    }
                                    Log.d("targetReset", "hand swap click");
                                    targetReset();
                                }
                            }
                            if (target_swap_two.mSlotType == Slot_Type.LOOT) {
                                if (target_swap.mSlotType == Slot_Type.INVENTORY) {
                                    if ((target_swap_two.getType() == Inventory_Type.WEAPON ||
                                            target_swap_two.getType() == Inventory_Type.SHIELD) &&
                                            (target_swap.getType() == Inventory_Type.WEAPON ||
                                                    target_swap.getType() == Inventory_Type.SHIELD))
                                    {
                                        changeGearScore(getChangeGearScoreAfterReplace(target_swap.getGearScore(),target_swap_two.getGearScore()));
                                    }
                                    else{
                                        if (target_swap.getType() == Inventory_Type.WEAPON ||
                                                target_swap.getType() == Inventory_Type.SHIELD)
                                        {
                                            changeGearScore(getChangeGearScoreAfterReplace(target_swap.getGearScore(),null));
                                            changeGearScore(target_swap_two.getGearScore());
                                        }
                                        else{
                                            if (target_swap_two.getType() == Inventory_Type.WEAPON ||
                                                    target_swap_two.getType() == Inventory_Type.SHIELD)
                                            {
                                                changeGearScore(getChangeGearScoreAfterReplace(null,target_swap_two.getGearScore()));
                                                changeGearScore(-target_swap.getGearScore());
                                            }
                                            else{
                                                changeGearScore(target_swap_two.getGearScore()-target_swap.getGearScore());
                                            }
                                        }
                                    }
                                    inventory_temp.Copy(target_swap_two);
                                    target_swap_two.copy(target_swap);
                                    target_swap.copy(inventory_temp);
                                    Log.d("targetReset", "loot swap click");
                                    targetReset();
                                }
                                else {
                                    if (target_swap_two.getType() == Inventory_Type.WEAPON ||
                                            target_swap_two.getType() == Inventory_Type.SHIELD)
                                    {
                                        if(((Card_Hand)target_swap).durability_text.getVisibility()==View.VISIBLE){
                                            changeGearScore(getChangeGearScoreAfterReplace(target_swap.getGearScore(),target_swap_two.getGearScore()));
                                            inventory_temp.Copy(target_swap_two);
                                            target_swap_two.copy(target_swap);
                                            target_swap.copy(inventory_temp);
                                        }
                                        else{
                                            changeGearScore(getChangeGearScoreAfterReplace(target_swap.getGearScore(),target_swap_two.getGearScore()));
                                            target_swap.copy(target_swap_two);
                                            ((Card_Hand)target_swap).durability_text.setVisibility(View.VISIBLE);
                                            ((Card_Hand)target_swap).durability_image.setVisibility(View.VISIBLE);
                                            target_swap_two.setVisibility(View.GONE);
                                            loot_count--;
                                            tryContinue();
                                        }
                                        Log.d("targetReset", "loot swap click");
                                        targetReset();
                                    }
                                }
                            }
                        }
                    }
                    else {
                        Log.d("targetReset", "target off");
                        targetReset();
                    }
                }
            }
        };
    }

    private void deleteOnClickListenerBeforeAnomationTargetReset() {
        for (byte i = 0; i < INVENTORYMAXCOUNT; i++) {
            mInventory[i].setOnClickListener(null);
        }
        for (int i = 0; i < loot_max_count; i++) {
            loot[i].setOnClickListener(null);
        }
        mHandOne.setOnClickListener(null);
        mHandTwo.setOnClickListener(null);
    }

    private void targetReset() {
        if (target_swap!=null){
            target_off_animation.start();
            Log.d("target off", (String) target_swap.mNameText.getText());
        }
        mTradeSkillImage.setOnClickListener(null);
        target_swap = null;
        is_first_click = true;
    }

    private void changeGearScore(int delta) {
        mGearScore += delta;
        mGearScoreText.setText(String.format("%d", mGearScore));
    }

    View.OnClickListener mDamagListener = mDamagListener();

    View.OnClickListener mDamagListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int damage = ((mHandOne.getType() == Inventory_Type.WEAPON) ?
                        mHandOne.getValueOne() : 0) + ((mHandTwo.getType() == Inventory_Type.WEAPON) ?
                        mHandTwo.getValueOne() : 0);

                mobHPChange(-damage);

                HP_Calculation();

                if (hp < 1) {
                    gameReset();
                    return;
                }

                if(mHandOne.durability_text.getVisibility()==View.VISIBLE){
                    mHandOne.setDurability(mHandOne.getDurability()-1);
                    if (mHandOne.getDurability()<1){
                        changeGearScore(getChangeGearScoreAfterReplace(mHandOne.getGearScore(),0));
                        setHand(mHandOne);
                    }
                    else{
                        mHandOne.Set_Durability_Text(mHandOne.getDurability());
                    }
                }

                if(mHandTwo.durability_text.getVisibility()==View.VISIBLE){
                    mHandTwo.setDurability(mHandTwo.getDurability()-1);
                    if (mHandTwo.getDurability()<1){
                        changeGearScore(getChangeGearScoreAfterReplace(mHandTwo.getGearScore(),0));
                        setHand(mHandTwo);
                    }
                    else{
                        mHandTwo.Set_Durability_Text(mHandTwo.getDurability());
                    }
                }

                if (mCardTableTarget.getValueTwo() < 1) {
                    mobDead();
                }
            }

            private void HP_Calculation() {
                int mob_damage = mCardTableTarget.getValueOne();
                if(mob_damage>0&& mHandOne.getType() == Inventory_Type.SHIELD){
                    if (mHandOne.getValueOne() < mob_damage){
                        mob_damage -= mHandOne.getValueOne();
                    }
                    else{
                        mob_damage = 0;
                    }
                }
                if(mob_damage>0&& mHandTwo.getType() == Inventory_Type.SHIELD){
                    if (mHandTwo.getValueOne() < mob_damage){
                        mob_damage -= mHandTwo.getValueOne();
                    }
                    else{
                        mob_damage = 0;
                    }
                }
                if (mob_damage>0){
                    changeHP(-mob_damage);
                }
            }
        };
    }

    private void mobHPChange(int delta) {
        mCardTableTarget.setValueTwo(mCardTableTarget.getValueTwo()+delta);
        mCardTableTarget.setValueTwoText(mCardTableTarget.getValueTwo());
    }

    private void mobDead() {
        mCardTableTarget.setOnClickListener(null);
        mCardTableTarget.setValueTwoText(0);
        moneyChange(mCardTableTarget.Get_Money());
        mStats.addExperience(mCardTableTarget.getExperience());
        mCardTableTarget.getCloseAnimation().start();
    }

    private void lootGet() {

        while (((loot_count > 0) && (mInventoryItemCount < INVENTORYMAXCOUNT))) {
            if(loot[loot_id].getVisibility()==View.VISIBLE) {
                mInventory[mInventoryItemCount].copy(loot[loot_id]);
                loot[loot_id].setVisibility(View.GONE);
                mInventory[mInventoryItemCount].setVisibility(View.VISIBLE);
                mInventoryItemCount++;
                if (loot[loot_id].getType()==Inventory_Type.WEAPON ||
                        loot[loot_id].getType()==Inventory_Type.SHIELD)
                {
                    changeGearScore(getChangeGearScoreAfterReplace(null,loot[loot_id].getGearScore()));
                }
                else{
                    changeGearScore(loot[loot_id].getGearScore());
                }
                loot_count--;
            }
            loot_id++;
        }

        tryContinue();
    }

    private void tryContinue() {
        if (loot_count < 1 && is_loot_enable) {
            onClickButtonContinue(null);
        }
    }

    View.OnClickListener mCardTradeBuyClickYes = mCardTradeBuyClickYes();
    View.OnClickListener mCardTradeBuyClickYes() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogWindow.close();
                trade_target.setVisibility(View.INVISIBLE);
                mTradeCost[trade_target.mSlotId].setVisibility(View.INVISIBLE);
                mTradeCostImage[trade_target.mSlotId].setVisibility(View.INVISIBLE);
                mInventory[mInventoryItemCount].copy(trade_target);
                mInventory[mInventoryItemCount].setVisibility(View.VISIBLE);
                mInventoryItemCount++;
                changeGearScore(trade_target.getGearScore());
                moneyChange(-trade_target.getCost());
            }
        };
    }

    View.OnClickListener mCardTradeSellClickYes = mCardTradeSellClickYes();
    View.OnClickListener mCardTradeSellClickYes() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogWindow.close();
                target_swap.setVisibility(View.INVISIBLE);
                changeGearScore(-target_swap.getGearScore());
                mInventoryItemCount--;
                inventorySort();
                moneyChange(target_swap.getCost());
                targetReset();
            }
        };
    }

    View.OnDragListener mOnSell = mOnSell();
    View.OnDragListener mOnSell() {
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
                    if (target_swap.getIdDrawable()!=R.drawable.kulak_levo||
                            target_swap.getIdDrawable()!=R.drawable.kulak_pravo)
                    {
                        mDialogWindow.openDialog(
                                String.format("Продать карту за %d золотых?", target_swap.getCost()),
                                mCardTradeSellClickYes
                        );
                    }
                }
                return true;
            }
        };
    }

    byte mCostVendorSkill = 1;
    View.OnClickListener mOnClickVendorSkill = mOnClickVendorSkill();
    View.OnClickListener mOnClickVendorSkill() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (money>= mCostVendorSkill){
                    mDialogWindow.openDialog(
                            String.format("Обновить ассортимент торговца за %d золотых?", mCostVendorSkill),
                            mOnClickVendorSkillYes
                    );
                }
                else{
                    mDialogWindow.openInfo(
                            String.format("Недостаточно %d золота.", mCostVendorSkill -money)
                    );
                }
            }
        };
    }
    View.OnClickListener mOnClickVendorSkillYes = mOnClickVendorSkillYes();
    View.OnClickListener mOnClickVendorSkillYes() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogWindow.close();
                moneyChange(-mCostVendorSkill);
                for (byte i = 0; i<loot_max_count;i++){
                    mTradeItem[i].change(mStats, db_open_helper,random, mGearScore);
                    mTradeItem[i].open();
                    mTradeItem[i].setVisibility(View.VISIBLE);
                    mTradeCost[i].setText(String.format("%d", mTradeItem[i].getCost()));
                    mTradeCost[i].setVisibility(View.VISIBLE);
                    mTradeCostImage[i].setVisibility(View.VISIBLE);
                }
            }
        };
    }

    View.OnClickListener mOnClickBlacksmithSkill = mOnClickBlacksmithSkill();
    View.OnClickListener mOnClickBlacksmithSkill() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (money>= mCostVendorSkill){
                    mDialogWindow.openDialog(
                            String.format("Починитm предмет за %d золотых?", mCostVendorSkill),
                            mOnClickBlacksmithSkillYes
                    );
                }
                else{
                    mDialogWindow.openInfo(
                            String.format("Недостаточно %d золота.", mCostVendorSkill -money)
                    );
                }
            }
        };
    }
    View.OnClickListener mOnClickBlacksmithSkillYes = mOnClickBlacksmithSkillYes();
    View.OnClickListener mOnClickBlacksmithSkillYes() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogWindow.close();
                moneyChange(-mCostVendorSkill);
                target_swap.setDurability(10);
                if (target_swap.mSlotType ==Slot_Type.HAND){
                    ((Card_Hand)target_swap).Set_Durability_Text(10);
                }
                targetReset();
            }
        };
    }

    View.OnClickListener mOnClickInnkeeperSkill = mOnClickInnkeeperSkill();
    View.OnClickListener mOnClickInnkeeperSkill() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (money>= mCostVendorSkill){
                    mDialogWindow.openDialog(
                            String.format("Отдохнуть и восстановить здоровье за %d золотых?", mCostVendorSkill),
                            mOnClickInnkeeperSkillYes
                    );
                }
                else{
                    mDialogWindow.openInfo(
                            String.format("Недостаточно %d золота.", mCostVendorSkill -money)
                    );
                }
            }
        };
    }
    View.OnClickListener mOnClickInnkeeperSkillYes = mOnClickInnkeeperSkillYes();
    View.OnClickListener mOnClickInnkeeperSkillYes() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogWindow.close();
                moneyChange(-mCostVendorSkill);
                changeHP(mHpMax);
                mTradeSkillImage.setOnClickListener(null);
            }
        };
    }

    void moneyChange(int delta){
        money += delta;
        money_text.setText(String.format("%d", money));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStats = new Stats(db_open_helper);
        mStats.setLayout(findViewById(R.id.stats));
        mStats.setDamageButton(findViewById(R.id.statsDamageButton));
        mStats.setDefenceButton(findViewById(R.id.statsDefenceButton));
        mStats.setHPButton(findViewById(R.id.statsHPButton));
        mStats.setLevelAndExperienceText(findViewById(R.id.statsExperience));
        mStats.setDamageBonusText(findViewById(R.id.statsDamageText));
        mStats.setDefenceBonusText(findViewById(R.id.statsDefenceText));
        mStats.setHPBonusText(findViewById(R.id.statsHPText));
        mStats.setPointsText(findViewById(R.id.statsPointsText));
        mStats.setResetButton(findViewById(R.id.statsResetButton));

        mGearScoreText = findViewById(R.id.gearScore);

        mTable = findViewById(R.id.table);
        mTable.setOnDragListener(mTableOnDropListener);

        mTradeSkill = findViewById(R.id.trade_skill);
        mTradeZone = findViewById(R.id.trade_zone);
        mTradeZone.setOnDragListener(mOnSell);

        mDialogWindow.setLayout(findViewById(R.id.trade_window));
        mDialogWindow.setText(findViewById(R.id.trade_window_text));
        mDialogWindow.setButtonOk(findViewById(R.id.trade_window_ok));
        mDialogWindow.setButtonYes(findViewById(R.id.trade_window_yes));
        mDialogWindow.setButtonNo(findViewById(R.id.trade_window_no));

        mTradeSkillImage = findViewById(R.id.trade_skill_image);
        mTradeSkillImage.setOnClickListener(mOnClickVendorSkill);

        mShadow = findViewById(R.id.shadow);

        button_start = findViewById(R.id.button_start_layout);
        button_continue = findViewById(R.id.button_continue_layout);
        button_continue.setVisibility(View.GONE);

        money_text = findViewById(R.id.money_text);
        hp_text = findViewById(R.id.hp_text);

        hp_view = findViewById(R.id.hp);

        hp_bar = findViewById(R.id.hp_bar);

        hp_bar_drawable = hp_bar.getDrawable();

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

        mCardsTable[0].mNameText = findViewById(R.id.card_name_1);
        mCardsTable[1].mNameText = findViewById(R.id.card_name_2);
        mCardsTable[2].mNameText = findViewById(R.id.card_name_3);
        mCardsTable[3].mNameText = findViewById(R.id.card_name_4);
        mCardsTable[4].mNameText = findViewById(R.id.card_name_6);
        mCardsTable[5].mNameText = findViewById(R.id.card_name_7);
        mCardsTable[6].mNameText = findViewById(R.id.card_name_8);
        mCardsTable[7].mNameText = findViewById(R.id.card_name_9);

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

        loot[0].mSlotType = Slot_Type.LOOT;
        loot[1].mSlotType = Slot_Type.LOOT;
        loot[2].mSlotType = Slot_Type.LOOT;

        loot[0].imageView = findViewById(R.id.card_loot_0_view);
        loot[1].imageView = findViewById(R.id.card_loot_1_view);
        loot[2].imageView = findViewById(R.id.card_loot_2_view);

        loot[0].mNameText = findViewById(R.id.card_loot_0_name);
        loot[1].mNameText = findViewById(R.id.card_loot_1_name);
        loot[2].mNameText = findViewById(R.id.card_loot_2_name);

        loot[0].value_one_text = findViewById(R.id.card_loot_0_value);
        loot[1].value_one_text = findViewById(R.id.card_loot_1_value);
        loot[2].value_one_text = findViewById(R.id.card_loot_2_value);

        mTradeItem[0] = findViewById(R.id.card_trade_0);
        mTradeItem[1] = findViewById(R.id.card_trade_1);
        mTradeItem[2] = findViewById(R.id.card_trade_2);

        mTradeItem[0].imageView = findViewById(R.id.card_trade_0_view);
        mTradeItem[1].imageView = findViewById(R.id.card_trade_1_view);
        mTradeItem[2].imageView = findViewById(R.id.card_trade_2_view);

        mTradeItem[0].mNameText = findViewById(R.id.card_trade_0_name);
        mTradeItem[1].mNameText = findViewById(R.id.card_trade_1_name);
        mTradeItem[2].mNameText = findViewById(R.id.card_trade_2_name);

        mTradeItem[0].value_one_text = findViewById(R.id.card_trade_0_value);
        mTradeItem[1].value_one_text = findViewById(R.id.card_trade_1_value);
        mTradeItem[2].value_one_text = findViewById(R.id.card_trade_2_value);

        mTradeItem[0].mSlotId = 0;
        mTradeItem[1].mSlotId = 1;
        mTradeItem[2].mSlotId = 2;

        mTradeItem[0].setOnClickListener(mOnClickBuyCardListener);
        mTradeItem[1].setOnClickListener(mOnClickBuyCardListener);
        mTradeItem[2].setOnClickListener(mOnClickBuyCardListener);

        mTradeCost[0] = findViewById(R.id.cost_0_text);
        mTradeCost[1] = findViewById(R.id.cost_1_text);
        mTradeCost[2] = findViewById(R.id.cost_2_text);
        mTradeCostImage[0] = findViewById(R.id.cost_0_view);
        mTradeCostImage[1] = findViewById(R.id.cost_1_view);
        mTradeCostImage[2] = findViewById(R.id.cost_2_view);

        mHandOne = findViewById(R.id.card_hand_left);
        mHandTwo = findViewById(R.id.card_hand_right);
        mHandOne.mSlotType = Slot_Type.HAND;
        mHandTwo.mSlotType = Slot_Type.HAND;
        mHandOne.imageView = findViewById(R.id.card_hand_leftview);
        mHandTwo.imageView = findViewById(R.id.card_hand_right_view);
        mHandOne.mNameText = findViewById(R.id.card_hand_left_name);
        mHandTwo.mNameText = findViewById(R.id.card_hand_right_name);
        mHandOne.value_one_text = findViewById(R.id.card_hand_left_value);
        mHandTwo.value_one_text = findViewById(R.id.card_hand_right_value);
        mHandOne.setOnDragListener(mHandOnDropListener);
        mHandTwo.setOnDragListener(mHandOnDropListener);
        mHandOne.durability_text = findViewById(R.id.hand_one_durability_text);
        mHandTwo.durability_text = findViewById(R.id.hand_two_durability_text);
        mHandOne.durability_image = findViewById(R.id.hand_one_durability_image);
        mHandTwo.durability_image = findViewById(R.id.hand_two_durability_image);

        mInventory[0] = findViewById(R.id.card_inventory_0);
        mInventory[1] = findViewById(R.id.card_inventory_1);
        mInventory[2] = findViewById(R.id.card_inventory_2);
        mInventory[3] = findViewById(R.id.card_inventory_3);

        mInventory[0].mSlotType = Slot_Type.INVENTORY;
        mInventory[1].mSlotType = Slot_Type.INVENTORY;
        mInventory[2].mSlotType = Slot_Type.INVENTORY;
        mInventory[3].mSlotType = Slot_Type.INVENTORY;

        mInventory[0].imageView = findViewById(R.id.card_inventory_0_view);
        mInventory[1].imageView = findViewById(R.id.card_inventory_1_view);
        mInventory[2].imageView = findViewById(R.id.card_inventory_2_view);
        mInventory[3].imageView = findViewById(R.id.card_inventory_3_view);

        mInventory[0].mNameText = findViewById(R.id.card_inventory_0_name);
        mInventory[1].mNameText = findViewById(R.id.card_inventory_1_name);
        mInventory[2].mNameText = findViewById(R.id.card_inventory_2_name);
        mInventory[3].mNameText = findViewById(R.id.card_inventory_3_name);

        mInventory[0].value_one_text = findViewById(R.id.card_inventory_0_value);
        mInventory[1].value_one_text = findViewById(R.id.card_inventory_1_value);
        mInventory[2].value_one_text = findViewById(R.id.card_inventory_2_value);
        mInventory[3].value_one_text = findViewById(R.id.card_inventory_3_value);

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

        mTradeItem[0].TEST_GearScoreText = findViewById(R.id.cardTrade0GearScore);
        mTradeItem[1].TEST_GearScoreText = findViewById(R.id.cardTrade1GearScore);
        mTradeItem[2].TEST_GearScoreText = findViewById(R.id.cardTrade2GearScore);

        mHandOne.TEST_GearScoreText = findViewById(R.id.cardHandLeftGearScore);
        mHandTwo.TEST_GearScoreText = findViewById(R.id.cardHandRightGearScore);

        mInventory[0].TEST_GearScoreText = findViewById(R.id.cardInventory0GearScore);
        mInventory[1].TEST_GearScoreText = findViewById(R.id.cardInventory1GearScore);
        mInventory[2].TEST_GearScoreText = findViewById(R.id.cardInventory2GearScore);
        mInventory[3].TEST_GearScoreText = findViewById(R.id.cardInventory3GearScore);

        loot[0].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.loot0GearScoreMob);
        loot[1].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.loot1GearScoreMob);
        loot[2].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.loot2GearScoreMob);

        mTradeItem[0].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardTrade0GearScoreMob);
        mTradeItem[1].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardTrade1GearScoreMob);
        mTradeItem[2].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardTrade2GearScoreMob);

        mHandOne.TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardHandLeftGearScoreMob);
        mHandTwo.TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardHandRightGearScoreMob);

        mInventory[0].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardInventory0GearScoreMob);
        mInventory[1].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardInventory1GearScoreMob);
        mInventory[2].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardInventory2GearScoreMob);
        mInventory[3].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardInventory3GearScoreMob);

        gameLoad();

    }

    private void gameLoad() {

        for (byte i = 0; i < 8; i++) {
            mCardsTable[i].setIdInArray(i);
            mCardsTable[i].mNameText.setVisibility(View.INVISIBLE);
            mCardsTable[i].value_two_text.setVisibility(View.INVISIBLE);
            mCardsTable[i].value_one_text.setVisibility(View.INVISIBLE);
            mCardsTable[i].imageView.setImageResource(card_back);
            mCardsTable[i].setIdDrawable(card_back);
            mCardsTable[i].imageView.setVisibility(View.INVISIBLE);
        }
        card_center.setVisibility(View.INVISIBLE);

        loot[1].setVisibility(View.GONE);
        loot[2].setVisibility(View.GONE);

        mInventoryItemCount = 0;

        mInventory[0].change(mStats, db_open_helper, 1);
        mInventory[0].open();
        mHandOne.hand_drawable = R.drawable.kulak_levo;
        mHandTwo.hand_drawable = R.drawable.kulak_pravo;
        mHandOne.setOnClickListener(null);
        mHandTwo.setOnClickListener(null);
        setHand(mHandOne);
        setHand(mHandTwo);

        mInventory[0].mSlotId = 0;
        for (byte i = 1; i < INVENTORYMAXCOUNT; i++) {
            mInventory[i].setVisibility(View.GONE);
            mInventory[i].mSlotId = i;
        }

        mHpMax = mHpMaxDefault + mStats.getHPBonus();
        hp = mHpMax;
        hp_text.setText(String.valueOf(hp));
        hp_bar_drawable.setLevel(10000 * hp / mHpMax);

        mIsAnimate = true;
        Log.d("mIsAnimate", String.valueOf(mIsAnimate));

        mCardsTable[1].setOnClickListener(setTargetListener);
        mCardsTable[3].setOnClickListener(setTargetListener);
        mCardsTable[4].setOnClickListener(setTargetListener);
        mCardsTable[6].setOnClickListener(setTargetListener);

        money_text.setText(String.valueOf(money_bank));

        mShadow.setVisibility(View.VISIBLE);
        mShadow.setAlpha(0f);
    }

    private void setHand(Card_Hand hand) {
        hand.setGearScore(0);
        hand.TEST_MOB_GEARSCORE_TEXT.setText(0+"");
        hand.setValueOne(1+mStats.getDamageBonus());
        hand.mNameText.setText("Кулак");
        hand.mIdDrawable = hand.hand_drawable;
        hand.setType(Inventory_Type.WEAPON);
        hand.setValueOneText(hand.getValueOne());
        Picasso.with(getBaseContext()).load(hand.hand_drawable).into(hand.imageView);
/*
        mHandOne.imageView.setImageResource(R.drawable.fist);
*/
        hand.durability_text.setVisibility(View.INVISIBLE);
        hand.durability_image.setVisibility(View.INVISIBLE);
    }

    private void gameReset() {

        money_bank += money;
        money = 0;

        gameLoad();

        columnCenterCardTableReset.start();
        card_reset_row_center.start();

        button_start.setVisibility(View.VISIBLE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

/*
        data_base = db_open_helper.getWritableDatabase();
        data_base.close();

        setTextSize();

        setAnimators();
*/
    }

    private void setTextSize() {
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
            mCardsTable[i].mNameText.setTextSize(COMPLEX_UNIT_PX, card_name_text_size);
            mCardsTable[i].value_one_text.setTextSize(COMPLEX_UNIT_PX, card_hp_and_damage_text_size);
            mCardsTable[i].value_two_text.setTextSize(COMPLEX_UNIT_PX, card_hp_and_damage_text_size);
        }

        float card_inventory_value_one_text_size = (float) (Math.sqrt(Math.pow(mInventory[0].imageView.getWidth(), 2.0) +
                Math.pow(mInventory[0].imageView.getHeight(), 2.0)) * card_hp_and_damage_text_size_constant);
        float card_inventory_name_text_size = (float) (Math.sqrt(Math.pow(mInventory[0].imageView.getWidth(), 2.0) +
                Math.pow(mInventory[0].imageView.getHeight(), 2.0)) * card_name_text_size_constant);
        for (int i = 0; i < INVENTORYMAXCOUNT; i++) {
            mInventory[i].mNameText.setTextSize(COMPLEX_UNIT_PX, card_inventory_name_text_size);
            mInventory[i].value_one_text.setTextSize(COMPLEX_UNIT_PX, card_inventory_value_one_text_size);
        }

        float card_loot_value_one_text_size = (float) (Math.sqrt(Math.pow(loot[0].imageView.getWidth(), 2.0) +
                Math.pow(loot[0].imageView.getHeight(), 2.0)) * card_hp_and_damage_text_size_constant);
        float card_loot_name_text_size = (float) (Math.sqrt(Math.pow(loot[0].imageView.getWidth(), 2.0) +
                Math.pow(loot[0].imageView.getHeight(), 2.0)) * card_name_text_size_constant);
        for (int i = 0; i < loot_max_count; i++) {
            loot[i].mNameText.setTextSize(COMPLEX_UNIT_PX, card_loot_name_text_size);
            loot[i].value_one_text.setTextSize(COMPLEX_UNIT_PX, card_loot_value_one_text_size);
        }
        Log.d(""+card_loot_value_one_text_size, ""+card_loot_name_text_size);
        loot[0].setVisibility(View.GONE);

        float card_hand_value_one_text_size = (float) (Math.sqrt(Math.pow(mHandOne.imageView.getWidth(), 2.0) +
                Math.pow(mHandOne.imageView.getHeight(), 2.0)) * card_hp_and_damage_text_size_constant);
        float card_hand_name_text_size = (float) (Math.sqrt(Math.pow(mHandOne.imageView.getWidth(), 2.0) +
                Math.pow(mHandOne.imageView.getHeight(), 2.0)) * card_name_text_size_constant);
            mHandOne.mNameText.setTextSize(COMPLEX_UNIT_PX, card_hand_name_text_size);
            mHandOne.value_one_text.setTextSize(COMPLEX_UNIT_PX, card_hand_value_one_text_size);
        mHandTwo.mNameText.setTextSize(COMPLEX_UNIT_PX, card_hand_name_text_size);
        mHandTwo.value_one_text.setTextSize(COMPLEX_UNIT_PX, card_hand_value_one_text_size);


        hp_text_size = (float) (Math.sqrt(Math.pow(hp_view.getWidth(), 2.0) +
                Math.pow(hp_view.getHeight(), 2.0)) * hp_text_size_constant);
        hp_text.setTextSize(COMPLEX_UNIT_PX, hp_text_size);
    }

    AnimatorSet columnCenterCardTableReset = new AnimatorSet();
    private void setAnimators() {

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

        mInventory[0].imageView.getLocationOnScreen(card_inventory_coordinates);
        mHandOne.imageView.getLocationOnScreen(card_hand_coordinates);
        loot[0].imageView.getLocationOnScreen(card_loot_coordinates);
        findViewById(R.id.guideline_inventory_top).getLocationOnScreen(guideline_inventory_top_coordinates);

        inventory_animation_delta = card_inventory_coordinates[1] + mInventory[0].imageView.getHeight() -
                displayMetrics.heightPixels;
        hand_animation_delta = card_hand_coordinates[1] + mHandOne.imageView.getHeight() -
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
                mCardsTable[1].open();
                mCardsTable[3].open();
                mCardsTable[4].open();
                mCardsTable[6].open();
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
                mIsAnimate = false;
                Log.d("mIsAnimate", String.valueOf(mIsAnimate));
            }
        };
        openCardTable.addListener(openCardTableListener);

        Animator shadowShow  = ObjectAnimator.ofFloat(
                mShadow,
                View.ALPHA,
                0f,
                .5f
        );

        Animator shadowHide = ObjectAnimator.ofFloat(
                mShadow,
                View.ALPHA,
                .5f,
                0f
        );

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
                mCardTableTarget.close(card_back);
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
/*
                ObjectAnimator.ofFloat(mCardsTable[4], View.TRANSLATION_X, 0f, card_4_coordinates[0]-card_6_coordinates[0]),
                ObjectAnimator.ofFloat(mCardsTable[4], View.TRANSLATION_Y, 0f, -card_distance_between_Y),
*/
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

        setCardAnimatorsReset();
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
                    mCardsTable[3].close(cardCenterBack);
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
                    mCardsTable[2].close(card_back);
                    mCardsTable[4].close(card_back);
                    mCardsTable[7].close(card_back);
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
                mCardsTable[6].close(cardCenterBack);
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
                mCardsTable[0].close(card_back);
                mCardsTable[1].close(card_back);
                mCardsTable[2].close(card_back);
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
                mCardsTable[4].close(cardCenterBack);
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
                mCardsTable[0].close(card_back);
                mCardsTable[3].close(card_back);
                mCardsTable[5].close(card_back);
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
                mCardsTable[1].close(cardCenterBack);
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
                mCardsTable[5].close(card_back);
                mCardsTable[6].close(card_back);
                mCardsTable[7].close(card_back);
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
    private void setCardAnimatorsReset() {
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
    }

    private void loot() {
        mShadow.bringToFront();

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
            loot[i].change(mStats, db_open_helper, random, mCardTableTarget.getGearScore(),typeLoot[i]);
            loot[i].setVisibility(View.VISIBLE);
            loot[i].open();
        }

        is_loot_enable = true;
        loot_id = 0;
        lootGet();

        if (loot_count > 0) {
            button_continue.bringToFront();
            button_continue.setVisibility(View.VISIBLE);
        }
    }

    private void spawn(Card_Table cardTable) {
        if (cardTable.isClose()) {
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

    public void onClickButtonStart(View view) {
        setTextSize();
        setAnimators();

        buttonStart();
    }
    void buttonStart() {
        SQLiteDatabase data_base = db_open_helper.getReadableDatabase();

        String[] column_name = {
                DB_Open_Helper.CVENDOR,
                DB_Open_Helper.CHALT,
                DB_Open_Helper.CWORS,
                DB_Open_Helper.CFood,
                DB_Open_Helper.CSpell,
                DB_Open_Helper.CChest,
                DB_Open_Helper.GSRANGERATE,
                DB_Open_Helper.HP
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
                cursor.getColumnIndexOrThrow(DB_Open_Helper.CVENDOR)
        );
        mChanceHalt = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.CHALT)
        );
        mChanceWeaponOrShield = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.CWORS)
        );
        mChanceFood = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.CFood)
        );
        mChanceSpell = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.CSpell)
        );
        mChanceChest = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.CChest)
        );
        Card_Table.setGearScoreRangeRate(
                cursor.getInt(
                        cursor.getColumnIndexOrThrow(DB_Open_Helper.GSRANGERATE)
                )/100f
        );
        mHpMaxDefault = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.HP)
        );
        cursor.close();

        mHpMax = mHpMaxDefault + mStats.getHPBonus();
        hp = mHpMax;
        hp_text.setText(String.valueOf(hp));
        hp_bar_drawable.setLevel(10000 * hp / mHpMax);

        setHand(mHandOne);
        setHand(mHandTwo);

        button_start.setVisibility(View.GONE);
        mGearScoreWeaponOrShieldInInventory.add(0);
        mGearScoreWeaponOrShieldInInventory.add(0);
        mGearScore = mStats.getGearScoreBonus();
        changeGearScore(getChangeGearScoreAfterReplace(0, mHandOne.getGearScore()));
        changeGearScore(getChangeGearScoreAfterReplace(0, mHandTwo.getGearScore()));
        for (byte i = 0; i < INVENTORYMAXCOUNT; i++) {
            if (mInventory[i].getVisibility()==View.VISIBLE){
                mInventoryItemCount++;
                if (mInventory[i].getType()==Inventory_Type.WEAPON ||
                        mInventory[i].getType()==Inventory_Type.SHIELD)
                {
                    changeGearScore(getChangeGearScoreAfterReplace(null, mInventory[i].getGearScore()));
                }
                else{
                    changeGearScore(mInventory[i].getGearScore());
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            mCardsTable[i].imageView.setVisibility(View.VISIBLE);
        }
        card_center.setVisibility(View.VISIBLE);

        for (byte i = 0; i < INVENTORYMAXCOUNT; i++) {
            mInventory[i].setOnClickListener(mInventoryOnClickSwap);
        }
        mHandOne.setOnClickListener(mInventoryOnClickSwap);
        mHandTwo.setOnClickListener(mInventoryOnClickSwap);

        money_text.setText(String.valueOf(money));

        mIsAnimate = true;
        Log.d("mIsAnimate", String.valueOf(mIsAnimate));
        openCardTable.start();
    }

    public void onClickButtonContinue(View view) {

        is_loot_enable = false;
        for (byte i = 0; i < loot_max_count; i++) {
            loot[i].close(card_back);
            loot[i].setVisibility(View.GONE);
        }
        loot_count = 0;
        if (target_swap != null) {
            targetReset();
        }
        mCardTableTarget.getChangeAnimation().start();
        mCardTableTarget.setOnClickListener(setTargetListener);
        button_continue.setVisibility(View.GONE);
    }

    public void onClickTradeExit(View view){
        mCardTableTarget.getChangeAnimation().start();
        mTradeSkill.setVisibility(View.GONE);
        mTradeZone.setVisibility(View.GONE);
        mTable.setOnDragListener(mTableOnDropListener);
    }

    Stats mStats;
    public void onClickIconStats(View view){
        mStats.setVisibility();
    }
    View.OnClickListener onClickAddDamageYesListener = onClickAddDamageYesListener();
    View.OnClickListener onClickAddDamageYesListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStats.addDamagePoint();
                mDialogWindow.close();
            }
        };
    }
    public void onClickAddDamage(View view){
        mDialogWindow.openDialog(
                "Улучшить атаку?",
                onClickAddDamageYesListener
        );
    }
    View.OnClickListener onClickAddDefenceYesListener = onClickAddDefenceYesListener();
    View.OnClickListener onClickAddDefenceYesListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStats.addDefencePoint();
                mDialogWindow.close();
            }
        };
    }
    public void onClickAddDefence(View view){
        mDialogWindow.openDialog(
                "Улучшить защиту?",
                onClickAddDefenceYesListener
        );
    }
    View.OnClickListener onClickAddHpYesListener = onClickAddHpYesListener();
    View.OnClickListener onClickAddHpYesListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStats.addHPPoint();
                mDialogWindow.close();
            }
        };
    }
    public void onClickAddHP(View view){
        mDialogWindow.openDialog(
                "Улучшить здоровье?",
                onClickAddHpYesListener
        );
    }
    int mStatsResetCost = 1;
    View.OnClickListener onClickStatsResetListener = onClickStatsResetListener();
    View.OnClickListener onClickStatsResetListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStats.reset();
                moneyChange(-mStatsResetCost);
                mDialogWindow.close();
            }
        };
    }
    public void onClickStatsReset(View view){
        if (money>=mStatsResetCost){
            mDialogWindow.openDialog(
                    String.format("Сбросить всё за %d золота.", mStatsResetCost),
                    onClickStatsResetListener
            );
        }
        else{
            mDialogWindow.openInfo(
                    String.format("Недостаточно %d золота.", mStatsResetCost-money)
            );
        }
    }

    public void onClickIconMenu(View view){
        if (findViewById(R.id.balanceMenu).getVisibility()==View.GONE){
            findViewById(R.id.balanceMenu).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.balanceMenu).setVisibility(View.GONE);
        }
    }
    public void onClickBalanceButton(View view){

        EditText baseText = findViewById(R.id.TEST1);
        EditText idText = findViewById(R.id.id);
        EditText paramText = findViewById(R.id.parmetr);
        EditText valueText = findViewById(R.id.value);

       try {
            String base = baseText.getText().toString();
            String id = idText.getText().toString();
            String param = paramText.getText().toString();
            String value = valueText.getText().toString();
            Byte.parseByte(value);
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
                    Byte.parseByte(id);
                    base = DB_Open_Helper.table_mobs;
                    where = DB_Open_Helper.id +"=?";
                    arg = new String[]{id+""};
                    break;
                }
                case c:{
                    Byte.parseByte(id);
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
           baseText.setText("");
           idText.setText("");
           paramText.setText("");
           valueText.setText("");
       }
        catch (Exception e){

        }
    }

    public void onClickIconCollection(View view){
        if (findViewById(R.id.testMenu).getVisibility()==View.GONE){
            findViewById(R.id.testMenu).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.testMenu).setVisibility(View.GONE);
        }
    }
    public void onClickTestButton(View view){

        EditText idslotText = findViewById(R.id.idslot);
        EditText iditemText = findViewById(R.id.iditem);

        try {
            byte idslot = Byte.parseByte(idslotText.getText().toString());
            byte iditem = Byte.parseByte(iditemText.getText().toString());

            if (idslot<4){
                mInventory[idslot].change(mStats, db_open_helper,iditem);
                mInventory[idslot].open();
                mInventory[idslot].setVisibility(View.VISIBLE);
                mInventory[idslot].setDurability(mInventory[idslot].getDurabilityMax());
            }
            else{
                if (idslot==4){
                    mHandOne.change(mStats, db_open_helper,iditem);
                    mHandOne.open();
                    mHandOne.setVisibility(View.VISIBLE);
                    mHandOne.durability_text.setVisibility(View.VISIBLE);
                    mHandOne.durability_image.setVisibility(View.VISIBLE);
                    mHandOne.setDurability(mHandOne.getDurabilityMax());
                    mHandOne.Set_Durability_Text(mHandOne.getDurability());
                }
                else{
                    mHandTwo.change(mStats, db_open_helper,iditem);
                    mHandTwo.open();
                    mHandTwo.setVisibility(View.VISIBLE);
                    mHandTwo.durability_text.setVisibility(View.VISIBLE);
                    mHandTwo.durability_image.setVisibility(View.VISIBLE);
                    mHandTwo.setDurability(mHandTwo.getDurabilityMax());
                    mHandTwo.Set_Durability_Text(mHandTwo.getDurability());
                }
            }
            idslotText.setText("");
            iditemText.setText("");
        }
        catch (Exception e){

        }
    }
}