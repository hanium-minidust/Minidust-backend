package com.minidust.api.util;

import com.minidust.api.models.PollutionData;
import com.minidust.api.repository.PollutionRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Component
public class PollutionAPI {
    private final PollutionRepository pollutionRepository;
    private final String API_KEY = "rD91vycFGdhMeipqQIuYBD4bhZKf/vYOFsxWwoWwWlV9HLbonxD22rOLOiuEokmR9Ge2b7qCrqNUpHzSz7W7hQ==";

    //List<String> sidoName = Arrays.asList("서울", "부산", "대구", "인천", "광주", "대전", "울산", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주", "세종");
    List<String> sidoName = Arrays.asList("서울", "경기"); // 서울, 경기에 대해서 시험
    HashMap<String, List<Double>> stationList = new HashMap<>();

    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 0 * * * *")
    public void updatePollutionData(String query) {
        updateStation(query); // TODO 이렇게 쓰면 트래픽이 너무 많다. 일단 시작할때 이거랑 같이 한번 돌고, 매달 한번씩만, 배포할때 수정하자.
        List<PollutionData> returnValue = new ArrayList<>();
        RestTemplate rest = new RestTemplate();

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("apis.data.go.kr")
                .path("/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty")
                .queryParam("serviceKey", API_KEY)
                .queryParam("returnType", "json")
                .queryParam("numOfRows", "500")
                .queryParam("pageNo", "1")
                .queryParam("sidoName", query)
                .queryParam("ver", "1.0")
                .encode()
                .build();

        ResponseEntity<String> responseEntity = rest.getForEntity(uriComponents.toUri(), String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
//        int status = httpStatus.value(); TODO 추가적인 오류 핸들링이 필요하다.
        String response = responseEntity.getBody();

        JSONObject json = new JSONObject(response);
        JSONArray jsonArray = json.getJSONObject("response").getJSONObject("body").getJSONArray("items");

        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.getJSONObject(i).getString("pm10Value").equals("-") || jsonArray.getJSONObject(i).getString("pm25Value").equals("-")) {
                continue; // TODO pm10Value 나 pm25Value 에 Int 가 아니라, 장비점검이나 "-" 가 들어가 있는 경우 대처가 필요하다.
            }

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String stationName = jsonObject.getString("stationName");
            List<Double> coords = stationList.get(stationName);
            PollutionData pollutionData = new PollutionData(
                    jsonObject.getString("sidoName"),
                    jsonObject.getString("stationName"),
                    coords.get(0),
                    coords.get(1),
                    jsonObject.getInt("pm25Value"),
                    jsonObject.getInt("pm10Value"));
            pollutionRepository.save(pollutionData);
        }
        // TODO 정상적으로 업데이트가 되었는지 확인값도 필요하다.
    }

    //@Scheduled(cron = "0 0 1 1 * *") // 매달 1일 새벽 1시에 업데이트
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

        ResponseEntity<String> responseEntity = rest.getForEntity(uriComponents.toUri(), String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();

        System.out.println(response);
        JSONObject json = new JSONObject(response);
        JSONArray jsonArray = json.getJSONObject("response").getJSONObject("body").getJSONArray("items");
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            stationList.put(jsonObject.getString("stationName"), Arrays.asList(jsonObject.getDouble("dmY"), jsonObject.getDouble("dmX")));
        }
    }
}
