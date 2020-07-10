package com.example.fangyuanzheng.schoolday.data;

import org.json.JSONObject;

/**
 * Created by Fangyuan Zheng on 4/10/2017.
 */

public class Item implements JSONPopulate{
    private  Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {
        condition=new Condition();
        condition.populate(data.optJSONObject("condition"));

    }
}
