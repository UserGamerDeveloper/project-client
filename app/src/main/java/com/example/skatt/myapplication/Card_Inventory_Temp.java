package com.example.skatt.myapplication;

public class Card_Inventory_Temp {
    int value_one;
    int id_drawable;
    private int type;
    CharSequence name_text = "";
    int durability;
    int cost;
    int mGearScore;
    int TEST_MOB_GEARSCORE;

    void Copy(Card_Inventory card){

        this.value_one = card.Get_Value_One();
        this.id_drawable = card.Get_Id_Drawable();
        this.name_text = card.name_text.getText();
        this.type = card.getType();
        this.durability = card.Get_Durability();
        this.cost = card.Get_Cost();
        this.mGearScore = card.getGearScore();
        this.TEST_MOB_GEARSCORE = card.TEST_MOB_GEARSCORE;
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
    int Get_Cost(){
        return cost;
    }

    public int getGearScore() {
        return mGearScore;
    }
    public void setGearScore(int gearScore) {
        mGearScore = gearScore;
    }
}
