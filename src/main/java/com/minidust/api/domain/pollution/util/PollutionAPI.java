package com.minidust.api.domain.pollution.util;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@Slf4j
@Component
public class PollutionAPI {
    private static final String API_KEY = "rD91vycFGdhMeipqQIuYBD4bhZKf/vYOFsxWwoWwWlV9HLbonxD22rOLOiuEokmR9Ge2b7qCrqNUpHzSz7W7hQ==";
    private static final String baseUrl = "http://apis.data.go.kr/B552584/";

    public JSONArray fetchByType(FetchType fetchType, String query) {
        String url = "";
        String queryParamName = "";
        if (fetchType == FetchType.PM) {
            url = baseUrl + "ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
            queryParamName = "sidoName";
        } else {
            url = baseUrl + "MsrstnInfoInqireSvc/getMsrstnList";
            queryParamName = "addr";
        }

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .uri(URI.create(url))
                .queryParam("serviceKey", API_KEY)
                .queryParam("returnType", "json")
                .queryParam("numOfRows", "500")
                .queryParam("pageNo", "1")
                .queryParam(queryParamName, query)
                .queryParam("ver", "1.0")
                .encode()
                .build();

        try {
            ResponseEntity<String> forEntity = new RestTemplate().getForEntity(uriComponents.toUri(), String.class);
            return parseToJsonArray(forEntity);
        } catch (RestClientException e) {
            log.warn("[RESTClientException 발생] " + LocalDateTime.now() + " 시도한 파라미터 : " + fetchType + " + " + query);
            return new JSONArray(); // 아무것도 못받아왔을때는 빈 배열을 리턴한다.
        }
    }

    // 정상적이지 않다면 빈 배열을 반환해서 결과값을 받아 로직을 실행하는 곳에서 반복문이 돌지 않도록 합니다.
    public JSONArray parseToJsonArray(ResponseEntity<String> responseEntity) {
        try {
            return new JSONObject(responseEntity.getBody()).getJSONObject("response").getJSONObject("body").getJSONArray("items");
        } catch (JSONException e) {
            return new JSONArray();
        }
    }
}
