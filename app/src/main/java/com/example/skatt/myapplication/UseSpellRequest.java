package com.example.skatt.myapplication;

import java.util.ArrayList;

public class UseSpellRequest {
    ArrayList<CardPlayerResponse> mInventory;
    byte mSlotSpell;

    public ArrayList<CardPlayerResponse> getInventory() {
        return mInventory;
    }

    public void setInventory(ArrayList<CardPlayerResponse> inventory) {
        mInventory = inventory;
    }

    public byte getSlotSpell() {
        return mSlotSpell;
    }

    public void setSlotSpell(byte slotSpell) {
        mSlotSpell = slotSpell;
    }

}
