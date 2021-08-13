# Minidust API 명세

### 미세먼지 측정기 데이터

- /api 앞에는 서버의 주소인 "http://3.36.87.106/"이 생략되어 있습니다.
- 모든 결과값의 반환은 JSON 타입으로 이루어집니다.

|   변수명    |                        설명                         |   범위    |
| :---------: | :-------------------------------------------------: | :-------: |
|     id      |        미세먼지 측정기의 id를 입력하는 부분         | NOT NULL  |
|  latitude   |           GPS 좌표의 위도를 입력하는 부분           |   33~43   |
|  longitude  |           GPS 좌표의 경도를 입력하는 부분           | 123 ~ 133 |
| temperature | 미세먼지 측정기의 온도 센서 결과값을 입력하는 부분  | -50 ~ 50  |
|  humidity   |  미세먼지 측정기의 습도센서 결과값을 입력하는 부분  |  1 ~ 100  |
|    pm25     | 미세먼지 측정기의 초미세먼지 결과값을 입력하는 부분 | 1 ~ 1000  |
|    pm10     |  미세먼지 측정기의 미세먼지 결과값을 입력하는 부분  | 1 ~ 1000  |

1. 모든 측정 결과 가져오기
    ```http
    GET /api/data
    ```

    - 출력값
        ```json
        {
          "status": "OK",
          "message": "OK",
          "data": [
            {
              "createdAt": "2021-08-13T10:31:31.211",
              "modifiedAt": "2021-08-13T10:31:31.211",
              "id": 2000,
              "latitude": 36.123421,
              "longitude": 125.2312,
              "temperature": 32,
              "humidity": 34,
              "pm25": 15,
              "pm10": 31
            },
            {
              "createdAt": "2021-08-13T10:53:03.888",
              "modifiedAt": "2021-08-13T10:53:03.888",
          "id": 2001,
              "latitude": 34.123421,
              "longitude": 124.2323,
              "temperature": 32,
              "humidity": 80,
              "pm25": 15,
              "pm10": 31
            }
          ]
        }
        ```

    - 예외 처리

        1. 범위 밖에 있는 값이 나올경우

            - 어떤 부분의 어떤 범위 오류가 있는지 표시를 해줍니다.

           ```json
           {
             "status": "BAD_REQUEST",
             "message": "temperature 온도는 50도 보다 높아질 수 없습니다.",
             "data": null
           }
           ```

2. 특정 기기의 데이터를 고유번호를 이용해 가져오기
   ```http
   GET /api/data/{id}
   ```

    - 출력값

      ```json
      {
        "status": "OK",
        "message": "OK",
        "data": {
          "createdAt": "2021-08-13T10:31:31.211",
          "modifiedAt": "2021-08-13T10:31:31.211",
          "id": 2000,
          "latitude": 36.123421,
          "longitude": 125.2312,
          "temperature": 32,
          "humidity": 34,
          "pm25": 15,
          "pm10": 31
        }
      }
      ```


- 예외 처리
    1. 해당 아이디를 가진 데이터가 없을 경우
       ```json {  
       "status":  "NOT_FOUND",  
       "message":  null,  
       "data":  null  
       }
       ```

       ​

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



### 미세먼지 공공 API 데이터

1. 특정 도시의 모든 미세먼지 오염도 데이터 가져오기

      ```http
      GET /api/pollution?query={도시이름} // 도시이름 : 서울, 경기
      ```

    - 출력값

         ```json
            {
               "status":"OK",
               "message":"OK",
               "data":[
                  {
                     "id":1,
                     "sidoName":"서울",
                     "stationName":"도봉구",
                     "longitude":127.029333,
                     "latitude":37.654278,
                     "pm25":4,
                     "pm10":16
                  },
                  {
                     "id":2,
                     "sidoName":"서울",
                     "stationName":"은평구",
                     "longitude":126.9335038,
                     "latitude":37.6104714,
                     "pm25":9,
                     "pm10":12
                  }
               ]
            }
         ```



### 날씨 데이터 가져오기

1. 위도와 경도로 해당 위치의 날씨정보 가져오기

   ```HTTP
   GET /api/weather?lon={longitude}&lat={latitude}
   ```

    - 출력값
      ```json
      {
            "status":"OK",
            "message":"OK",
            "data":{
               "id":null,
               "icon":"https://openweathermap.org/img/wn/10d.png",
               "temperature":27,
               "humidity":65,
               "name":"Osan"
            }
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
	   "status":"OK",
	   "message":"OK",
	   "data":{
	      "latitude":37.0204,
	      "longitude":127.2718
	   }
	 }
     ```

2. 위도와 경도를 주소로 변환하기

   ```http
   GET /api/map/coordsToAddress?lon={longitude}&lat={latitude}
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