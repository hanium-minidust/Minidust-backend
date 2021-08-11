package com.minidust.api.util;

import com.minidust.api.models.WeatherData;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class WeatherAPI {

//    https://openweathermap.org/current

    private final String API_KEY = "cd22e7cd9f97c714f98216e2dfa32791";

    public WeatherData getWeatherFromCoords(double longitude, double latitude) {
        RestTemplate rest = new RestTemplate();

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.openweathermap.org")
                .path("/data/2.5/weather")
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("appid", API_KEY)
                .queryParam("lang", "kr")
                .queryParam("units", "metric")
                .encode()
                .build();

        ResponseEntity<String> responseEntity = rest.getForEntity(uriComponents.toUri(), String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();

        JSONObject jsonObject = new JSONObject(response);
        String name = jsonObject.getString("name");
        String icon = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
        int temp = (int) jsonObject.getJSONObject("main").getFloat("temp");
        int humidity = (int) jsonObject.getJSONObject("main").getInt("humidity");
        return new WeatherData(name, "https://openweathermap.org/img/wn/" + icon + ".png", temp, humidity);
    }
}
