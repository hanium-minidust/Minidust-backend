package com.minidust.api.domain.pollution.repository;

import com.minidust.api.domain.pollution.models.PollutionStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollutionStationRepository extends JpaRepository<PollutionStation, Long> {
    PollutionStation findByStationName(String stationName);
}
