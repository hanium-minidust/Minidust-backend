package com.minidust.api.domain.pollution.repository;

import com.minidust.api.domain.pollution.models.Pollution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PollutionRepository extends JpaRepository<Pollution, String> {
    List<Pollution> findAllBySidoName(String sidoName);
}
