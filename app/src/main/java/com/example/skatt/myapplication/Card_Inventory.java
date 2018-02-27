package com.example.skatt.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Random;

class Card_Inventory extends Card {

    static String[] column_name = {
            DB_Open_Helper.id,
            DB_Open_Helper.name,
            DB_Open_Helper.value_one,
            DB_Open_Helper.id_image,
            DB_Open_Helper.type,
            DB_Open_Helper.cost,
            DB_Open_Helper.GEARSCORE,
            DB_Open_Helper.MOBGEARSCORE
    };
    int durability;
    byte slot_type;
    byte slot_id;
    int cost;
    int TEST_MOB_GEARSCORE;
    TextView TEST_MOB_GEARSCORE_TEXT;

    public Card_Inventory(Context context) {
        super(context);
    }
    public Card_Inventory(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public Card_Inventory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void Change(DB_Open_Helper db_open_helper, Random random, int gearScoreMob){
        gearScoreMob = 0;
        SQLiteDatabase data_base = db_open_helper.getReadableDatabase();

        int type = random.nextInt(3);

        Cursor cursor = data_base.query(
                DB_Open_Helper.table_inventory,
                column_name,
                DB_Open_Helper.MOBGEARSCORE + "=? AND " + DB_Open_Helper.type + "=?",
                new String[]{gearScoreMob+"",type+""},
                null,
                null,
                null
        );
        setData(random, cursor);
    }
    void Change(DB_Open_Helper db_open_helper, Random random, int gearScoreMob, int type){
        gearScoreMob = 0;
        SQLiteDatabase data_base = db_open_helper.getReadableDatabase();

        Cursor cursor = data_base.query(
                DB_Open_Helper.table_inventory,
                column_name,
                DB_Open_Helper.MOBGEARSCORE + "=? AND " + DB_Open_Helper.type + "=?",
                new String[]{gearScoreMob+"",type+""},
                null,
                null,
                null
        );

        setData(random, cursor);
    }
    void Change(DB_Open_Helper db_open_helper, int id){
        SQLiteDatabase data_base = db_open_helper.getReadableDatabase();

        Cursor cursor = data_base.query(
                DB_Open_Helper.table_inventory,
                column_name,
                DB_Open_Helper.id + "=?",
                new String[]{id+""},
                null,
                null,
                null
        );

        setData(new Random(), cursor);
    }
    private void setData(Random random, Cursor cursor) {
        cursor.moveToPosition(random.nextInt(cursor.getCount()));

        this.mGearScore = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.GEARSCORE)
        );
        this.TEST_GearScoreText.setText(String.format("%d", mGearScore));

        TEST_MOB_GEARSCORE = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.MOBGEARSCORE)
        );
        this.TEST_GearScoreText.setText(String.format("%d", TEST_MOB_GEARSCORE));

        name_text.setText(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(DB_Open_Helper.name)
                )
        );

        value_one = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.value_one)
        );

        this.setValueOneText(value_one);

        id_drawable = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.id_image)
        );

        this.type = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.type)
        );

        this.cost = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.cost)
        );
        durability = random.nextInt(10)+1;
        cursor.close();
    }

    void open() {
        super.open();
        this.value_one_text.setVisibility(View.VISIBLE);
    }

    void Copy(Card_Inventory card){
        super.copy(card);
        this.value_one_text.setVisibility(View.VISIBLE);
        this.durability = card.Get_Durability();
        this.cost = card.Get_Cost();
        this.TEST_MOB_GEARSCORE = card.TEST_MOB_GEARSCORE;
        TEST_MOB_GEARSCORE_TEXT.setText(this.TEST_MOB_GEARSCORE+"");
    }
    void Copy(Card_Inventory_Temp card){

        this.value_one = card.Get_Hp();
        this.id_drawable = card.Get_Id_Drawable();
        Picasso.with(getContext()).load(id_drawable).placeholder(R.color.color_black).into(imageView);
/*
        this.imageView.setImageResource(id_drawable);
*/
        this.name_text.setText(card.name_text);
        this.setValueOneText(card.value_one);
        this.type = card.Get_Type();
        this.durability = card.durability;
        this.cost = card.Get_Cost();
        this.mGearScore = card.getGearScore();
        this.TEST_GearScoreText.setText(String.format("%d", mGearScore));
        this.TEST_MOB_GEARSCORE = card.TEST_MOB_GEARSCORE;
        TEST_MOB_GEARSCORE_TEXT.setText(this.TEST_MOB_GEARSCORE+"");
    }

    int Get_Cost(){
        return cost;
    }

    int Get_Durability(){
        return durability;
    }
    void Set_Durability(int durability){
        this.durability = durability;
    }

    public byte getSlot_type() {
        return slot_type;
    }
    public void setSlot_type(byte slot_type) {
        this.slot_type = slot_type;
    }
}