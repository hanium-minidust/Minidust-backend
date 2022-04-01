package com.minidust.api.domain.pollution.service;

import com.minidust.api.domain.pollution.models.PollutionStation;
import com.minidust.api.domain.pollution.repository.StationRepository;
import com.minidust.api.domain.pollution.util.StationFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationFetcher stationFetcher;
    private final StationRepository stationRepository;

    public PollutionStation findCoordsByName(String stationName) {
        PollutionStation pollutionStation = stationRepository.findByStationName(stationName);
        return pollutionStation;
    }

    public HashMap<String, PollutionStation> findCoordsBySidoName(String sidoName) {
        List<PollutionStation> stationList = stationRepository.findAllBysidoName(sidoName);
        HashMap<String, PollutionStation> stationMap = new HashMap<>();

        stationList.forEach(tmp -> stationMap.put(tmp.getStationName(), tmp));
        return stationMap;
    }

    public void updateStation(String sidoName) {
        List<PollutionStation> stationList = stationFetcher.fetchStation(sidoName);
        stationRepository.saveAll(stationList);
    }
}
