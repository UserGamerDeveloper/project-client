package com.example.skatt.myapplication;

public class Card_Inventory_Temp {
    int value_one;
    int id_drawable;
    private int type;
    CharSequence name_text = "";

    void Copy(Card_Inventory card){

        this.value_one = card.Get_Value_One();
        this.id_drawable = card.Get_Id_Drawable();
        this.name_text = card.name_text.getText();
        this.type = card.Get_Type();
    }

    boolean Is_Close(int card_back){
        return id_drawable == card_back;
    }

    int Get_Hp(){
        return value_one;
    }

    int Get_Id_Drawable(){
        return id_drawable;
    }

    int Get_Type(){
        return type;
    }
}
