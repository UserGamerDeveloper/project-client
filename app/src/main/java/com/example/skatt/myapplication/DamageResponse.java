package com.example.skatt.myapplication;

public class DamageResponse {
    public String getLoot() {
        return mLoot;
    }
    public void setLoot(String loot) {
        mLoot = loot;
    }
    public MobResponse[] getNextMobs() {
        return mNextMobs;
    }
    public void setNextMobs(MobResponse[] nextMobs) {
        this.mNextMobs = nextMobs;
    }

    private String mLoot;
    private MobResponse[] mNextMobs;
}