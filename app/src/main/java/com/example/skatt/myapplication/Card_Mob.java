package com.example.skatt.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

class Card_Mob extends Card_Inventory {

    private int value_two;
    TextView value_two_text;
    private int money;

    void Change(SQLiteDatabase data_base, DB_Open_Helper db_open_helper, Random random){

        data_base = db_open_helper.getReadableDatabase();

        String[] column_name = {
                DB_Open_Helper.id,
                DB_Open_Helper.name,
                DB_Open_Helper.value_one,
                DB_Open_Helper.value_two,
                DB_Open_Helper.id_image,
                DB_Open_Helper.money
        };

        Cursor cursor = data_base.query(
                DB_Open_Helper.table_mobs,
                column_name,
                DB_Open_Helper.id + "=?",
                new String[]{String.valueOf(random.nextInt(4))},
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

        value_one_text.setText(
                String.format(" %s", value_one)
        );

        value_two = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.value_two)
        );

        value_two_text.setText(
                String.format("%s ", value_two)
        );

        id_drawable = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.id_image)
        );

        money = cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.money)
        );

        cursor.close();
    }

    void Copy(Card_Mob card){

        super.Copy(card);
        this.value_two = card.Get_Damage();
        this.value_two_text.setText(card.value_two_text.getText());
        this.value_two_text.setVisibility(card.value_two_text.getVisibility());
    }

    void Open() {

        super.Open();
        this.value_two_text.setVisibility(View.VISIBLE);
    }

    void Close(int card_back) {
        super.Close(card_back);
        this.value_two_text.setVisibility(View.INVISIBLE);
    }

    int Get_Damage(){
        return value_two;
    }

    void Set_Value_Two(int damage){
        this.value_two = damage;
    }

    int Get_Money(){
        return money;
    }

    void Set_Money(int money){
        this.money = money;
    }
}
