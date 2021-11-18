package com.minidust.api.domain.pollution.service;

import com.minidust.api.domain.pollution.models.PollutionData;
import com.minidust.api.domain.pollution.models.PollutionStation;
import com.minidust.api.domain.pollution.repository.PollutionRepository;
import com.minidust.api.domain.pollution.repository.PollutionStationRepository;
import com.minidust.api.domain.pollution.util.PollutionDataApi;
import com.minidust.api.domain.pollution.util.PollutionStationApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PollutionApiService {
    private final PollutionDataApi pollutionDataApi;
    private final PollutionStationApi pollutionStationApi;

    private final PollutionRepository pollutionRepository;
    private final PollutionStationRepository pollutionStationRepository;

    // TODO 이렇게 처리하면 기존에 존재했던게 업데이트가 불가능하다.

    public void updatePollutionData(String query) {
        List<PollutionData> pollutionDataList = pollutionDataApi.updatePollutionData(query);
        // 리스트에서 꺼내서 기존 데이터가 있으면 업데이트, 없으면 새로 추가하는 로직이 필요하다.
        pollutionRepository.saveAll(pollutionDataList);   }

    public void updatePollutionStation(String query) {
        List<PollutionStation> pollutionStationList = pollutionStationApi.updateStation(query);
        pollutionStationRepository.saveAll(pollutionStationList);
    }
}

