package com.minidust.api.service;

import com.minidust.api.models.PollutionData;
import com.minidust.api.repository.PollutionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PollutionApiService {

    private final PollutionRepository pollutionRepository;

    public PollutionApiService(PollutionRepository pollutionRepository) {
        this.pollutionRepository = pollutionRepository;
    }

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
