package com.minidust.api.domain.pollution.util;

import com.minidust.api.domain.pollution.models.PollutionStation;
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
public class StationFetcher {
    private final PollutionAPI pollutionAPI;

    public List<PollutionStation> fetchStation(String sidoName) {
        ArrayList<PollutionStation> result = new ArrayList<>();
        try {
            ResponseEntity<String> responseEntity = pollutionAPI.fetchByType(FetchType.STATION, sidoName);
            JSONArray jsonArray = pollutionAPI.parseToJsonArray(responseEntity);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                result.add(PollutionStation.builder()
                        .stationName(jsonObject.getString("stationName"))
                        .latitude(jsonObject.getDouble("dmX"))
                        .longitude(jsonObject.getDouble("dmY"))
                        .build());
            }

        } catch (RestClientException e) {
            log.warn("[미세먼지 측정소 API] RESTClientException 발생" + LocalDateTime.now());
        } catch (JSONException e) {
            log.warn("[미세먼지 측정소 API] JSONException 발생" + LocalDateTime.now());
        } catch (Exception e) {
            log.warn("[미세먼지 측정소 API] " + e + " 발생" + LocalDateTime.now());
        }

        return result;
    }
}
