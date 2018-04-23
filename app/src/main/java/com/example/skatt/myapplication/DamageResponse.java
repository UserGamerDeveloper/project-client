package com.example.skatt.myapplication;

public class DamageResponse {
    public String getLoot() {
        return mLoot;
    }
    public void setLoot(String loot) {
        mLoot = loot;
    }
    public byte[] getCardTableID() {
        return cardTableID;
    }
    public void setCardTableID(byte[] cardTableID) {
        this.cardTableID = cardTableID;
    }

    private String mLoot;
    private byte[] cardTableID;
}