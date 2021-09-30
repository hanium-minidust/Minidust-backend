package com.minidust.api.models;

import lombok.Builder;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "station")
public class PollutionStation extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    double latitude;

    @Column(nullable = false)
    double longitude;

    @Column(nullable = false)
    String stationName;
}
