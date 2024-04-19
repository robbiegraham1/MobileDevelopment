/*  Starter project for Mobile Platform Development in main diet 2023/2024
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/

//
// Name                 _________________
// Student ID           _________________
// Programme of Study   _________________
//

// UPDATE THE PACKAGE NAME to include your Student Identifier
package com.example.md_coursework1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.Configuration;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private ScrollView scrollView;
   private Toolbar myToolbar;
    private  TextView city;
    private HashMap<Integer, String> cityNamesMap = new HashMap<>();
    private Button nextButton;
    private Button previousButton;
    private Button moreInfoButton;
    private String url1 = "";
    private String[] urlSources = {
            "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/2648579",
            "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/5128581",
            "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/1185241",
            "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/2643743",
            "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/287286",
            "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/934154"
    };
    private  String [] threeDayUrlSources = {
            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579",
            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581",
            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241",
            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743",
            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286",
            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154"
    };
    private Queue<String> urlQueue;
    private Queue<String> threeDayUrlQueue;

    private String parseResult;
    private String parseResult2;
    private List<WeatherData> weatherDataList;

    private List<WeatherData3Day> weatherData3DayList;
    private ViewFlipper viewFlipper;
    private int cityCount;
private  TextView[] moreInfo = new TextView[18];
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView = (ScrollView)findViewById(R.id.scrollView);
        scrollView.setBackgroundColor(Color.parseColor("#00BFFF"));

        nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
        previousButton = (Button) findViewById(R.id.prevButton);
        previousButton.setOnClickListener(this);
        moreInfoButton = (Button) findViewById(R.id.moreInfo);
        moreInfoButton.setOnClickListener(this);

        city = (TextView) findViewById(R.id.cityName);

        viewFlipper = (ViewFlipper) findViewById(R.id.flip);
        viewFlipper.setInAnimation(this, android.R.anim.fade_in);
        viewFlipper.setOutAnimation(this, android.R.anim.fade_out);

        urlQueue = new LinkedList<>();
        Collections.addAll(urlQueue, urlSources);
        threeDayUrlQueue = new LinkedList<>();
        Collections.addAll(threeDayUrlQueue, threeDayUrlSources);

        weatherDataList = new ArrayList<>();
        weatherData3DayList = new ArrayList<>();

        cityCount = 1;
        SetCityNames();
        city.setText(GetCityNames(cityCount));

        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Choose city");

        startProgress();
        System.out.println("Create");
    }

    private  void SwitchedLayout()
    {
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        scrollView.setBackgroundColor(Color.parseColor("#00BFFF"));

        nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
        previousButton = (Button) findViewById(R.id.prevButton);
        previousButton.setOnClickListener(this);
        moreInfoButton = (Button) findViewById(R.id.moreInfo);
        moreInfoButton.setOnClickListener(this);

        city = (TextView) findViewById(R.id.cityName);

        viewFlipper = (ViewFlipper) findViewById(R.id.flip);
        viewFlipper.setInAnimation(this, android.R.anim.fade_in);
        viewFlipper.setOutAnimation(this, android.R.anim.fade_out);

        urlQueue = new LinkedList<>();
        Collections.addAll(urlQueue, urlSources);
        threeDayUrlQueue = new LinkedList<>();
        Collections.addAll(threeDayUrlQueue, threeDayUrlSources);

        weatherDataList = new ArrayList<>();
        weatherData3DayList = new ArrayList<>();

        cityCount = cityCount;
        SetCityNames();
        city.setText(GetCityNames(cityCount));

        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Choose city");

        startProgress();
    }


    public void onClick(View aview) {
        if (aview == nextButton) {
            viewFlipper.showNext();
            if (cityCount == 6)
                cityCount = 1;
            else
                cityCount = cityCount + 1;
        }
        if (aview == previousButton) {
            viewFlipper.showPrevious();
            if (cityCount == 1)
                cityCount = 6;
            else
                cityCount = cityCount - 1;
        }
        if(aview == moreInfoButton)
        {
            for (TextView textView : moreInfo) {
                if (textView != null) {
                    if (textView.getVisibility() == View.VISIBLE) {
                        textView.setVisibility(View.GONE);
                    } else {
                        textView.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
        city.setText(GetCityNames(cityCount));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_1);
            SwitchedLayout();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
            SwitchedLayout();
        }
    }

    public void startProgress() {
        if (!urlQueue.isEmpty()) {
            String nextUrl = urlQueue.poll();
            new Thread(new Task(nextUrl)).start();
        }

        if(!threeDayUrlQueue.isEmpty()){
           String nextUrl = threeDayUrlQueue.poll();
            new Thread(new Task2(nextUrl)).start();
        }
        else
        {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    displayThreeDayWeatherData();
                }
            });
        }
    }

    private class Task implements Runnable {
        private String url;

        public Task(String aurl) {
            url = aurl;
        }

        @Override
        public void run() {

            String result = "";
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";
            StringBuilder resultBuilder = new StringBuilder();

            //Log.e("MyTag", "in run");

            try {
               // Log.e("MyTag", "in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                while ((inputLine = in.readLine()) != null) {
                    result = result + inputLine;
                     resultBuilder.append(inputLine);
                   Log.e("MyTag", inputLine);

                }
                in.close();
            } catch (IOException ae) {
                Log.e("MyTag", "ioexception");
            }

            int i = result.indexOf(">");
            result = result.substring(i + 1);

            parseData(result);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    displayWeatherData();
                    startProgress();
                }
            });
        }
    }
    private class Task2 implements Runnable {
        private String url;

        public Task2(String aurl) {
            url = aurl;
        }

        @Override
        public void run() {

            String result = "";
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";
            StringBuilder resultBuilder = new StringBuilder();

            Log.e("MyTag", "in run");

            try {
                Log.e("MyTag", "in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                while ((inputLine = in.readLine()) != null) {
                    result = result + inputLine;
                     resultBuilder.append(inputLine);
                    Log.e("MyTag", inputLine);

                }
                in.close();
            } catch (IOException ae) {
            }

            int i = result.indexOf(">");
            result = result.substring(i + 1);

            parseData2(result);

        }
    }
    private void parseData(String dataToParse) {
        try {
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(new StringReader(dataToParse));
                    int eventType = xpp.getEventType();
                    List<String> weatherItems = new ArrayList<>();
                    String data = new String();

                    String city = "";
                    String time = "";
                    String temperature = "";
                    String windDir = "";
                    String windSpd = "";
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG)
                        {
                            if (xpp.getName().equalsIgnoreCase("item")) {
                                data = "" + "\n";

                            } else if (xpp.getName().equalsIgnoreCase("title")) {
                                String temp = xpp.nextText();
                                data = data + temp + "\n";
                                String[] parts = temp.split("-");
                                city = parts[0];
                                time = parts[1].split(": ")[0].trim();

                            } else if (xpp.getName().equalsIgnoreCase("description")) {
                                String temp = xpp.nextText();
                                data = data + temp + "\n";

                                String[] parts = temp.split(", ");

                                if (parts.length >= 3) {

                                    String[] temperature1 = parts[0].split(":");
                                    if (temperature1.length >= 2) {
                                        temperature = temperature1[1].trim();
                                    }

                                    String[] windDir1 = parts[1].split(":");
                                    if (windDir1.length >= 2) {
                                        windDir = windDir1[1].trim();
                                    }

                                    String[] windSpeedStr = parts[2].split(":");
                                    if (windSpeedStr.length >= 2) {
                                        windSpd = windSpeedStr[1].trim();
                                    }
                                } else {
                                    System.out.println("Unexpected data format: " + temp);
                                }
                            } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                                String temp = xpp.nextText();
                                data = data + temp + "\n";
                            }
                        } else if (eventType == XmlPullParser.END_TAG)
                        {
                            if (xpp.getName().equalsIgnoreCase("item")) {
                                weatherItems.add(data);
                            }
                        }
                        eventType = xpp.next();
                    }// End of while
                    parseResult = "" + weatherItems;
                    weatherDataList.add(new WeatherData(city, time,temperature, windDir, windSpd));

                } catch (XmlPullParserException ae1) {
                    Log.e("MyTag", "Parsing error" + ae1.toString());
                } catch (IOException ae1) {
                    Log.e("MyTag", "IO error during parsing");
                }

                Log.d("MyTag", "End of document reached");


            } // End of parseData

    private void parseData2(String dataToParse) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(dataToParse));
            int eventType = xpp.getEventType();
            List<String> weatherItems = new ArrayList<>();
            String data = new String();
            List<WeatherData3Day> cityWeatherList = new ArrayList<>();

            String city = "";
            String day = "";
            String minTemp = "";
            String maxTemp = "";
            String weather = "";
            String windDir = "";
            String windSpd = "";
            String visibility = "";
            String pressure = "";
            String humidity = "";
            String uvRisk = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase("item")) {
                        data = "" + "\n";

                    } else if (xpp.getName().equalsIgnoreCase("title")) {
                        String temp = xpp.nextText();
                        data = data + temp + "\n";

                        String[] parts = temp.split(":");
                        if (parts.length >= 2) {
                            day = parts[0].trim();

                            String[] weatherPart = parts[1].split(",");
                            if (weatherPart.length >= 1) {
                                weather = weatherPart[0].trim();
                            }
                        }

                        if (temp.contains("Minimum Temperature:")) {
                            minTemp = temp.substring(temp.indexOf("Minimum Temperature:") + "Minimum Temperature:".length())
                                    .split("C")[0].trim();
                        }

                        if (temp.contains("Maximum Temperature:")) {
                            maxTemp = temp.substring(temp.indexOf("Maximum Temperature:") + "Maximum Temperature:".length())
                                    .split("C")[0].trim();
                        }

                    } else if (xpp.getName().equalsIgnoreCase("description")) {
                        String temp = xpp.nextText();
                        data = data + temp + "\n";

                        String[] parts = temp.split(", ");
                        if(parts.length >= 4) {
                            String[] windDir1 = parts[2].split(":");
                            if (windDir1.length >= 2) {
                                windDir = windDir1[1].trim();
                            }
                            String[] windSpd1 = parts[3].split(":");
                            if (windSpd1.length >= 2) {
                                windSpd = windSpd1[1].trim();
                            }
                            String[] visibility1 = parts[4].split(":");
                            if (visibility1.length >= 2) {
                                visibility= visibility1[1].trim();
                            }
                            String[] pressure1 = parts[5].split(":");
                            if (pressure1.length >= 2) {
                                pressure = pressure1[1].trim();
                            }
                            String[] humidity1 = parts[6].split(":");
                            if (humidity1.length >= 2) {
                                humidity = humidity1[1].trim();
                            }
                            String[] uvRisk1 = parts[7].split(":");
                            if (uvRisk1.length >= 2) {
                                uvRisk = uvRisk1[1].trim();
                            }
                        }
                    } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                        String temp = xpp.nextText();
                        data = data + temp + "\n";
                    }
                } else if (eventType == XmlPullParser.END_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase("item")) {
                        WeatherData3Day threeDayData = new WeatherData3Day(day,weather,minTemp,maxTemp,windDir, windSpd,visibility,pressure,humidity,uvRisk);

                        System.out.println(threeDayData.Show3DayWeatherData());
                       // cityWeatherList.add(threeDayData);
                        weatherData3DayList.add(threeDayData);

                        weatherItems.add(data);
                    }
                }
                eventType = xpp.next();
            }
            parseResult2 = "" + weatherItems;

           // weatherData3DayList.add(new WeatherData3Day(city,day,time,minTemp,maxTemp,weather, windDir, windSpd,humidity));
           // System.out.println("parseData2");
           // System.out.println("Day " + day + " Weather " + weather + " min temp " + minTemp + " max temp" + maxTemp + " wind dir " + windDir + " wind speed " + windSpd);

        } catch (XmlPullParserException ae1) {
            Log.e("MyTag", "Parsing error" + ae1.toString());
        } catch (IOException ae1) {
            Log.e("MyTag", "IO error during parsing");
        }

        Log.d("MyTag", "End of document reached");
    } // End of parseData

    private void displayWeatherData() {
                for (int i = 0; i < weatherDataList.size(); i++) {
                    WeatherData weatherData = weatherDataList.get(i);
                    LinearLayout weatherLayout = (LinearLayout) viewFlipper.getChildAt(i);
                    if (weatherLayout != null) {
                        TextView dateTextView = (TextView) weatherLayout.findViewById(getResources().getIdentifier("date" + (i + 1), "id", getPackageName()));
                        TextView timeTextView = (TextView) weatherLayout.findViewById(getResources().getIdentifier("time" + (i + 1), "id", getPackageName()));
                        TextView temperatureTextView = (TextView) weatherLayout.findViewById(getResources().getIdentifier("temperature" + (i + 1), "id", getPackageName()));
                        TextView windDirectionTextView = (TextView) weatherLayout.findViewById(getResources().getIdentifier("windDirection" + (i + 1), "id", getPackageName()));
                        TextView windSpeedTextView = (TextView) weatherLayout.findViewById(getResources().getIdentifier("windSpeed" + (i + 1), "id", getPackageName()));


                        if (dateTextView != null) {
                            dateTextView.setText("" + weatherData.GetDay());
                        }
                        if (timeTextView != null) {
                            timeTextView.setText("" + weatherData.GetTime());
                        }
                        if (temperatureTextView != null) {
                            temperatureTextView.setText("" + weatherData.GetMaxT());
                        }
                        if (windDirectionTextView != null) {
                            windDirectionTextView.setText("" + weatherData.GetWindD());
                        }
                        if (windSpeedTextView != null) {
                            windSpeedTextView.setText("" + weatherData.GetWindS());
                        }

                       int time = SplitTime(weatherData.time);

                     if(time >= 6 && time < 18)
                     {
                         weatherLayout.setBackgroundColor(Color.parseColor("#00BFFF"));
                     }
                     else
                     {
                         weatherLayout.setBackgroundColor(Color.parseColor("#191970"));
                         dateTextView.setTextColor(Color.WHITE);
                         timeTextView.setTextColor(Color.WHITE);
                         temperatureTextView.setTextColor(Color.WHITE);
                         windDirectionTextView.setTextColor(Color.WHITE);
                         windSpeedTextView.setTextColor(Color.WHITE);
                     }

                    } else {
                        Log.e("Error", "LinearLayout at index " + i + " is null");
                    }
                }
    }

    private void displayThreeDayWeatherData() {
        int cityIndex = 0;
        int dayIndex = 0;

        for (int i = 0; i < weatherData3DayList.size(); i++) {
            WeatherData3Day weatherData = weatherData3DayList.get(i);

            LinearLayout cityWeatherLayout = (LinearLayout) viewFlipper.getChildAt(cityIndex);

            if (cityWeatherLayout == null) {
                cityIndex++;
                continue;
            }
            moreInfo[i] = cityWeatherLayout.findViewById(getResources().getIdentifier("infoD" + (dayIndex + 1) + (cityIndex + 1), "id", getPackageName()));

            LinearLayout linearLayout = cityWeatherLayout.findViewById(getResources().getIdentifier("linearLayoutD" + (dayIndex + 1) + (cityIndex + 1), "id", getPackageName()));

            if (linearLayout == null) {
                dayIndex++;
                continue;
            }

            TextView threeDayDate = linearLayout.findViewById(getResources().getIdentifier("dateD" + (dayIndex + 1) + (cityIndex + 1), "id", getPackageName()));
             ImageView threeDayWeather = linearLayout.findViewById(getResources().getIdentifier("weatherD" + (dayIndex + 1) + (cityIndex + 1), "id", getPackageName()));
            TextView threeDayMinT = linearLayout.findViewById(getResources().getIdentifier("minTempD" + (dayIndex + 1) + (cityIndex + 1), "id", getPackageName()));
             TextView threeDayMaxT = linearLayout.findViewById(getResources().getIdentifier("maxTempD" + (dayIndex + 1) + (cityIndex + 1), "id", getPackageName()));

            if (threeDayDate != null) {
                threeDayDate.setText(weatherData.GetDay());
                threeDayDate.setTextColor(Color.WHITE);
            }
            if (threeDayWeather != null) {
                if (weatherData.day.equals("Tonight")) {
                    switch (weatherData.GetWeather()) {
                        case "Sunny":
                            threeDayWeather.setImageResource(R.drawable.night_clear);
                            break;
                        case "Light Rain":
                            threeDayWeather.setImageResource(R.drawable.night_rain);
                            break;
                        case "Heavy Rain":
                            threeDayWeather.setImageResource(R.drawable.rain);
                            break;
                        case "Thundery Showers":
                            threeDayWeather.setImageResource(R.drawable.night_rain_thunder);
                            break;
                        case "Sunny Intervals":
                            threeDayWeather.setImageResource(R.drawable.night_partial_cloud);
                            break;
                        case "Drizzle":
                            threeDayWeather.setImageResource(R.drawable.night_rain);
                            break;
                        case "Light Rain Showers":
                            threeDayWeather.setImageResource(R.drawable.night_rain);
                            break;
                        case "Light Cloud":
                            threeDayWeather.setImageResource(R.drawable.overcast);
                            break;
                        case "Clear Sky":
                            threeDayWeather.setImageResource(R.drawable.night_clear);
                            break;
                        case "Partly Cloudy":
                            threeDayWeather.setImageResource(R.drawable.night_partial_cloud);
                            break;
                        default:
                            threeDayWeather.setImageResource(R.drawable.cloudy);
                            break;
                    }
                }
                 else {
                    switch (weatherData.GetWeather()) {
                        case "Sunny":
                            threeDayWeather.setImageResource(R.drawable.day_clear);
                            break;
                        case "Light Rain":
                            threeDayWeather.setImageResource(R.drawable.day_rain);
                            break;
                        case "Heavy Rain":
                            threeDayWeather.setImageResource(R.drawable.rain);
                            break;
                        case "Thundery Showers":
                            threeDayWeather.setImageResource(R.drawable.rain_thunder);
                            break;
                        case "Sunny Intervals":
                            threeDayWeather.setImageResource(R.drawable.day_partial_cloud);
                            break;
                        case "Drizzle":
                            threeDayWeather.setImageResource(R.drawable.day_rain);
                            break;
                        case "Light Rain Showers":
                            threeDayWeather.setImageResource(R.drawable.day_rain);
                            break;
                        case "Light Cloud":
                            threeDayWeather.setImageResource(R.drawable.overcast);
                            break;
                        case "Clear Sky":
                            threeDayWeather.setImageResource(R.drawable.day_clear);
                            break;
                        case "Parly Cloudy":
                            threeDayWeather.setImageResource(R.drawable.day_partial_cloud);
                            break;
                        default:
                            threeDayWeather.setImageResource(R.drawable.cloudy);
                            break;
                    }
                }
            }
            if (threeDayMinT != null) {
                threeDayMinT.setText(weatherData.GetMinT() + " / ");
                threeDayMinT.setTextColor(Color.WHITE);
            }
            if (threeDayMaxT != null) {
                threeDayMaxT.setText(weatherData.GetMaxT());
                threeDayMaxT.setTextColor(Color.WHITE);
            }

            if (moreInfo[i] != null) {
                moreInfo[i].setText(weatherData.day + " Wind: " + weatherData.windD + " " + weatherData.windS + " Visibility: " + weatherData.visibility + " Pressure: " + weatherData.pressure + " Humidity: " + weatherData.humidity + " UvRisk: " + weatherData.uvRisk);
                moreInfo[i].setTextColor(Color.WHITE);
                moreInfo[i].setVisibility(View.GONE);
            }

            dayIndex++;
            if (dayIndex == 3) {
                dayIndex = 0;
                cityIndex++;
            }
        }
    }


    private  void SetCityNames()
    {
        cityNamesMap.put(1, "Glasgow");
        cityNamesMap.put(2, "New York");
        cityNamesMap.put(3, "Bangladesh");
        cityNamesMap.put(4, "London");
        cityNamesMap.put(5, "Oman");
        cityNamesMap.put(6, "Mauritius");
    }

    private  String GetCityNames(int cityNumber)
    {
        return cityNamesMap.get(cityNumber);
    }
    private int SplitTime(String time)
    {
        String time1 = time.split(":")[0].trim();
        int time2 = Integer.parseInt(time1);
        return time2;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getTitle().toString()) {
            case "Glasgow":
                Toast.makeText(this, "Glasgow Weather", Toast.LENGTH_LONG).show();
                cityCount = 1;
                viewFlipper.setDisplayedChild(0);
                break;
            case "London":
                Toast.makeText(this, "London Weather", Toast.LENGTH_LONG).show();
                cityCount = 4;
                viewFlipper.setDisplayedChild(3);
                break;
            case "New York":
                Toast.makeText(this, "NewYork Weather", Toast.LENGTH_LONG).show();
                cityCount = 2;
                viewFlipper.setDisplayedChild(1);
                break;
            case "Oman":
                Toast.makeText(this, "Oman Weather", Toast.LENGTH_LONG).show();
                cityCount = 5;
                viewFlipper.setDisplayedChild(4);
                break;
            case "Mauritius":
                Toast.makeText(this, "Mauritius Weather", Toast.LENGTH_LONG).show();
                cityCount = 6;
                viewFlipper.setDisplayedChild(5);
                break;
            case "Bangladesh":Toast.makeText(this, "Bangladesh Weather", Toast.LENGTH_LONG).show();
                cityCount = 3;
                viewFlipper.setDisplayedChild(2);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        city.setText(GetCityNames(cityCount));
        return true;
    }
}





