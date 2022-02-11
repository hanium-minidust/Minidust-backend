package com.minidust.api.domain.pollution.util;

import com.minidust.api.domain.pollution.dto.CoordsDto;
import com.minidust.api.domain.pollution.models.Pollution;
import com.minidust.api.domain.pollution.service.PollutionService;
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

@Component
@Slf4j
@RequiredArgsConstructor
public class DustFetcher {

    private final PollutionService pollutionService;
    private final PollutionAPI pollutionAPI;

    public ArrayList<Pollution> fetchDust(String sidoName) {
        ArrayList<Pollution> pollutionDataList = new ArrayList<>();
        try {
            ResponseEntity<String> responseEntity = pollutionAPI.fetchByType(FetchType.PM, sidoName);
            JSONArray jsonArray = pollutionAPI.parseToJsonArray(responseEntity);

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    CoordsDto coords = pollutionService.getCoordsByStationName(jsonObject.getString("stationName"));

                    pollutionDataList.add(Pollution.builder()
                            .stationName(jsonObject.getString("stationName"))
                            .sidoName(jsonObject.getString("sidoName"))
                            .latitude(coords.getLatitude())
                            .longitude(coords.getLongitude())
                            .pm10(jsonObject.getInt("pm10Value"))
                            .pm25(jsonObject.getInt("pm25Value"))
                            .build());

                } catch (JSONException e) {
                } // pm10Value or pm25Value 에 숫자값이 아닌 값이 있을경우 JSONException 발생 가능
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
