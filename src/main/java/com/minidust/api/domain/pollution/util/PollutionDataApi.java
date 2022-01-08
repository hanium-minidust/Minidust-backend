package com.minidust.api.domain.pollution.util;

import com.minidust.api.domain.pollution.models.PollutionData;
import com.minidust.api.domain.pollution.service.PollutionDataService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PollutionDataApi {
    private static final String API_KEY = "rD91vycFGdhMeipqQIuYBD4bhZKf/vYOFsxWwoWwWlV9HLbonxD22rOLOiuEokmR9Ge2b7qCrqNUpHzSz7W7hQ==";

    private final PollutionDataService pollutionDataService;

    /**
     * 미세먼지 API에서 미세먼지 정보 가져오기
     * PollutionData 리스트를 받아서, 해당 리스트를 서비스로 통째로 넘겨서 데이터베이스에 올립시다.
     */
    public ArrayList<PollutionData> updatePollutionData(String query) {
        ResponseEntity<String> responseEntity;
        ArrayList<PollutionData> pollutionDataList = new ArrayList<>();
        try {
            // 서버가 500 오류를 가질 경우 RestClientException 발생 가능
            responseEntity = fetchPollutionDataFromApi(query);

            // JSON이 아닌 형태로 리턴될 경우 JSONException 발생 가능
            JSONArray jsonArray = entityToJsonArray(responseEntity);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    PollutionData pollutionData = JsonObjectToPollutionData(jsonArray.getJSONObject(i));
                    pollutionDataList.add(pollutionData);
                } catch (JSONException e) { // pm10Value or pm25Value 에 숫자값이 아닌 값이 있을경우 JSONException 발생 가능
//                    continue;
                }
            }
        } catch (RestClientException e) { // 서버에 접속이 불가능할 경우(500 에러 등)
            System.out.println("미세먼지 데이터 업데이트 부분 RESTClientException 발생");
        } catch (JSONException e) { // ResponseEntity -> JSON 과정에서 JSON 형식이 아닌것을 받아 왔을때 발생(통신장애, XML 받음 등)
            System.out.println("미세먼지 데이터 업데이트 부분 JSONException 발생");
        } catch (Exception e) { // 예상치 못한 예외 발생으로 서버 다운을 방지
            System.out.println("[WARN]" + new Date() + "미세먼지 데이터 업데이트 부분 Exception 발생");
            e.printStackTrace();
        }
        return pollutionDataList;
    }

    // API에서 정보 가져오기
    private ResponseEntity<String> fetchPollutionDataFromApi(String query) throws RestClientException {
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

        return rest.getForEntity(uriComponents.toUri(), String.class);
    }

    // 가져온 정보를 JSONArray 형태로 변환하기(responseEntity -> 진짜 데이터가 있는 JSONArray인 items까지)
    private JSONArray entityToJsonArray(ResponseEntity<String> responseEntity) throws JSONException, RestClientException {
        String response = responseEntity.getBody();
        HttpStatus httpStatus = responseEntity.getStatusCode();
        if (!httpStatus.is2xxSuccessful()) {
            throw new RestClientException("미세먼지 측정소 서버가 200을 리턴하지 않았습니다.");
        }
        JSONObject json = new JSONObject(response);
        return json.getJSONObject("response").getJSONObject("body").getJSONArray("items");
    }

    // JSONArray 내부의 JSONObject 들을 PollutionData 객체로 맵핑하기
    private PollutionData JsonObjectToPollutionData(JSONObject jsonObject) throws JSONException {
        String stationName = jsonObject.getString("stationName");
        List<Double> coords = pollutionDataService.getCoordsByStationName(stationName);

        PollutionData pollutionData = PollutionData.builder()
                .stationName(jsonObject.getString("stationName"))
                .sidoName(jsonObject.getString("sidoName"))
                .latitude(coords.get(0))
                .longitude(coords.get(1))
                .pm10(jsonObject.getInt("pm10Value"))
                .pm25(jsonObject.getInt("pm25Value"))
                .build();

        return pollutionData;
    }
}
