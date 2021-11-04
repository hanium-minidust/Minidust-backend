package com.minidust.api.domain.pollution.models;

import com.minidust.api.global.util.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pollution_station")
public class PollutionStation extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(nullable = false)
    double latitude;

    @Column(nullable = false)
    double longitude;

    @Column(nullable = false)
    String stationName;
}
