package com.minidust.api.domain.pollution.util;

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

@Component
public class PollutionAPI {
    private static final String API_KEY = "rD91vycFGdhMeipqQIuYBD4bhZKf/vYOFsxWwoWwWlV9HLbonxD22rOLOiuEokmR9Ge2b7qCrqNUpHzSz7W7hQ==";
    private static final String baseUrl = "http://apis.data.go.kr/B552584/";

    public ResponseEntity<String> fetchByType(FetchType fetchType, String query) throws RestClientException {
        RestTemplate rest = new RestTemplate();
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

        return rest.getForEntity(uriComponents.toUri(), String.class);
    }

    public JSONArray parseToJsonArray(ResponseEntity<String> responseEntity) throws JSONException {
        return new JSONObject(responseEntity.getBody()).getJSONObject("response").getJSONObject("body").getJSONArray("items");
    }
}
