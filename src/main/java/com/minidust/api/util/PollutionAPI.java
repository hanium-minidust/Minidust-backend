package com.minidust.api.util;

import com.minidust.api.models.PollutionData;
import com.minidust.api.repository.PollutionRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@RequiredArgsConstructor
@Component
public class PollutionAPI {
    private final PollutionRepository pollutionRepository;
    private final String API_KEY = "rD91vycFGdhMeipqQIuYBD4bhZKf/vYOFsxWwoWwWlV9HLbonxD22rOLOiuEokmR9Ge2b7qCrqNUpHzSz7W7hQ==";

    //List<String> sidoName = Arrays.asList("서울", "부산", "대구", "인천", "광주", "대전", "울산", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주", "세종");
    List<String> sidoName = Arrays.asList("서울", "경기"); // 서울, 경기에 대해서 시험
    HashMap<String, List<Double>> stationList = new HashMap<>();

    public void updatePollutionData(String query) {
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
            try {
                if (Objects.isNull(jsonArray.getJSONObject(i).get("pm25Value"))
                        || Objects.isNull(jsonArray.getJSONObject(i).get("pm10Value"))
                        || jsonArray.getJSONObject(i).getString("pm10Value").equals("-")
                        || jsonArray.getJSONObject(i).getString("pm25Value").equals("-")) {
                    continue;
                }
            } catch (Exception e) {
                System.out.println(jsonArray.getJSONObject(i));
                System.out.println(e.getMessage());
                continue;
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

        JSONObject json = new JSONObject(response);
        JSONArray jsonArray = json.getJSONObject("response").getJSONObject("body").getJSONArray("items");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            stationList.put(jsonObject.getString("stationName"), Arrays.asList(jsonObject.getDouble("dmY"), jsonObject.getDouble("dmX")));
        }
    }
}
