package com.minidust.api.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class BiCoordsToAddr {
    private static final String API_KEY_ID = "37k5obr4wx";
    private static final String API_KEY = "HVDyYjmhhaJ0AROI4cmdBI3HINusYR3ewPmFKyZq";

    // 둘에 대해 검색결과가 없는 경우에 대한 처리

    /**
     * 주소 -> 경도(Longitude), 위도(Latitude)
     *
     * @param query 주소 키워드를 입력받습니다.
     * @return 경도(X) LONGITUDE, 위도(Y) LATITUDE 가 담긴 리스트를 리턴합니다.
     */
    public static List<Double> getCoordsFromAddress(String query) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-NCP-APIGW-API-KEY-ID", API_KEY_ID);
        headers.add("X-NCP-APIGW-API-KEY", API_KEY);
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" +
                query, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();

        // TODO 여기에서 JSONException 발생 할텐데.
        JSONObject json = new JSONObject(response);
        JSONArray values = json.getJSONArray("addresses");
        if (values.length() == 0) {
            return Collections.emptyList();
        }
        JSONObject jsonObject = (JSONObject) values.get(0);
        Double longitude = jsonObject.getDouble("x");
        Double latitude = jsonObject.getDouble("y");

        return Arrays.asList(longitude, latitude);
    }

    /**
     * 경도(Longitude), 위도(Latitude) -> 주소
     *
     * @param longitude 경도를 인자로 받습니다.
     * @param latitude  위도를 인자로 받습니다.
     * @return 도, 시, 동의 순서가 담긴 String의 List가 리턴됩니다. ex) [경기도, 안성시, 당왕동]
     */
    public static List<String> getAddressFromCoordinates(Double longitude, Double latitude) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-NCP-APIGW-API-KEY-ID", API_KEY_ID);
        headers.add("X-NCP-APIGW-API-KEY", API_KEY);
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?request=coordsToaddr&coords="
                + longitude + "," + latitude + "&output=json", HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
//        int status = httpStatus.value(); TODO 추가적인 오류 핸들링이 필요하다.
        String response = responseEntity.getBody();

        JSONObject json = new JSONObject(response);
        JSONArray values = json.getJSONArray("results");
        JSONObject jsonObject = values.getJSONObject(0).getJSONObject("region");
        String firstAddress = jsonObject.getJSONObject("area1").getString("name"); // name -> 경기도, alias -> 경기, 기상 API 에 따라 바꿔써야 할 부분
        String firstAlias = jsonObject.getJSONObject("area1").getString("alias");
        String secondAddress = jsonObject.getJSONObject("area2").getString("name"); // 시 단위
        String thirdAddress = jsonObject.getJSONObject("area3").getString("name"); // 동 단위

        return Arrays.asList(firstAddress, firstAlias, secondAddress, thirdAddress);
    }
}