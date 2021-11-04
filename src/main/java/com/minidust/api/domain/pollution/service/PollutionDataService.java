package com.minidust.api.domain.pollution.service;

import com.minidust.api.domain.pollution.models.PollutionData;
import com.minidust.api.domain.pollution.models.PollutionStation;
import com.minidust.api.domain.pollution.repository.PollutionRepository;
import com.minidust.api.domain.pollution.repository.PollutionStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PollutionDataService {

    private final PollutionRepository pollutionRepository;
    private final PollutionStationRepository pollutionStationRepository;

    public List<PollutionData> getAll() {
        return pollutionRepository.findAll();
    }

    public List<PollutionData> getAllByCity(String query) {
        return pollutionRepository.findAllBySidoName(query);
    }

    public List<Double> getCoordsByStationName(String query) {
        PollutionStation pollutionStation = pollutionStationRepository.findByStationName(query);
        return Arrays.asList(pollutionStation.getLatitude(), pollutionStation.getLongitude());
    }
}
