package com.minidust.api.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class PollutionStationApi {
    private static final String API_KEY = "rD91vycFGdhMeipqQIuYBD4bhZKf/vYOFsxWwoWwWlV9HLbonxD22rOLOiuEokmR9Ge2b7qCrqNUpHzSz7W7hQ==";
    static HashMap<String, List<Double>> stationList = new HashMap<>();

    /**
     * 미세먼지 측정소 API에서 측정소, 위도, 경도 정보 가져오기
     */
    public void updateStation(String query) {
        RestTemplate rest = new RestTemplate();
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("apis.data.go.kr")
                .path("/B552584/MsrstnInfoInqireSvc/getMsrstnList")
                .queryParam("serviceKey", API_KEY)
                .queryParam("returnType", "json")
                .queryParam("numOfRows", "500")
                .queryParam("pageNo", "1")
                .queryParam("addr", query)
                .queryParam("ver", "1.0")
                .encode()
                .build();

        ResponseEntity<String> responseEntity;
        try {
            responseEntity = rest.getForEntity(uriComponents.toUri(), String.class);
        } catch (RestClientException e) {
            System.out.println("[INFO] " + new Date() + " 미세먼지 측정소 정보가 오류로 인해 업데이트 되지 않았습니다.");
            return;
        }

        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();

        JSONObject json = new JSONObject(response);
        JSONArray jsonArray = json.getJSONObject("response").getJSONObject("body").getJSONArray("items");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            stationList.put(jsonObject.getString("stationName"), Arrays.asList(jsonObject.getDouble("dmX"), jsonObject.getDouble("dmY")));
        }
    }
}
