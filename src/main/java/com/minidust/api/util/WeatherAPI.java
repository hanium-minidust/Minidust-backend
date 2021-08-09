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
    // 날씨를 1시간 단위로 업데이트해서 DB에 저장하자.
    /*
    https://api.openweathermap.org/data/2.5/weather?q=도시이름&appid=cd22e7cd9f97c714f98216e2dfa32791
    api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
    https://openweathermap.org/current
    날씨에서는, 일단 본인의 현재 위치에 따른 날씨를 위해서 geolocation 을 통해서 api에서 날씨를 받아오자.
    그리고 기기에서 보내주는 온/습도는 기기 내용 출력할때 같이 나갈거.

    캘빈도 - 273.15 = celsius.
     */

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
        System.out.println(jsonObject);
        String name = jsonObject.getString("name");
        String icon = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
        int temp = (int) jsonObject.getJSONObject("main").getFloat("temp");
        int humidity = (int) jsonObject.getJSONObject("main").getInt("humidity");
        return new WeatherData(name, "https://openweathermap.org/img/wn/" + icon + ".png", temp, humidity);
    }
}
