package com.minidust.api.domain.pollution.service;

import com.minidust.api.domain.pollution.models.Pollution;
import com.minidust.api.domain.pollution.repository.PollutionRepository;
import com.minidust.api.domain.pollution.util.DustFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PollutionService {

    private final PollutionRepository pollutionRepository;
    private final DustFetcher dustFetcher;

    public List<Pollution> findAllByName(String sidoName) {
        return pollutionRepository.findAllBySidoName(sidoName);
    }

    public void updateDust(String sidoName) {
        List<Pollution> pollutionDataList = dustFetcher.fetchDust(sidoName);

        for (Pollution entity : pollutionDataList) {
            Optional<Pollution> pollutionOptional = pollutionRepository.findById(entity.getStationName());

            if (pollutionOptional.isPresent()) {
                pollutionOptional.get().update(entity);
            } else {
                pollutionRepository.save(entity);
            }
        }
    }
}
