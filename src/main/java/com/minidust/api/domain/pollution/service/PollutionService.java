package com.minidust.api.domain.pollution.service;

import com.minidust.api.domain.pollution.models.Pollution;
import com.minidust.api.domain.pollution.repository.PollutionRepository;
import com.minidust.api.domain.pollution.repository.PollutionStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PollutionService {

    private final PollutionRepository pollutionRepository;
    private final PollutionStationRepository pollutionStationRepository;

    public List<Pollution> findAllByName(String sidoName) {
        return pollutionRepository.findAllBySidoName(sidoName);
    }
}
