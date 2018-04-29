package com.example.skatt.myapplication;

import java.util.ArrayList;
import java.util.List;

public class LoginResponce {
    public byte getHP() {
        return mHP;
    }
    public byte getState() {
        return mState;
    }
    public int getMoney() {
        return mMoney;
    }
    public int getMoneyBank() {
        return mMoneyBank;
    }
    public Byte getCardTable0() {
        return mCardTable0;
    }
    public Byte getCardTable1() {
        return mCardTable1;
    }
    public Byte getCardTable2() {
        return mCardTable2;
    }
    public Byte getCardTable3() {
        return mCardTable3;
    }
    public Byte getCardTable4() {
        return mCardTable4;
    }
    public Byte getCardTable5() {
        return mCardTable5;
    }
    public Byte getCardTable6() {
        return mCardTable6;
    }
    public Byte getCardTable7() {
        return mCardTable7;
    }
    public Byte getCardTableTargetHP() {
        return mCardTableTargetHP;
    }
    public List<CardPlayerResponse> getInventory() {
        return mInventory;
    }
    public List<CardPlayerResponse> getLoot() {
        return mLoot;
    }
    public List<CardPlayerResponse> getHands() {
        return mHands;
    }
    public StatsResponse getStats() {
        return mStats;
    }

    public Byte getCardTableTargetIDInArray() {
        return mCardTableTargetIDInArray;
    }
    public void setCardTableTargetIDInArray(Byte cardTableTargetIDInArray) {
        mCardTableTargetIDInArray = cardTableTargetIDInArray;
    }

    public void setHP(byte HP) {
        mHP = HP;
    }
    public void setState(byte state) {
        mState = state;
    }
    public void setMoney(int money) {
        mMoney = money;
    }
    public void setMoneyBank(int moneyBank) {
        mMoneyBank = moneyBank;
    }
    public void setCardTable0(Byte cardTable0) {
        mCardTable0 = cardTable0;
    }
    public void setCardTable1(Byte cardTable1) {
        mCardTable1 = cardTable1;
    }
    public void setCardTable2(Byte cardTable2) {
        mCardTable2 = cardTable2;
    }
    public void setCardTable3(Byte cardTable3) {
        mCardTable3 = cardTable3;
    }
    public void setCardTable4(Byte cardTable4) {
        mCardTable4 = cardTable4;
    }
    public void setCardTable5(Byte cardTable5) {
        mCardTable5 = cardTable5;
    }
    public void setCardTable6(Byte cardTable6) {
        mCardTable6 = cardTable6;
    }
    public void setCardTable7(Byte cardTable7) {
        mCardTable7 = cardTable7;
    }
    public void setCardTableTargetHP(Byte cardTableTargetHP) {
        mCardTableTargetHP = cardTableTargetHP;
    }
    public void setInventory(List<CardPlayerResponse> inventory) {
        mInventory = inventory;
    }
    public void setLoot(List<CardPlayerResponse> loot) {
        mLoot = loot;
    }
    public void setHands(List<CardPlayerResponse> hands) {
        mHands = hands;
    }
    public void setStats(StatsResponse stats) {
        mStats = stats;
    }

    public List<CardPlayerResponse> getTrade() {
        return mTrade;
    }
    public void setTrade(List<CardPlayerResponse> trade) {
        mTrade = trade;
    }

    private byte mHP;
    private byte mState;
    private int mMoney;
    private int mMoneyBank;
    private Byte mCardTable0;
    private Byte mCardTable1;
    private Byte mCardTable2;
    private Byte mCardTable3;
    private Byte mCardTable4;
    private Byte mCardTable5;
    private Byte mCardTable6;
    private Byte mCardTable7;
    private Byte mCardTableTargetIDInArray;
    private Byte mCardTableTargetHP;
    private List<CardPlayerResponse> mInventory =new ArrayList<>();
    private List<CardPlayerResponse> mLoot =new ArrayList<>();
    private List<CardPlayerResponse> mHands =new ArrayList<>();
    private List<CardPlayerResponse> mTrade = new ArrayList<>();
    private StatsResponse mStats;
}