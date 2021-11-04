package com.minidust.api.domain.pollution.util;

import com.minidust.api.domain.pollution.models.PollutionStation;
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
import java.util.HashMap;
import java.util.List;

@Component
public class PollutionStationApi {
    private static final String API_KEY = "rD91vycFGdhMeipqQIuYBD4bhZKf/vYOFsxWwoWwWlV9HLbonxD22rOLOiuEokmR9Ge2b7qCrqNUpHzSz7W7hQ==";
    static HashMap<String, List<Double>> stationList = new HashMap<>();

    /**
     * 미세먼지 측정소 API에서 측정소, 위도, 경도 정보 가져오기
     */
    public List<PollutionStation> updateStation(String query) {
        ArrayList<PollutionStation> result = new ArrayList<>();
        try {
            ResponseEntity<String> responseEntity = fetchPollutionStationApi(query);
            HttpStatus httpStatus = responseEntity.getStatusCode();
            int status = httpStatus.value();

            if (status != 200) {
                throw new RestClientException("미세먼지 측정소 API 서버가 200 을 반환하지 않았습니다.");
            }

            JSONArray jsonArray = entityToJsonArray(responseEntity); // ResponseEntity -> JsonArray
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                PollutionStation pollutionStation = jsonObjectToPollutionStationObject(jsonObject);
                result.add(pollutionStation);
            }

        } catch (RestClientException restClientException) {
            System.out.println("미세먼지 측정소 업데이트 부분 RESTClientException 발생");
        } catch (JSONException jsonException) {
            System.out.println("미세먼지 측정소 업데이트 부분 JSONException 발생");
        } catch (Exception exception) {
            System.out.println("[WARN]" + new Date() + "미세먼지 측정소 업데이트 부분 Exception 발생");
            exception.printStackTrace();
        }

        return result;
    }

    private ResponseEntity<String> fetchPollutionStationApi(String query) {
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

        return rest.getForEntity(uriComponents.toUri(), String.class);
    }

    private JSONArray entityToJsonArray(ResponseEntity<String> responseEntity) {
        String response = responseEntity.getBody();
        JSONObject json = new JSONObject(response);
        return json.getJSONObject("response").getJSONObject("body").getJSONArray("items");
    }

//    private void savePollutionStationByJsonArray(JSONArray jsonArray) {
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            stationList.put(jsonObject.getString("stationName"), Arrays.asList(jsonObject.getDouble("dmX"), jsonObject.getDouble("dmY")));
//            PollutionStation tmp = PollutionStation.builder()
//                    .stationName(jsonObject.getString("stationName"))
//                    .latitude(jsonObject.getDouble("dmX"))
//                    .longitude(jsonObject.getDouble("dmY"))
//                    .build();
//
//            return tmp;
//        }
//    }

    private PollutionStation jsonObjectToPollutionStationObject(JSONObject jsonObject) {
        PollutionStation tmp = PollutionStation.builder()
                .stationName(jsonObject.getString("stationName"))
                .latitude(jsonObject.getDouble("dmX"))
                .longitude(jsonObject.getDouble("dmY"))
                .build();

        return tmp;
    }
}
