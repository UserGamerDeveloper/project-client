package com.example.skatt.myapplication;

public class CardInventoryTemp {
    private Byte mIDItem;
    int value_one;
    int id_drawable;
    private int type;
    CharSequence name_text = "";
    int durability;
    int mDurabilityMax;
    int cost;
    int mGearScore;
    int TEST_MOB_GEARSCORE;

    void Copy(CardInventory card){
        this.mIDItem = card.getIDItem();
        this.value_one = card.getValueOne();
        this.id_drawable = card.getIdDrawable();
        this.name_text = card.mNameText.getText();
        this.type = card.getType();
        this.durability = card.getDurability();
        this.mDurabilityMax = card.getDurabilityMax();
        this.cost = card.getBuyCost();
        this.mGearScore = card.getGearScore();
        this.TEST_MOB_GEARSCORE = card.TEST_MOB_GEARSCORE;
    }

    public Byte getIDItem() {
        return mIDItem;
    }
    public int getDurabilityMax() {
        return mDurabilityMax;
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
