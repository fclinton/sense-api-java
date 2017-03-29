package com.fclinton.SenseApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Foster on 3/28/2017.
 */
public class TimelineData {
    private int sleepScore;
    private String message;
    private String date;
    private ArrayList<TimelineEventData>eventDatas;
    private int totalSleepMinutes;
    private int totalSoundSleepMinutes;
    private int totalTimeToSleep;
    private int totalTimesAwake;
    private int fellAsleepTimestamp;
    private int wokeUpTimestamp;
    private boolean isIdealTemperature,isIdealHumidity,isIdealAirQuality,isIdealLight,isIdealSound;
    TimelineData(int sleepScore, String message, String date, JSONArray eventsJson,
                 int totalSleepMinutes,int totalSoundSleepMinutes,int totalTimeToSleep,
                 int totalTimesAwake,int fellAsleepTimestamp,int wokeUpTimestamp,
                 boolean isIdealTemperature, boolean isIdealHumidity, boolean isIdealAirQuality,
                 boolean isIdealLight, boolean isIdealSound){

        eventDatas=new ArrayList<>();
        //FIRST THE EASY STUFF
        this.sleepScore=sleepScore;
        this.message=message;
        this.date=date;
        this.totalSleepMinutes=totalSleepMinutes;
        this.totalSoundSleepMinutes=totalSoundSleepMinutes;
        this.totalTimeToSleep=totalTimeToSleep;
        this.totalTimesAwake=totalTimesAwake;
        this.fellAsleepTimestamp=fellAsleepTimestamp;
        this.wokeUpTimestamp=wokeUpTimestamp;
        this.isIdealAirQuality=isIdealAirQuality;
        this.isIdealTemperature=isIdealTemperature;
        this.isIdealHumidity=isIdealHumidity;
        this.isIdealLight=isIdealLight;
        this.isIdealSound=isIdealSound;
        for(int i=0;i<eventsJson.length();i++){
            eventDatas.add(new TimelineEventData(eventsJson.getJSONObject(i)));
        }

    }
    TimelineData(JSONObject jsonObject){
        this(jsonObject.getInt("score"),
                jsonObject.getString("message"),
                jsonObject.getString("date"),
                jsonObject.getJSONArray("events"),
                jsonObject.getJSONArray("metrics").getJSONObject(0).getInt("value"),
                jsonObject.getJSONArray("metrics").getJSONObject(1).getInt("value"),
                jsonObject.getJSONArray("metrics").getJSONObject(2).getInt("value"),
                jsonObject.getJSONArray("metrics").getJSONObject(3).getInt("value"),
                jsonObject.getJSONArray("metrics").getJSONObject(4).getInt("value"),
                jsonObject.getJSONArray("metrics").getJSONObject(5).getInt("value"),
                Objects.equals(jsonObject.getJSONArray("metrics").getJSONObject(6).getString("condition"), "IDEAL"),
                Objects.equals(jsonObject.getJSONArray("metrics").getJSONObject(7).getString("condition"), "IDEAL"),
                Objects.equals(jsonObject.getJSONArray("metrics").getJSONObject(8).getString("condition"), "IDEAL"),
                Objects.equals(jsonObject.getJSONArray("metrics").getJSONObject(9).getString("condition"), "IDEAL"),
                Objects.equals(jsonObject.getJSONArray("metrics").getJSONObject(10).getString("condition"), "IDEAL")
                                );
    }

    public int getSleepScore() {
        return sleepScore;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<TimelineEventData> getEventDatas() {
        return eventDatas;
    }

    public int getTotalSleepMinutes() {
        return totalSleepMinutes;
    }

    public int getTotalSoundSleepMinutes() {
        return totalSoundSleepMinutes;
    }

    public int getTotalTimeToSleep() {
        return totalTimeToSleep;
    }

    public int getTotalTimesAwake() {
        return totalTimesAwake;
    }

    public int getFellAsleepTimestamp() {
        return fellAsleepTimestamp;
    }

    public int getWokeUpTimestamp() {
        return wokeUpTimestamp;
    }

    public boolean isIdealTemperature() {
        return isIdealTemperature;
    }

    public boolean isIdealHumidity() {
        return isIdealHumidity;
    }

    public boolean isIdealAirQuality() {
        return isIdealAirQuality;
    }

    public boolean isIdealLight() {
        return isIdealLight;
    }

    public boolean isIdealSound() {
        return isIdealSound;
    }
}
