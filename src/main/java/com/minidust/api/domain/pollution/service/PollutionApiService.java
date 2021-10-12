package com.minidust.api.domain.pollution.service;

import com.minidust.api.domain.pollution.models.PollutionData;
import com.minidust.api.domain.pollution.repository.PollutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PollutionApiService {

    private final PollutionRepository pollutionRepository;

    @Transactional
    public void uploadData(PollutionData pollutionData) {
        long id = pollutionData.getId();
        Optional<PollutionData> isExist = pollutionRepository.findById(id);
        if (isExist.isPresent()) {
            isExist.get().update(pollutionData);
        } else {
            pollutionRepository.save(pollutionData);
        }
    }
}
