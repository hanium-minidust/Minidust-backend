package com.minidust.api.global.util;

import com.minidust.api.domain.pollution.service.PollutionService;
import com.minidust.api.domain.pollution.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class UpdaterOnload implements ApplicationRunner {

    private final PollutionService pollutionService;
    private final StationService stationService;

    @Autowired
    public UpdaterOnload(PollutionService pollutionService, StationService stationService) {
        this.pollutionService = pollutionService;
        this.stationService = stationService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> cityName = Arrays.asList("서울", "경기");
        for (String city : cityName) {
            stationService.updateStation(city);
            pollutionService.updateDust(city);
        }
    }
}
