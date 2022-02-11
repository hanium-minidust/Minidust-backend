package com.minidust.api.domain.pollution.service;

import com.minidust.api.domain.pollution.models.Pollution;
import com.minidust.api.domain.pollution.models.PollutionStation;
import com.minidust.api.domain.pollution.repository.PollutionRepository;
import com.minidust.api.domain.pollution.repository.PollutionStationRepository;
import com.minidust.api.domain.pollution.util.DustFetcher;
import com.minidust.api.domain.pollution.util.StationFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class PollutionUpdateService {
    private final DustFetcher dustFetcher;
    private final StationFetcher stationFetcher;

    private final PollutionRepository pollutionRepository;
    private final PollutionStationRepository pollutionStationRepository;

    public void updateDust(String sidoName) {
        List<Pollution> pollutionDataList = dustFetcher.fetchDust(sidoName);

        for (Pollution data : pollutionDataList) {
            Optional<Pollution> pollutionOptional = pollutionRepository.findById(data.getStationName());

            if (pollutionOptional.isPresent()) {
                pollutionOptional.get().update(data);
            } else {
                pollutionRepository.save(data);
            }
        }
    }

    public void updateStation(String sidoName) {
        List<PollutionStation> pollutionStationList = stationFetcher.fetchStation(sidoName);
        pollutionStationRepository.saveAll(pollutionStationList);
    }
}

