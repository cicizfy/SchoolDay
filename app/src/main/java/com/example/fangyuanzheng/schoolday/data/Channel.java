package com.example.fangyuanzheng.schoolday.data;

import org.json.JSONObject;
/**
 * Created by Fangyuan Zheng on 4/10/2017.
 */

public class Channel implements JSONPopulate  {

    private  Item item;
    private Units units;

    public Item getItem() {
        return item;
    }

    public Units getUnits() {
        return units;
    }

    @Override
    public void populate(JSONObject data) {
        units=new Units();
        units.populate(data.optJSONObject("units"));

        item=new Item();
        item.populate(data.optJSONObject("item"));

    }
}
