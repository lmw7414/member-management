# member-management
## 1. 포트 정보
1. 애플리케이션 포트
    - Spring Boot 기본 포트: 8080
    - (nginx 리버스 프록시 사용시) 외부 접속 포트: 80
2. DB 정보
    - DB 종류: MariaDB
    - 호스트: localhost 
    - 포트: 3306 
    - DB 이름: member_management

## 2. 실행가이드
### 1. 빌드 환경 및 사전 준비
- JDK: OpenJDK 21 이상
- 빌드 도구: Gradle (프로젝트 내부 `./gradlew` 사용)
- DB: MariaDB 10.x
- 기본 실행 프로필: local
### 2. 소스코드 클론
```bash
git clone https://github.com/lmw7414/member-management.git
cd member-management
./gradlew clean build
```
### 3. DB 설정
```mysql
CREATE DATABASE member_management CHARACTER SET utf8mb4;

CREATE USER 'member'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON member_management.* TO 'member'@'localhost';

FLUSH PRIVILEGES;
```

### 4. 서비스 실행
```bash
java -jar build/libs/app.jar \
    --spring.profiles.active=local \
    --spring.datasource.username=member \
    --spring.datasource.password=1234
```

(2번까지 진행 후) Docker 사용시 (Docker, Docker-compose 설치 필요)
.env 파일 추가(docker-compose 파일과 동일한 디렉토리에)
```
SPRING_DATASOURCE_USERNAME=member
SPRING_DATASOURCE_PASSWORD=1234
SPRING_PROFILES_ACTIVE=prod
```
```bash
docker-compose up -d --build
```

## 3. API 테스트 방법
로컬: http://localhost:8080

서버: http://3.35.97.41

### 1. 회원가입(POST /api/members/join)
**request**
```json
{
    "username": "test",
    "password": "12341234",
    "email": "test@naver.com"
}

```
**response**
```json
{
    "resultCode": "success",
    "result": {
        "username": "test",
        "email": "test@naver.com"
    }
}
```

### 2. 로그인(POST /api/members/login)
**reqeust**
```json
{
    "username": "test",
    "password": "12341234"
}

```
**response**
```json
{
    "resultCode": "success",
    "result": "success"
}
```

### 3. 단건조회(GET /api/members/{username})
**response예시**
```json
{
    "resultCode": "success",
    "result": {
        "username": "test",
        "email": "test@naver.com",
        "createdAt": "2025-11-17T02:49:33.595409"
    }
}
```
### 4. 전체조회(GET /api/members)
**response예시**
```json
{
  "resultCode": "success",
  "result": [
    {
      "username": "abcd1234",
      "email": "abcd@naver.com",
      "createdAt": "2025-11-17T01:52:14.474854"
    },
    {
      "username": "test1",
      "email": "test1@naver.com",
      "createdAt": "2025-11-17T01:59:27.750508"
    },
    {
      "username": "test2",
      "email": "test2@naver.com",
      "createdAt": "2025-11-17T01:59:37.927912"
    },
    {
      "username": "test3",
      "email": "test3@naver.com",
      "createdAt": "2025-11-17T01:59:44.170764"
    }
  ]
}
```