package com.minidust.api.domain.pollution.util;

import com.minidust.api.domain.pollution.models.Pollution;
import com.minidust.api.domain.pollution.models.PollutionStation;
import com.minidust.api.domain.pollution.service.StationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class DustFetcher {
    private final StationService stationService;
    private final PollutionAPI pollutionAPI;
    private static HashMap<String, PollutionStation> stationMap;

    public ArrayList<Pollution> fetchDust(String sidoName) {
        JSONArray jsonArray = pollutionAPI.fetchByType(FetchType.PM, sidoName);

        ArrayList<Pollution> pollutionList = new ArrayList<>();
        stationMap = stationService.findCoordsBySidoName(sidoName);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Optional<Pollution> entity = convertJsonToEntity(jsonObject);
            entity.ifPresent(pollutionList::add);
        }

        return pollutionList;
    }

    private Optional<Pollution> convertJsonToEntity(JSONObject jsonObject) {
        try {
            PollutionStation station = stationMap.get(jsonObject.getString("stationName"));

            return Optional.of(Pollution.builder()
                    .stationName(jsonObject.getString("stationName"))
                    .sidoName(jsonObject.getString("sidoName"))
                    .latitude(station.getLatitude())
                    .longitude(station.getLongitude())
                    .pm10(jsonObject.getInt("pm10Value"))
                    .pm25(jsonObject.getInt("pm25Value"))
                    .build());

        } catch (JSONException e) {
            return Optional.empty();
        }
    }
}
