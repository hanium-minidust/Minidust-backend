## Minidust API 서버

미니더스트에서의 센서와 앱간의 통신을 위한 API 서버입니다.
- 미세먼지 센서로부터 데이터를 전송받아 데이터베이스에 저장합니다.
- 앱이 요청할 경우, 데이터베이스에 존재하는 센서 개별의 데이터 혹은 모든 센서의 데이터를 제공합니다.
- 사용자의 위도와 경도를 인자로 받아 해당 위치의 날씨 혹은 주소를 반환해줍니다.

---
#### 사용한 기술
- Spring Boot
- JPA
- MySQL
- Swagger
- Amazon EC2

---

#### 시스템 구성도
정리중

#### ERD 다이어그램
정리중

#### API 문서(Swagger)
- 클라우드 서비스 종료 관계로 PDF로 대체하였습니다.
[Swagger UI.pdf](https://github.com/hanium-minidust/Minidust-backend/files/7574824/Swagger.UI.pdf)