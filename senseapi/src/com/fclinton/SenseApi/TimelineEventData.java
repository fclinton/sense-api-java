package com.fclinton.SenseApi;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Foster on 3/28/2017.
 */
public class TimelineEventData {
    public static final int SLEEP_STATE_AWAKE = 0;
    public static final int SLEEP_STATE_MEDIUM = 1;
    public static final int SLEEP_STATE_SOUND = 2;
    private Calendar timestamp;
    private int durationInMillis;
    private String message;
    private int sleepDepth;
    private int sleepState;
    private String eventType;
    TimelineEventData(int timeStampMillis,int durationMillis,String messageText,
                      int sleepDepthVal,String eventTypeName,String sleepStateText){
        timestamp=Calendar.getInstance();
        timestamp.setTimeInMillis(timeStampMillis);
        timestamp.setTimeZone(TimeZone.getTimeZone("utc"));
        durationInMillis=durationMillis;
        message=messageText;
        sleepDepth=sleepDepthVal;
        eventType=eventTypeName;
        sleepState=getSleepState(sleepStateText);
    }
    TimelineEventData(JSONObject jsonObject){
        this(
                jsonObject.getInt("timestamp"),
                jsonObject.getInt("duration_millis"),
                jsonObject.getString("message"),
                jsonObject.getInt("sleep_depth"),
                jsonObject.getString("event_type"),
                jsonObject.getString("sleep_state")
        );
    }

    public static int getSleepState(String sleepStateText){
        switch (sleepStateText){
            case "AWAKE":
                return SLEEP_STATE_AWAKE;
            case "MEDIUM":
                return SLEEP_STATE_MEDIUM;
            case "SOUND":
                return SLEEP_STATE_SOUND;
        }
        return -1;
    }

    public int getSleepState() {
        return sleepState;
    }

    public Calendar getTimestamp() {
        return timestamp;
    }

    public int getDurationInMillis() {
        return durationInMillis;
    }

    public String getMessage() {
        return message;
    }

    public int getSleepDepth() {
        return sleepDepth;
    }

    public String getEventType() {
        return eventType;
    }
}
