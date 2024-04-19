package com.example.md_coursework1;

public class WeatherData3Day {

        public String day;
        public String weather;
        public String minT;
        public String maxT;
        public String windD;
        public String windS;
        public String visibility;
        public String pressure;
        public String humidity;
        public String uvRisk;



        public WeatherData3Day(String day,String weather,String minT, String maxT, String windDir,String windSpd, String visibility, String pressure, String humidity, String uvRisk) {
            this.day  = day;
            this.weather = weather;
            this.minT = minT;
            this.maxT = maxT;
            this.windD = windDir;
            this.windS = windSpd;
            this.visibility = visibility;
            this.pressure = pressure;
            this. humidity = humidity;
            this.uvRisk = uvRisk;
        }


        public String GetDay()
        {
            return day;
        }
        public String GetWeather()
    {
        return  weather;
    }
        public String GetMinT()
         {
            return minT;
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
        public String GetVisibility(){return visibility;}
        public String GetPressure(){return pressure;}
        public String GetHumidity()
        {
            return humidity;
        }
        public String GetUvRisk(){return uvRisk;}


        public String Show3DayWeatherData()
        {
            String weatherdata = day + " " + weather + " " + minT + " " + maxT + " " + windD + " " + windS + " " + visibility + " " + pressure + " " + humidity + " " + uvRisk;
            return  weatherdata;
        }
}
