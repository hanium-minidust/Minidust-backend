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

import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Component
public class PollutionAPI {
    private final PollutionRepository pollutionRepository;
    private final String API_KEY = "rD91vycFGdhMeipqQIuYBD4bhZKf/vYOFsxWwoWwWlV9HLbonxD22rOLOiuEokmR9Ge2b7qCrqNUpHzSz7W7hQ==";

    //List<String> sidoName = Arrays.asList("서울", "부산", "대구", "인천", "광주", "대전", "울산", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주", "세종");
    List<String> sidoName = Arrays.asList("서울", "경기"); // 서울, 경기에 대해서 시험
    HashMap<String, List<Double>> stationList = new HashMap<>();

    @Transactional
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
                if (jsonArray.getJSONObject(i).isNull("pm25Value")
                        || jsonArray.getJSONObject(i).isNull("pm10Value")
                        || jsonArray.getJSONObject(i).getString("pm10Value").equals("-")
                        || jsonArray.getJSONObject(i).getString("pm25Value").equals("-")
                        || jsonArray.getJSONObject(i).getString("pm10Value").equals("통신장애")
                        || jsonArray.getJSONObject(i).getString("pm25Value").equals("통신장애")) {
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

            long id = stationName.hashCode() < 0 ? (long) (stationName.hashCode() * - 1) : (long) stationName.hashCode();
            // hashcode 의 값이 음수라면 양수로 변경해주기

            Optional<PollutionData> isExist = pollutionRepository.findById(id);

            PollutionData pollutionData = new PollutionData(
                    id,
                    jsonObject.getString("sidoName"),
                    jsonObject.getString("stationName"),
                    coords.get(0),
                    coords.get(1),
                    jsonObject.getInt("pm25Value"),
                    jsonObject.getInt("pm10Value"));

            if (isExist.isPresent()) {
                isExist.get().update(pollutionData);
            } else {
                pollutionRepository.save(pollutionData);
            }
        }
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
