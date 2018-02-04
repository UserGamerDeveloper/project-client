package com.example.skatt.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.View;

import com.squareup.picasso.Picasso;

import java.util.Random;

class Card_Inventory extends Card {

    static String[] column_name = {
            DB_Open_Helper.id,
            DB_Open_Helper.name,
            DB_Open_Helper.value_one,
            DB_Open_Helper.id_image,
            DB_Open_Helper.type,
            DB_Open_Helper.cost
    };
    int durability;
    byte slot_type;
    byte slot_id;
    int cost;

    public Card_Inventory(Context context) {
        super(context);
    }
    public Card_Inventory(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public Card_Inventory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void Change(DB_Open_Helper db_open_helper, Random random){

        SQLiteDatabase data_base = db_open_helper.getReadableDatabase();

        int type = random.nextInt(3);
        String[] value = new String[]{
                String.valueOf(type*DB_Open_Helper.count_id_in_type +
                        random.nextInt(DB_Open_Helper.count_item_in_type.get(type)))
        };

        Cursor cursor = data_base.query(
                DB_Open_Helper.table_inventory,
                column_name,
                DB_Open_Helper.id + "=?",
                value,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        name_text.setText(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(DB_Open_Helper.name)
                )
        );

        value_one = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.value_one)
        );

        this.Set_Value_One_text(value_one);

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
    void Change(DB_Open_Helper db_open_helper, Random random, int type){

        SQLiteDatabase data_base = db_open_helper.getReadableDatabase();

        String[] value = new String[]{
                String.valueOf(type*DB_Open_Helper.count_id_in_type +
                        random.nextInt(DB_Open_Helper.count_item_in_type.get(type)))
        };

        Cursor cursor = data_base.query(
                DB_Open_Helper.table_inventory,
                column_name,
                DB_Open_Helper.id + "=?",
                value,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        name_text.setText(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(DB_Open_Helper.name)
                )
        );

        value_one = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.value_one)
        );

        this.Set_Value_One_text(value_one);

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

    void Open() {
        super.Open();
        this.value_one_text.setVisibility(View.VISIBLE);
    }

    void Copy(Card_Inventory card){

        super.Copy(card);
        this.value_one_text.setVisibility(View.VISIBLE);
        this.durability = card.Get_Durability();
        this.cost = card.Get_Cost();
    }
    void Copy(Card_Inventory_Temp card){

        this.value_one = card.Get_Hp();
        this.id_drawable = card.Get_Id_Drawable();
        Picasso.with(getContext()).load(id_drawable).placeholder(R.color.color_black).into(imageView);
/*
        this.imageView.setImageResource(id_drawable);
*/
        this.name_text.setText(card.name_text);
        this.Set_Value_One_text(card.value_one);
        this.type = card.Get_Type();
        this.durability = card.durability;
        this.cost = card.Get_Cost();
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
}
