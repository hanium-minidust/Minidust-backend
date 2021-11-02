package com.minidust.api.domain.pollution.service;

import com.minidust.api.domain.pollution.models.PollutionData;
import com.minidust.api.domain.pollution.repository.PollutionRepository;
import com.minidust.api.domain.pollution.util.PollutionDataApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PollutionApiService {
    private final PollutionDataApi pollutionDataApi;

    private final PollutionRepository pollutionRepository;
    List<String> cityName = Arrays.asList("서울", "경기");

    public void updatePollutionData(String query) {
        List<PollutionData> pollutionDataList = pollutionDataApi.updatePollutionData(query);
        pollutionRepository.saveAll(pollutionDataList);
    }
}
