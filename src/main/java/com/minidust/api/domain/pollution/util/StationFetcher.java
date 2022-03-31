package com.minidust.api.domain.pollution.util;

import com.minidust.api.domain.pollution.models.PollutionStation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class StationFetcher {
    private final PollutionAPI pollutionAPI;

    public List<PollutionStation> fetchStation(String sidoName) {
        ArrayList<PollutionStation> stationList = new ArrayList<>();
        JSONArray jsonArray = pollutionAPI.fetchByType(FetchType.STATION, sidoName);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Optional<PollutionStation> entity = convertJsonToEntity(jsonObject, sidoName);
            entity.ifPresent(stationList::add);
        }

        return stationList;
    }

    private Optional<PollutionStation> convertJsonToEntity(JSONObject jsonObject, String sidoName) {
        try {
            return Optional.of(PollutionStation.builder()
                    .stationName(jsonObject.getString("stationName"))
                    .latitude(jsonObject.getDouble("dmX"))
                    .longitude(jsonObject.getDouble("dmY"))
                    .sidoName(sidoName)
                    .build());
        } catch (RestClientException restClientException) {
            return Optional.empty();
        }
    }
}
