package com.minidust.api.global.util;

import com.minidust.api.domain.pollution.service.PollutionUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class UpdaterOnload implements ApplicationRunner {

    @Autowired
    private PollutionUpdateService pollutionUpdateService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> cityName = Arrays.asList("서울", "경기");
        for (String city : cityName) {
            pollutionUpdateService.updateStation(city);
            pollutionUpdateService.updateDust(city);
        }
    }
}
