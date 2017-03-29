package com.fclinton.SenseApi;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by Foster on 3/24/2017.
 */
public class SenseApi {
    private String accessToken;
    private Calendar accessTokenExpire;
    private char tempUnit = 'f';
   public SenseApi(String username,String password){
        try {
            URL apiURL = new URL("https://api.hello.is/v1/oauth2/token");
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) apiURL.openConnection();
            //Set the headers
            httpsURLConnection.setRequestProperty("X-Client-Version","1.4.4.4");
            httpsURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            httpsURLConnection.setRequestProperty("User-Agent","Sense/1.4.4.4 Platform/iOS OS/9.3.2");
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setDoOutput(true);
            //Set the post data
            Map<String,String> arguments = new HashMap<>();
            arguments.put("client_id", "cbaf8aaf-609a-46f8-98d9-292d5376a6b7");
            arguments.put("grant_type", "password");
            arguments.put("username",username);
            arguments.put("password",password);
            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String,String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;
            //do the post
            httpsURLConnection.setFixedLengthStreamingMode(length);
            httpsURLConnection.connect();
            try(OutputStream os = httpsURLConnection.getOutputStream()) {
                os.write(out);
            }
            Reader in = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            String response = sb.toString();
            JSONObject jsonObject = new JSONObject(response);
            setLogin(jsonObject.getString("access_token"),jsonObject.getInt("expires_in"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public  SenseApi(String access_token,int expiresin){
        setLogin(access_token,expiresin);
        accessTokenExpire= Calendar.getInstance();
        accessTokenExpire.add(Calendar.SECOND,expiresin);
    }

    private void setLogin(String access_token,int expiresin){
        accessToken=access_token;
        accessTokenExpire=Calendar.getInstance();
        accessTokenExpire.add(Calendar.SECOND,expiresin);
    }

    public void setTempUnit(boolean isFahrenheit){
        if(isFahrenheit) tempUnit ='f';
        if(!isFahrenheit) tempUnit ='c';
    }
    private String getAccessToken() {
        if (accessTokenExpire.after(Calendar.getInstance())) {
            return accessToken;
        } else {
            //TODO make something that will refresh token
        }
        return null;
    }
    public SensorData getCurrentSensorData(int sensorType){
        for(SensorData sensorData:getAllCurrentSensorData()){
            if(sensorData.getSensorType()==sensorType)return sensorData;
        }
        return null;
    }
    public ArrayList<SensorData> getAllCurrentSensorData(){
        try {
            URL apiURL = new URL("https://api.hello.is/v1/room/current?temp_unit="+ tempUnit);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) apiURL.openConnection();
            //Set the headers
            httpsURLConnection.setRequestProperty("X-Client-Version", "1.4.4.4");
            httpsURLConnection.setRequestProperty("Authorization", "Bearer "+getAccessToken());
            httpsURLConnection.setRequestProperty("User-Agent", "Sense/1.4.4.4 Platform/iOS OS/9.3.2");
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setDoOutput(true);
            Reader in = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0; )
                sb.append((char) c);
            String response = sb.toString();
            JSONObject jsonObject = new JSONObject(response);
            ArrayList<SensorData> sensorDataArrayList = new ArrayList<>();
            for(int i=0;i<5;i++){
                sensorDataArrayList.add(new SensorData(jsonObject.getJSONObject(SensorData.getRequestName(i)),i));
            }
            return sensorDataArrayList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public TimelineData getTimelineData(int month,int day, int year){
        try {
            URL apiURL = new URL("https://api.hello.is/v2/timeline/"+year+"-"+month+"-"+day);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) apiURL.openConnection();
            //Set the headers
            httpsURLConnection.setRequestProperty("X-Client-Version", "1.4.4.4");
            httpsURLConnection.setRequestProperty("Authorization", "Bearer "+getAccessToken());
            httpsURLConnection.setRequestProperty("User-Agent", "Sense/1.4.4.4 Platform/iOS OS/9.3.2");
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setDoOutput(true);
            Reader in = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0; )
                sb.append((char) c);
            String response = sb.toString();
            JSONObject jsonObject = new JSONObject(response);
            return new TimelineData(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
