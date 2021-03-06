package com.minidust.api.global.util;

import com.minidust.api.domain.pollution.service.PollutionService;
import com.minidust.api.domain.pollution.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final PollutionService pollutionService;
    private final StationService stationService;

    List<String> sidoName = Arrays.asList("서울", "경기");

    // 매 시간 5분에 미세먼지 오염도 정보를 업데이트 합니다.
    @Scheduled(cron = "0 5 * * * *")
    public void pollutionDataUpdater() {
        for (String query : sidoName) {
            pollutionService.updateDust(query);
        }
        System.out.println(new Date() + " 미세먼지 데이터가 업데이트 되었습니다.");
    }

    // 미세먼지 측정소 정보를 업데이트
    // 초, 분, 시, 일, 월, 주 순서, 매달 1일 새벽 1시에 업데이트가 진행됩니다.
    @Scheduled(cron = "0 0 1 1 * *")
    public void pollutionStationUpdater() {
        for (String query : sidoName) {
            stationService.updateStation(query);
        }
        System.out.println(new Date() + " 미세먼지 측정소 데이터가 업데이트 되었습니다.");
    }
}
