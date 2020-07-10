package com.example.fangyuanzheng.schoolday.service;

import com.example.fangyuanzheng.schoolday.data.Channel;

import android.net.Uri;
import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
/**
 * Created by Fangyuan Zheng on 4/10/2017.
 */

public class YahooWeatherService {
    private WeatherServiceCallback callback;
    private  String location;
    private  Exception error;
    public YahooWeatherService(WeatherServiceCallback callback){
        this.callback=callback;
    }
    public String getLocation(){
        return location;
    }

    public void refreshWeather(String location){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String YQL=String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"syracuse, ny\")");
                String endpoint=String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));
                try {
                    URL url=new URL(endpoint);
                    URLConnection connection=url.openConnection();
                    InputStream inputStream=connection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result=new StringBuilder();
                    String line;
                    while((line =reader.readLine())!=null){
                        result.append(line);

                    }
                    return  result.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    error=e;
                } catch (IOException e) {
                    error=e;
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(String s){
                if(s==null&&error!=null){
                    callback.serviceFailure(error);
                    return;
                }
                try {
                    JSONObject data=new JSONObject(s);
                    JSONObject queryResults=data.optJSONObject("query");
                    int count=queryResults.optInt("count");
                    if (count==0){
                        callback.serviceFailure(new LoactionWeatherException("No Weather Info found"));
                        return;
                    }
                    Channel channel =new Channel();
                    channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));
                    callback.serviceSuccess(channel);
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.serviceFailure(e);
                }

            }
        }.execute(location);

    }
    public class LoactionWeatherException extends  Exception{

        public LoactionWeatherException(String message) {
            super(message);
        }
    }
}
