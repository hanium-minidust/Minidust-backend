package com.minidust.api.domain.pollution.repository;

import com.minidust.api.domain.pollution.models.PollutionStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PollutionStationRepository extends JpaRepository<PollutionStation, String> {
    PollutionStation findByStationName(String stationName);

    List<PollutionStation> findAllBysidoName(String sidoName);
}
