package com.minidust.api.util;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final PollutionAPI pollutionAPI;

    List<String> sidoName = Arrays.asList("서울", "경기");

    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 5 * * * *")
    public void pollutionDataUpdater() {
        for (String x : sidoName) {
            pollutionAPI.updatePollutionData(x);
        }
        System.out.println(new Date() + " 미세먼지 데이터가 업데이트 되었습니다.");
    }

    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 0 1 1 * *")
    public void pollutionStationUpdater() {
        for (String x : sidoName) {
            pollutionAPI.updateStation(x);
        }
        System.out.println(new Date() + " 미세먼지 측정소 데이터가 업데이트 되었습니다.");
    }
}
