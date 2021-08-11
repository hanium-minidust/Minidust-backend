package com.minidust.api.service;

import com.minidust.api.models.PollutionData;
import com.minidust.api.repository.PollutionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollutionDataService {

    private final PollutionRepository pollutionRepository;

    public PollutionDataService(PollutionRepository pollutionRepository) {
        this.pollutionRepository = pollutionRepository;
    }

    public List<PollutionData> getAll() {
        return pollutionRepository.findAll();
    }

    public List<PollutionData> getAllByCity(String query) {
        return pollutionRepository.findAllBySidoName(query);
    }
}
