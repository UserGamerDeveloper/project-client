package com.example.skatt.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

class Card {
    private int damage;
    private int hp;
    private int id_drawable;
    ConstraintLayout layout;
    ImageView imageView;
    TextView name_text;
    TextView hp_text;
    TextView damage_text;

    void Change(SQLiteDatabase data_base, DB_Open_Helper db_open_helper, Random random){

        data_base = db_open_helper.getReadableDatabase();

        String[] column_name = {
                DB_Open_Helper.id,
                DB_Open_Helper.name,
                DB_Open_Helper.hp,
                DB_Open_Helper.damage,
                DB_Open_Helper.id_image
        };

        Cursor cursor = data_base.query(
                DB_Open_Helper.table_name,
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

        this.Set_Hp(cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.hp)
                )
        );

        hp_text.setText(
                String.format(" %s",hp)
        );

        this.Set_Damage(cursor.getInt(
                cursor.getColumnIndexOrThrow(DB_Open_Helper.damage)
                )
        );

        damage_text.setText(
                String.format("%s ", damage)
        );

        this.Set_Id_Drawable(cursor.getInt(
                        cursor.getColumnIndexOrThrow(DB_Open_Helper.id_image)
                )
        );

        cursor.close();
    }

    void Copy(Card card){

        this.damage = card.Get_Damage();
        this.hp = card.Get_Hp();
        this.id_drawable = card.Get_Id_Drawable();
        this.imageView.setImageResource(id_drawable);
        this.name_text.setText(card.name_text.getText());
        this.name_text.setVisibility(card.name_text.getVisibility());
        this.hp_text.setText(card.hp_text.getText());
        this.hp_text.setVisibility(card.hp_text.getVisibility());
        this.damage_text.setText(card.damage_text.getText());
        this.damage_text.setVisibility(card.damage_text.getVisibility());
    }

    void Open() {

        this.name_text.setVisibility(View.VISIBLE);
        this.damage_text.setVisibility(View.VISIBLE);
        this.hp_text.setVisibility(View.VISIBLE);
        this.imageView.setImageResource(id_drawable);
    }

    void Close(int card_back) {

        this.name_text.setVisibility(View.INVISIBLE);
        this.damage_text.setVisibility(View.INVISIBLE);
        this.hp_text.setVisibility(View.INVISIBLE);
        this.imageView.setImageResource(card_back);
    }

    boolean Is_Close(int card_back){
        return id_drawable == card_back;
    }
    int Get_Damage(){
        return damage;
    }

    int Get_Hp(){
        return hp;
    }

    int Get_Id_Drawable(){
        return id_drawable;
    }

    void Set_Damage(int damage){
        this.damage = damage;
    }

    void Set_Hp(int hp){
        this.hp = hp;
    }

    void Set_Id_Drawable(int id_drawable){
        this.id_drawable = id_drawable;
    }
}
