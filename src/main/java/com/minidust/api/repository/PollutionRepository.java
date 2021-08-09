package com.minidust.api.repository;

import com.minidust.api.models.PollutionData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PollutionRepository extends JpaRepository<PollutionData, Long> {
    List<PollutionData> findAllBySidoName(String sidoName);
}
