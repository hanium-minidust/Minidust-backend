package com.minidust.api;

import com.minidust.api.util.PollutionAPI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    // CommandLineRunner 를 만들자.
    @Bean
    public CommandLineRunner runner(PollutionAPI pollutionAPI) {
        return args -> {
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
            System.out.println("현재시간 : " + new Date());

            List<String> updateList = Arrays.asList("서울", "경기");
            for (String x : updateList) {
                pollutionAPI.updateStation(x);
                pollutionAPI.updatePollutionData(x);
            }
        };
    }
}
