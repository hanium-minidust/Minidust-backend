package com.minidust.api.domain.pollution.service;

import com.minidust.api.domain.pollution.models.PollutionData;
import com.minidust.api.domain.pollution.models.PollutionStation;
import com.minidust.api.domain.pollution.repository.PollutionRepository;
import com.minidust.api.domain.pollution.repository.PollutionStationRepository;
import com.minidust.api.domain.pollution.util.PollutionDataApi;
import com.minidust.api.domain.pollution.util.PollutionStationApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PollutionApiService {
    private final PollutionDataApi pollutionDataApi;
    private final PollutionStationApi pollutionStationApi;

    private final PollutionRepository pollutionRepository;
    private final PollutionStationRepository pollutionStationRepository;
    List<String> cityName = Arrays.asList("서울", "경기");

    public void updatePollutionData(String query) {
        List<PollutionData> pollutionDataList = pollutionDataApi.updatePollutionData(query);
        pollutionRepository.saveAll(pollutionDataList);
    }

    public void updatePollutionStation(String query) {
        List<PollutionStation> pollutionStationList = pollutionStationApi.updateStation(query);
        pollutionStationRepository.saveAll(pollutionStationList);
    }
}
