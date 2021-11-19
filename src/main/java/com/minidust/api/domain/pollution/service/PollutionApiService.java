package com.minidust.api.domain.pollution.service;

import com.minidust.api.domain.pollution.models.PollutionData;
import com.minidust.api.domain.pollution.models.PollutionStation;
import com.minidust.api.domain.pollution.repository.PollutionRepository;
import com.minidust.api.domain.pollution.repository.PollutionStationRepository;
import com.minidust.api.domain.pollution.util.PollutionDataApi;
import com.minidust.api.domain.pollution.util.PollutionStationApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PollutionApiService {
    private final PollutionDataApi pollutionDataApi;
    private final PollutionStationApi pollutionStationApi;

    private final PollutionRepository pollutionRepository;
    private final PollutionStationRepository pollutionStationRepository;

    @Transactional
    public void updatePollutionData(String query) {
        List<PollutionData> pollutionDataList = pollutionDataApi.updatePollutionData(query);
        for (PollutionData data : pollutionDataList) {
            Optional<PollutionData> isExist = pollutionRepository.findById(data.getId());
            if (isExist.isPresent()) {
                isExist.get().update(data);
            } else {
                pollutionRepository.save(data);
            }
        }
    }

    public void updatePollutionStation(String query) {
        List<PollutionStation> pollutionStationList = pollutionStationApi.updateStation(query);
        pollutionStationRepository.saveAll(pollutionStationList);
    }
}

