package com.minidust.api.domain.sensor.repository;

import com.minidust.api.domain.sensor.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
}
