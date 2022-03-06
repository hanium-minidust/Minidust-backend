package com.minidust.api.domain.pollution.service;

import com.minidust.api.domain.pollution.models.PollutionStation;
import com.minidust.api.domain.pollution.repository.PollutionStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StationService {

    private final PollutionStationRepository pollutionStationRepository;

    public PollutionStation findCoordsByName(String stationName) {
        PollutionStation pollutionStation = pollutionStationRepository.findByStationName(stationName);
        return pollutionStation;
    }

    public HashMap<String, PollutionStation> findAllCoordsBySidoName(String sidoName) {
        List<PollutionStation> stationInSido = pollutionStationRepository.findAllBysidoName(sidoName);
        HashMap<String, PollutionStation> stationMap = new HashMap<>();

        for (PollutionStation station : stationInSido) {
            stationMap.put(station.getStationName(), station);
        }

        return stationMap;
    }
}
