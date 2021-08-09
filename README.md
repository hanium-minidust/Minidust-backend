# Minidust API 명세

### 미세먼지 측정기 데이터

- /api 앞에는 서버의 주소인 "http://3.36.87.106/"이 생략되어 있습니다.

1. 모든 측정 데이터 가져오기

   ```http
   GET /api/data
   ```

   - 출력값

     ```json
     [
       {
         "createdAt": "2021-08-09T19:51:56.947",
         "modifiedAt": "2021-08-09T19:51:56.947",
         "id": 2000,
         "latitude": 37.01366648555692,
         "longitude": 127.26484694466728,
         "temperature": 32,
         "humidity": 80,
         "pm25": 15,
         "pm10": 31
       },
       {
         "createdAt": "2021-08-09T19:51:59.72",
         "modifiedAt": "2021-08-09T19:51:59.72",
         "id": 2001,
         "latitude": 37.01366648555692,
         "longitude": 127.26484694466728,
         "temperature": 32,
         "humidity": 80,
         "pm25": 15,
         "pm10": 31
       }
     ]
     ```

     

2. 특정 기기의 측정 데이터 가져오기

   ```http
   GET /api/data/{id}
   ```

   - 출력값

     ```json
     {
       "result": {
         "createdAt": "2021-08-09T19:51:56.947", // 만들어진 시간
         "modifiedAt": "2021-08-09T19:51:56.947", // 최근 수정 시간
         "id": {id}, // 위에서 입력한 기기 ID
         "latitude": 37.01366648555692, // 위도 값
         "longitude": 127.26484694466728, // 경도 값
         "temperature": 32, // 온도
         "humidity": 80, // 습도
         "pm25": 15, // 초미세먼지(PM2.5) 측정 값 
         "pm10": 31 // 미세먼지(PM10) 측정 값
       }
     }
     ```

3. 데이터 업로드하기

   ```http
   POST /api/data
   ```

   - 입력값

     ```json
     {
       "id":"2000",  // 기기 ID
       "latitude" : "127.26484694466728", // 위도값
       "longitude" : "37.01366648555692", // 경도값
       "temperature" : "32", // 온도 값
       "humidity" : "80", // 습도 값
       "pm25" : "15", // 초미세먼지(PM2.5) 측정 값
       "pm10" : "31" // 미세먼지(PM10) 측정 값
     }
     ```

   - 출력값

     ```json
     ID // 위에서 입력한 기기의 ID
     ```

4. 데이터 지우기

   ```HTTP
   DELETE /api/data/{id}
   ```

   - 출력값

     ```json
     ID // 위에서 입력한 기기의 ID
     ```

     

### 미세먼지 공공 API 데이터

1.  특정 도시의 모든 미세먼지 오염도 데이터 가져오기

   ```http
   GET /api/pollution?query={도시이름} // 도시이름 : 서울, 경기
   ```

   - 출력값

     ```json
     [
         {
             id: 1,
             sidoName: "서울",
             stationName: "중구",
             longitude: 126.975961,
             latitude: 37.564639,
             pm25: 10,
             pm10: 21
         },
         {
             id: 2,
             sidoName: "서울",
             stationName: "한강대로",
             longitude: 126.971519,
             latitude: 37.549389,
             pm25: 16,
             pm10: 23
         }
     ]
     ```



### 날씨 데이터 가져오기

1. 위도와 경도로 해당 위치의 날씨정보 가져오기

   ```HTTP
   GET /api/weather?lon={longitude}&lat={latitude}
   ```

   - 출력값

     ```json
     // 
     {
       "id": null,
       "icon": "https://openweathermap.org/img/wn/10n.png", // 해당 날씨와 맞는 아이콘 이미지
       "temperature": 29, // 온도(Celsius 단위)
       "humidity": 75, // 습도
       "name": "Anseong" // 도시 이름
     }
     ```



### 주소<-> 위도/경도 변환하기

1. 주소를 위도와 경도로 변환하기

   ```http
   GET /api/map/addressToCoords?query={주소} // 주소 : 상암동, 당왕동, 석정동 등
   ```

   - 출력값

     ```json
     {
         latitude: 37.0204, // 위도 값
         longitude: 127.2718 // 경도 값
     }
     ```

     

2. 위도와 경도를 주소로 변환하기

   ```http
   GET /api/map/coordsToAddress?lon={longitude}&lat={latitude}
   ```

   - 입력값

     ```json
     http://localhost:8080/api/map/coordsToAddress?lon=127.2718&lat=37.0204
     ```

   - 출력값

     ```json
     {
         First: "경기도",
         FirstAlias : "경기",
         Second: "안성시",
         Third: "당왕동"
     }
     ```
```
     
     
```