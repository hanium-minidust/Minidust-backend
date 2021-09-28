package com.minidust.api.util;

import com.minidust.api.models.PollutionData;
import com.minidust.api.service.PollutionApiService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/*
TODO
클래스도 관심사에 따라 분리? PollutionDataAPI / PollutionStationAPI ?
PollutionStationAPI 도 메소드 분리를 하자.
 */
@Component
public class PollutionApi {
    private static final String API_KEY = "rD91vycFGdhMeipqQIuYBD4bhZKf/vYOFsxWwoWwWlV9HLbonxD22rOLOiuEokmR9Ge2b7qCrqNUpHzSz7W7hQ==";
    private final PollutionApiService pollutionApiService;

    public PollutionApi(PollutionApiService pollutionApiService) {
        this.pollutionApiService = pollutionApiService;
    }

    //List<String> sidoName = Arrays.asList("서울", "부산", "대구", "인천", "광주", "대전", "울산", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주", "세종");
    List<String> sidoName = Arrays.asList("서울", "경기"); // 서울, 경기에 대해서 시험
    HashMap<String, List<Double>> stationList = new HashMap<>();

    /**
     * 미세먼지 API에서 미세먼지 정보 가져오기
     */
    @Transactional
    public void updatePollutionData(String query) {
        ResponseEntity<String> responseEntity;
        try {
            // 서버가 500 오류를 가질 경우 RestClientException 발생 가능
            responseEntity = fetchPollutionDataFromApi(query);
            // JSON이 아닌 형태로 리턴될 경우 JSONException 발생 가능
            JSONArray jsonArray = entityToJsonArray(responseEntity);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    // pm10Value or pm25Value 에 숫자값이 아닌 값이 있을경우 JSONException 발생 가능
                    JsonObjectToDatabase(jsonArray.getJSONObject(i));
                } catch (JSONException e) {
                    // JSONException 이 발생할 경우, Integer 로 형변환이 불가능할 때(자료이상, - 등)
                    //continue;
                }
            }
        } catch (RestClientException e) {
            // 서버 오류로 접속이 불가능했을때 발생하는 오류입니다.
            System.out.println("RESTClientException 발생");
        } catch (JSONException e) {
            // 서버는 접속이 가능하나, JSON 타입이 아닌 xml 로 반환되는 경우에 발생합니다.(통신장애나 API 점검시간의 경우)
            System.out.println("JSONException 발생");
        } catch (Exception e) {
            // 추가로 발생할 수 있는 예외들을 대비하기 위해서
            System.out.println("[WARN]" + new Date() + "Exception 발생");
            e.printStackTrace();
        }
    }

    // API에서 정보 가져오기
    public ResponseEntity<String> fetchPollutionDataFromApi(String query) throws RestClientException {
        RestTemplate rest = new RestTemplate();

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("apis.data.go.kr")
                .path("/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty")
                .queryParam("serviceKey", API_KEY)
                .queryParam("returnType", "json")
                .queryParam("numOfRows", "500")
                .queryParam("pageNo", "1")
                .queryParam("sidoName", query)
                .queryParam("ver", "1.0")
                .encode()
                .build();

        return rest.getForEntity(uriComponents.toUri(), String.class);
    }

    // 가져온 정보를 JSONArray 형태로 변환하기
    public JSONArray entityToJsonArray(ResponseEntity<String> responseEntity) throws JSONException {
        String response = responseEntity.getBody();
//        HttpStatus httpStatus = responseEntity.getStatusCode();
        JSONObject json = new JSONObject(response);
        return json.getJSONObject("response").getJSONObject("body").getJSONArray("items");
    }

    // JSONArray 내부의 JSONObject 들을 Database 로 업로드하기
    public void JsonObjectToDatabase(JSONObject jsonObject) throws JSONException {
        String stationName = jsonObject.getString("stationName");
        List<Double> coords = stationList.get(stationName);

        long id = Math.abs(stationName.hashCode());
        pollutionApiService.uploadData(
                new PollutionData(
                        id,
                        jsonObject.getString("sidoName"),
                        jsonObject.getString("stationName"),
                        coords.get(0),
                        coords.get(1),
                        jsonObject.getInt("pm25Value"),
                        jsonObject.getInt("pm10Value")));
    }

    /**
     * 미세먼지 측정소 API에서 측정소, 위도, 경도 정보 가져오기
     */
    public void updateStation(String query) {
        RestTemplate rest = new RestTemplate();
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("apis.data.go.kr")
                .path("/B552584/MsrstnInfoInqireSvc/getMsrstnList")
                .queryParam("serviceKey", API_KEY)
                .queryParam("returnType", "json")
                .queryParam("numOfRows", "500")
                .queryParam("pageNo", "1")
                .queryParam("addr", query)
                .queryParam("ver", "1.0")
                .encode()
                .build();

        ResponseEntity<String> responseEntity;
        try {
            responseEntity = rest.getForEntity(uriComponents.toUri(), String.class);
        } catch (RestClientException e) {
            System.out.println("[INFO] " + new Date() + " 미세먼지 측정소 정보가 오류로 인해 업데이트 되지 않았습니다.");
            return;
        }

        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();

        JSONObject json = new JSONObject(response);
        JSONArray jsonArray = json.getJSONObject("response").getJSONObject("body").getJSONArray("items");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            // TODO Map 자료형에 가지고 있을... 건지, 아니면 DB에 업로드를 할지. 객체 하나 만들어서, String 저장소 이름, double x, y
            stationList.put(jsonObject.getString("stationName"), Arrays.asList(jsonObject.getDouble("dmY"), jsonObject.getDouble("dmX")));
        }
    }
}
