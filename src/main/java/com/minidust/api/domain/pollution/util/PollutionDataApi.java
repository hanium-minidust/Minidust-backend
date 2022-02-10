package com.minidust.api.domain.pollution.util;

import com.minidust.api.domain.pollution.models.Pollution;
import com.minidust.api.domain.pollution.service.PollutionDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class PollutionDataApi {
    private static final String API_KEY = "rD91vycFGdhMeipqQIuYBD4bhZKf/vYOFsxWwoWwWlV9HLbonxD22rOLOiuEokmR9Ge2b7qCrqNUpHzSz7W7hQ==";

    private final PollutionDataService pollutionDataService;
    private final PollutionAPI pollutionAPI;

    /**
     * 미세먼지 API에서 미세먼지 정보 가져오기
     * PollutionData 리스트를 받아서, 해당 리스트를 서비스로 통째로 넘겨서 데이터베이스에 올립시다.
     */
    public ArrayList<Pollution> updateDust(String query) {
        ArrayList<Pollution> pollutionDataList = new ArrayList<>();
        try {
            ResponseEntity<String> responseEntity = pollutionAPI.fetchByType(FetchType.PM, query);
            JSONArray jsonArray = pollutionAPI.parseToJsonArray(responseEntity);

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    List<Double> coords = pollutionDataService.getCoordsByStationName(jsonObject.getString("stationName"));

                    pollutionDataList.add(Pollution.builder()
                            .stationName(jsonObject.getString("stationName"))
                            .sidoName(jsonObject.getString("sidoName"))
                            .latitude(coords.get(0))
                            .longitude(coords.get(1))
                            .pm10(jsonObject.getInt("pm10Value"))
                            .pm25(jsonObject.getInt("pm25Value"))
                            .build());

                } catch (JSONException e) { // pm10Value or pm25Value 에 숫자값이 아닌 값이 있을경우 JSONException 발생 가능
//                    continue;
                }
            }
        } catch (RestClientException e) { // 서버에 접속이 불가능할 경우(500 에러 등)
            log.warn("[미세먼지 측정 API] RESTClientException 발생" + LocalDateTime.now());
        } catch (JSONException e) { // ResponseEntity -> JSON 과정에서 JSON 형식이 아닌것을 받아 왔을때 발생(통신장애, XML 받음 등)
            log.warn("[미세먼지 측정 API] JSONException 발생" + LocalDateTime.now());
        } catch (Exception e) { // 예상치 못한 예외 발생으로 서버 다운을 방지
            log.warn("[미세먼지 측정 API]" + e.getCause().toString() + " 발생" + LocalDateTime.now());
        }

        return pollutionDataList;
    }
}
