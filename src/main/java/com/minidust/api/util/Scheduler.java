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

    private final PollutionDataApi pollutionDataAPI;
    private final PollutionStationApi pollutionStationApi;

    List<String> sidoName = Arrays.asList("서울", "경기");

    // 매 시간 5분에 미세먼지 오염도 정보를 업데이트 합니다.
    @Scheduled(cron = "0 5 * * * *")
    public void pollutionDataUpdater() {
        for (String x : sidoName) {
            pollutionDataAPI.updatePollutionData(x);
        }
        System.out.println(new Date() + " 미세먼지 데이터가 업데이트 되었습니다.");
    }

    // 초, 분, 시, 일, 월, 주 순서
    // 미세먼지 측정소 정보를 업데이트
    // 매달 1일 새벽 1시에 업데이트가 진행됩니다.
    @Scheduled(cron = "0 0 1 1 * *")
    public void pollutionStationUpdater() {
        for (String x : sidoName) {
            pollutionStationApi.updateStation(x);
        }
        System.out.println(new Date() + " 미세먼지 측정소 데이터가 업데이트 되었습니다.");
    }
}
