package com.example.skatt.myapplication;

import java.util.List;

public class ResponceTrade {
    private CardPlayerResponse[] mTrade;
    private Integer mSkillCost;

    public CardPlayerResponse[] getTrade() {
        return mTrade;
    }

    public void setTrade(CardPlayerResponse[] trade) {
        mTrade = trade;
    }

    public Integer getSkillCost() {
        return mSkillCost;
    }

    public void setSkillCost(Integer skillCost) {
        mSkillCost = skillCost;
    }
}