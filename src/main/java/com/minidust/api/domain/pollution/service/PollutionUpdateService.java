package com.minidust.api.domain.pollution.service;

import com.minidust.api.domain.pollution.models.Pollution;
import com.minidust.api.domain.pollution.models.PollutionStation;
import com.minidust.api.domain.pollution.repository.PollutionRepository;
import com.minidust.api.domain.pollution.repository.PollutionStationRepository;
import com.minidust.api.domain.pollution.util.DustUpdater;
import com.minidust.api.domain.pollution.util.StationUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class PollutionUpdateService {
    private final DustUpdater dustUpdater;
    private final StationUpdater stationUpdater;

    private final PollutionRepository pollutionRepository;
    private final PollutionStationRepository pollutionStationRepository;

    public void updatePollutionData(String sidoName) {
        List<Pollution> pollutionDataList = dustUpdater.updateDust(sidoName);

        for (Pollution data : pollutionDataList) {
            Optional<Pollution> pollutionOptional = pollutionRepository.findById(data.getStationName());

            if (pollutionOptional.isPresent()) {
                pollutionOptional.get().update(data);
            } else {
                pollutionRepository.save(data);
            }
        }
    }

    public void updatePollutionStation(String sidoName) {
        List<PollutionStation> pollutionStationList = stationUpdater.updateStation(sidoName);
        pollutionStationRepository.saveAll(pollutionStationList);
    }
}

