package com.minidust.api;

import com.minidust.api.util.PollutionAPI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Arrays;
import java.util.List;

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
            List<String> updateList = Arrays.asList("서울", "경기");
            for (String x : updateList) {
                pollutionAPI.updateStation(x);
            }
        };
    }
}
