package com.example.md_coursework1;

public class WeatherData {

    public String city;
    public String day;
    public String time;
    public String maxT;
    public String windD;
    public String windS;


    public WeatherData(String city, String time,String temperature, String windDir, String windSpd) {
        this.day = city;
        this.time = time;
        this.maxT = temperature;
        this.windD = windDir;
        this.windS = windSpd;
    }

    public String GetDay()
    {
       return day;
    }

    public String GetTime()
    {
        return time;
    }

    public String GetMaxT()
    {
        return maxT;
    }
    public String GetWindD()
    {
        return windD;
    }
    public String GetWindS()
    {
        return windS;
    }

    public String ShowWeatherData()
    {
        String weatherdata = day + " " + maxT + " " + windD + " " + windS;
        return weatherdata;
    }

}
