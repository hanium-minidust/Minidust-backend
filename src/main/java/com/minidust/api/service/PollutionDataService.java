package com.minidust.api.service;

import com.minidust.api.models.PollutionData;
import com.minidust.api.repository.PollutionRepository;
import com.minidust.api.util.PollutionAPI;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollutionDataService {

    private final PollutionRepository pollutionRepository;
    private final PollutionAPI pollutionAPI;

    public PollutionDataService(PollutionRepository pollutionRepository, PollutionAPI pollutionAPI) {
        this.pollutionRepository = pollutionRepository;
        this.pollutionAPI = pollutionAPI;
    }

    public void createData(PollutionData pollutionData) {
        pollutionRepository.save(pollutionData);
    }

    public List<PollutionData> getDataByCity(String query) {
        return pollutionRepository.findAllBySidoName(query);
    }
}
