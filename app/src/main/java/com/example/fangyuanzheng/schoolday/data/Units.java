package com.example.fangyuanzheng.schoolday.data;

import org.json.JSONObject;

/**
 * Created by Fangyuan Zheng on 4/10/2017.
 */

public class Units implements JSONPopulate {
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature=data.optString("temperature");
    }
}
