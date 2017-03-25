package com.fclinton.SenseApi;

import org.json.JSONObject;

import java.math.BigInteger;

/**
 * Created by Foster on 3/24/2017.
 */
public class SensorData {
    static public final int SENSOR_TYPE_PARTICULATES = 0;
    static public final int SENSOR_TYPE_HUMIDITY = 1;
    static public final int SENSOR_TYPE_SOUND = 2;
    static public final int SENSOR_TYPE_TEMPERATURE = 3;
    static public final int SENSOR_TYPE_LIGHT = 4;

    private String unit;
    private String condition;
    private String message;
    private Float value;
    private BigInteger last_updated_utc;
    private String idealConditions;
    private int sensorType;

    SensorData(String unit,String condition, String message, Float value,BigInteger last_updated_utc,String idealConditions,int sensorType){
        this.unit=unit;
        this.condition=condition;
        this.message=message;
        this.value=value;
        this.last_updated_utc=last_updated_utc;
        this.idealConditions=idealConditions;
        this.sensorType=sensorType;
    }
    SensorData(JSONObject jsonObject,int sensorType){
        this(
                jsonObject.getString("unit"),
                jsonObject.getString("condition"),
                jsonObject.getString("message"),
                (float) jsonObject.getLong("value"),
                jsonObject.getBigInteger("last_updated_utc"),
                jsonObject.getString("ideal_conditions"),
                sensorType
        );
    }

    static public String getRequestName(int sensorType){
        switch (sensorType){
            case SENSOR_TYPE_HUMIDITY:
                return "humidity";
            case SENSOR_TYPE_LIGHT:
                return "light";
            case SENSOR_TYPE_PARTICULATES:
                return "particulates";
            case SENSOR_TYPE_SOUND:
                return "sount";
            case SENSOR_TYPE_TEMPERATURE:
                return  "temperature";
        }
        return null; //Something went terribly wrong
    }

    static public String getSensorName(int sensorType){
        switch (sensorType){
            case SENSOR_TYPE_HUMIDITY:
                return "Humidity";
            case SENSOR_TYPE_LIGHT:
                return "Light";
            case SENSOR_TYPE_PARTICULATES:
                return "Air Quality";
            case SENSOR_TYPE_SOUND:
                return "Sound";
            case SENSOR_TYPE_TEMPERATURE:
                return  "Temperature";
        }
        return null; //Something went terribly wrong
    }

    public String getUnit() {
        return unit;
    }

    public String getCondition() {
        return condition;
    }

    public String getMessage() {
        return message;
    }

    public Float getValue() {
        return value;
    }

    public BigInteger getLast_updated_utc() {
        return last_updated_utc;
    }

    public String getIdealConditions() {
        return idealConditions;
    }

    public int getSensorType() {
        return sensorType;
    }

    public String getRequestName(){
        return getSensorName(this.sensorType);
    }

    public String getSensorName(){
        return getSensorName(this.sensorType);
    }

    public String toString(){
        return "The " + getSensorName() + " level is currently "+ value + " "+unit;
    }
}
