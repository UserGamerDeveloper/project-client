package com.example.skatt.myapplication;

class CardPlayerResponse {
    private byte mSlotId;
    private byte mIdItem;
    private byte mDurability;

    public CardPlayerResponse() {
    }

    byte getDurability(){
        return mDurability;
    }
    public byte getSlotId() {
        return mSlotId;
    }
    public byte getIdItem() {
        return mIdItem;
    }

    public void setSlotId(byte slotId) {
        mSlotId = slotId;
    }
    public void setIdItem(byte idItem) {
        mIdItem = idItem;
    }
    public void setDurability(byte durability) {
        mDurability = durability;
    }
}