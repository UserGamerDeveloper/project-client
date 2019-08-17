package com.example.skatt.myapplication;

import java.util.List;

public class ResponceTrade {
    private ItemResponse[] mTrade;
    private Integer mSkillCost;

    public ItemResponse[] getTrade() {
        return mTrade;
    }

    public void setTrade(ItemResponse[] trade) {
        mTrade = trade;
    }

    public Integer getSkillCost() {
        return mSkillCost;
    }

    public void setSkillCost(Integer skillCost) {
        mSkillCost = skillCost;
    }
}