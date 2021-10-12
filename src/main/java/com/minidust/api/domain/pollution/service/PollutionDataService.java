package com.minidust.api.domain.pollution.service;

import com.minidust.api.domain.pollution.models.PollutionData;
import com.minidust.api.domain.pollution.repository.PollutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PollutionDataService {

    private final PollutionRepository pollutionRepository;

    public List<PollutionData> getAll() {
        return pollutionRepository.findAll();
    }

    public List<PollutionData> getAllByCity(String query) {
        return pollutionRepository.findAllBySidoName(query);
    }
}
