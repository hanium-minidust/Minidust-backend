package com.minidust.api.domain.map.service;

import com.minidust.api.domain.map.dto.AddrToCoordsDto;
import com.minidust.api.domain.map.dto.CoordsToAddrDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MapService {
    private static final String API_KEY_ID = "37k5obr4wx";
    private static final String API_KEY = "HVDyYjmhhaJ0AROI4cmdBI3HINusYR3ewPmFKyZq";

    /**
     * 주소 -> 경도(Longitude), 위도(Latitude)
     *
     * @param query 주소 키워드를 입력받습니다.
     * @return 경도(X) LONGITUDE, 위도(Y) LATITUDE 가 담긴 리스트를 리턴합니다.
     */
    public static AddrToCoordsDto getCoordsFromAddress(String query) {
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

        JSONObject json = new JSONObject(response);
        JSONArray values = json.getJSONArray("addresses");

        // 결과값이 존재하지 않는 경우
        if (values.length() == 0) {
            throw new IllegalArgumentException("해당 주소로 조회할 수 없습니다.");
        }
        JSONObject jsonObject = (JSONObject) values.get(0);
        double longitude = jsonObject.getDouble("x");
        double latitude = jsonObject.getDouble("y");

        return AddrToCoordsDto.builder().longitude(longitude).latitude(latitude).build();
    }

    /**
     * 경도(Longitude), 위도(Latitude) -> 주소
     *
     * @param longitude 경도를 인자로 받습니다.
     * @param latitude  위도를 인자로 받습니다.
     * @return 도, 시, 동의 순서가 담긴 String의 List가 리턴됩니다. ex) [경기도, 안성시, 당왕동]
     */
    public static CoordsToAddrDto getAddressFromCoordinates(double longitude, double latitude) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-NCP-APIGW-API-KEY-ID", API_KEY_ID);
        headers.add("X-NCP-APIGW-API-KEY", API_KEY);
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?request=coordsToaddr&coords="
                + longitude + "," + latitude + "&output=json", HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        String response = responseEntity.getBody();

        JSONObject json = new JSONObject(response);
        JSONArray values = json.getJSONArray("results");

        // 결과값이 존재하지 않는 경우
        if (values.length() == 0) {
            throw new IllegalArgumentException("해당 좌표로 주소를 조회할 수 없습니다.");
        }

        JSONObject jsonObject = values.getJSONObject(0).getJSONObject("region");
        String firstAddress = jsonObject.getJSONObject("area1").getString("name"); // name -> 경기도, alias -> 경기, 기상 API 에 따라 바꿔써야 할 부분
        String firstAlias = jsonObject.getJSONObject("area1").getString("alias");
        String secondAddress = jsonObject.getJSONObject("area2").getString("name"); // 시 단위
        String thirdAddress = jsonObject.getJSONObject("area3").getString("name"); // 동 단위

        return new CoordsToAddrDto(firstAddress, firstAlias, secondAddress, thirdAddress);
    }

    public static boolean isCorrectCoords(double longitude, double latitude) {
        int longitudeInt = (int) longitude; // 123~133
        int latitudeInt = (int) latitude; // 32~44
        return longitudeInt < 134 && longitudeInt > 122 && latitudeInt < 45 && latitudeInt > 31;
    }
}