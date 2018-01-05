package com.example.skatt.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

class Card_Inventory extends ConstraintLayout {

    int value_one;
    int id_drawable;
    private int type;
    ImageView imageView;
    TextView name_text;
    TextView value_one_text;
    byte slot_type;
    byte slot_id;

    public Card_Inventory(Context context) {
        super(context);
    }

    public Card_Inventory(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Card_Inventory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void Change(SQLiteDatabase data_base, DB_Open_Helper db_open_helper, Random random){

        data_base = db_open_helper.getReadableDatabase();

        String[] column_name = {
                DB_Open_Helper.id,
                DB_Open_Helper.name,
                DB_Open_Helper.value_one,
                DB_Open_Helper.id_image,
                DB_Open_Helper.type
        };

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

        cursor.close();
    }

    void Copy(Card_Inventory card){

        this.value_one = card.Get_Value_One();
        this.id_drawable = card.Get_Id_Drawable();
        this.imageView.setImageResource(id_drawable);
        this.name_text.setText(card.name_text.getText());
        this.name_text.setVisibility(card.name_text.getVisibility());
        this.value_one_text.setText(card.value_one_text.getText());
        this.value_one_text.setVisibility(card.value_one_text.getVisibility());
        this.type = card.Get_Type();
    }

    void Copy(Card_Inventory_Temp card){

        this.value_one = card.Get_Hp();
        this.id_drawable = card.Get_Id_Drawable();
        this.imageView.setImageResource(id_drawable);
        this.name_text.setText(card.name_text);
        this.Set_Value_One_text(card.value_one);
        this.type = card.Get_Type();
    }

    void Open() {

        this.name_text.setVisibility(View.VISIBLE);
        this.value_one_text.setVisibility(View.VISIBLE);
        this.imageView.setImageResource(id_drawable);
    }

    void Close(int card_back) {

        this.name_text.setVisibility(View.INVISIBLE);
        this.value_one_text.setVisibility(View.INVISIBLE);
        this.imageView.setImageResource(card_back);
        this.id_drawable = card_back;
    }

    boolean Is_Close(int card_back){
        return id_drawable == card_back;
    }

    int Get_Value_One(){
        return value_one;
    }

    int Get_Id_Drawable(){
        return id_drawable;
    }

    int Get_Type(){
        return type;
    }

    void Set_Value_One_text(int hp){
        if (hp/10<1){
            value_one_text.setText(
                    String.format(" %s", hp)
            );
        }
        else{
            value_one_text.setText(
                    String.format("%s", hp)
            );
        }
    }

    void Use(){

    }

    void Set_Type(int type){
        this.type = type;
    }

    void Set_Value_One(int hp){
        this.value_one = hp;
    }

    void Set_Id_Drawable(int id_drawable){
        this.id_drawable = id_drawable;
    }
}
