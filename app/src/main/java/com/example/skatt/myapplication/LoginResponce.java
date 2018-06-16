package com.example.skatt.myapplication;

import java.util.ArrayList;
import java.util.List;

public class LoginResponce {

    private byte mHP;
    private byte mState;
    private int mMoney;
    private int mMoneyBank;
    private MobResponse mCardTable0;
    private MobResponse mCardTable1;
    private MobResponse mCardTable2;
    private MobResponse mCardTable3;
    private MobResponse mCardTable4;
    private MobResponse mCardTable5;
    private MobResponse mCardTable6;
    private MobResponse mCardTable7;
    private Byte mCardTableTargetIDInArray;
    private Byte mCardTableTargetHP;
    private List<ItemResponse> mInventory;
    private List<ItemResponse> mLoot;
    private List<ItemResponse> mTrade;
    private StatsResponse mStats;
    private Integer mCostVendorSkill;
    private byte mHpDefault;

    public byte getHP() {
        return mHP;
    }

    public void setHP(byte HP) {
        mHP = HP;
    }

    public byte getState() {
        return mState;
    }

    public void setState(byte state) {
        mState = state;
    }

    public int getMoney() {
        return mMoney;
    }

    public void setMoney(int money) {
        mMoney = money;
    }

    public int getMoneyBank() {
        return mMoneyBank;
    }

    public void setMoneyBank(int moneyBank) {
        mMoneyBank = moneyBank;
    }

    public MobResponse getCardTable0() {
        return mCardTable0;
    }

    public void setCardTable0(MobResponse cardTable0) {
        mCardTable0 = cardTable0;
    }

    public MobResponse getCardTable1() {
        return mCardTable1;
    }

    public void setCardTable1(MobResponse cardTable1) {
        mCardTable1 = cardTable1;
    }

    public MobResponse getCardTable2() {
        return mCardTable2;
    }

    public void setCardTable2(MobResponse cardTable2) {
        mCardTable2 = cardTable2;
    }

    public MobResponse getCardTable3() {
        return mCardTable3;
    }

    public void setCardTable3(MobResponse cardTable3) {
        mCardTable3 = cardTable3;
    }

    public MobResponse getCardTable4() {
        return mCardTable4;
    }

    public void setCardTable4(MobResponse cardTable4) {
        mCardTable4 = cardTable4;
    }

    public MobResponse getCardTable5() {
        return mCardTable5;
    }

    public void setCardTable5(MobResponse cardTable5) {
        mCardTable5 = cardTable5;
    }

    public MobResponse getCardTable6() {
        return mCardTable6;
    }

    public void setCardTable6(MobResponse cardTable6) {
        mCardTable6 = cardTable6;
    }

    public MobResponse getCardTable7() {
        return mCardTable7;
    }

    public void setCardTable7(MobResponse cardTable7) {
        mCardTable7 = cardTable7;
    }

    public Byte getCardTableTargetIDInArray() {
        return mCardTableTargetIDInArray;
    }

    public void setCardTableTargetIDInArray(Byte cardTableTargetIDInArray) {
        mCardTableTargetIDInArray = cardTableTargetIDInArray;
    }

    public Byte getCardTableTargetHP() {
        return mCardTableTargetHP;
    }

    public void setCardTableTargetHP(Byte cardTableTargetHP) {
        mCardTableTargetHP = cardTableTargetHP;
    }

    public List<ItemResponse> getInventory() {
        return mInventory;
    }

    public void setInventory(List<ItemResponse> inventory) {
        mInventory = inventory;
    }

    public List<ItemResponse> getLoot() {
        return mLoot;
    }

    public void setLoot(List<ItemResponse> loot) {
        mLoot = loot;
    }

    public List<ItemResponse> getTrade() {
        return mTrade;
    }

    public void setTrade(List<ItemResponse> trade) {
        mTrade = trade;
    }

    public StatsResponse getStats() {
        return mStats;
    }

    public void setStats(StatsResponse stats) {
        mStats = stats;
    }

    public Integer getCostVendorSkill() {
        return mCostVendorSkill;
    }

    public void setCostVendorSkill(Integer costVendorSkill) {
        mCostVendorSkill = costVendorSkill;
    }

    public byte getHpDefault() {
        return mHpDefault;
    }

    public void setHpDefault(byte hpDefault) {
        mHpDefault = hpDefault;
    }
}