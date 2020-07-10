package com.example.fangyuanzheng.schoolday.service;
import com.example.fangyuanzheng.schoolday.data.Channel;
/**
 * Created by Fangyuan Zheng on 4/10/2017.
 */

public interface WeatherServiceCallback {
    void  serviceSuccess(Channel channel);
    void  serviceFailure(Exception exception);
}
