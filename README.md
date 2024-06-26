# [MBTI] 실시간 업무 협업 플랫폼

```
✅ 실시간 업무 협업 플랫폼입니다. 핵심 서비스는 일정 관리, 출퇴근 관리, 채팅 등이 있습니다. 
```

---

<img width="1000" alt="logo" src="https://github.com/zilyun/Final_Project/assets/40315922/e5f6b111-d665-4fa6-8680-5ff5408f0a13">

---

- [💡 서비스 배경 및 목표](#💡-서비스-배경-및-목표)
- [🛠️ 기술 스택](#🛠️-기술-스택)
- [🗺️ 서버 구조](#🗺️-서버-구조)
- [🔥 기술적 개선 및 고려](#🔥-기술적-개선-및-고려)

---

## 💡 서비스 배경 및 목표

IT 인프라가 많이 보급되고 있으며, 이러한 흐름 속에서 업무의 생산성을 증대시키는 것이 더욱 중요해지고 있습니다.</br>
신뢰할 수 있는 실시간 업무 협업 플랫폼에 대한 필요성과 수요를 확인했습니다.

MBTI 서비스의 목표는 다음과 같습니다.<br>

- 목표 1. 실시간 업무 공유 및 협업을 원하는 기업과 팀을 위해 설계된 웹사이트를 구현합니다.</br>
- 목표 2. 실시간 알림, 채팅과 같은 기능을 통해 서비스 이용에 대한 사용자 편의성을 확보합니다.</br>
- 목표 3. 원활한 서비스 운영을 위한 기술적 요소를 적용하고 개선합니다. (ex. Redis 캐싱)

## 🛠️ 기술 스택

| 분류       | 기술명                                                                   |
|----------|----------------------------------------------------------------------------|
| BackEnd  | Java, Spring (Boot, Security), Junit, Redis, MySql, rabbitMQ               |
| FrontEnd | HTML, Javascript, Thymeleaf                                                |
| DevOps   | nGrinder, Scouter, EC2, RDS, S3, Docker, Jenkins                           |   
| Tools    | IntelliJ, Gradle                                                           |

## 🗺️ 서버 구조

![CICD](https://github.com/zilyun/Final_Project/assets/40315922/cd2532f4-a1df-440b-860a-792e963a53c4)

## 💾 DB 구조

![DB구조](https://github.com/zilyun/Final_Project/assets/40315922/61cb8933-8e20-41c8-b8bd-9c60da76f08f)

## 🔥 기술적 개선 및 고려

```
다음과 같은 구조로 작성돼 있으니 참고부탁드립니다. 🙇

- 상황 및 목표 [link]
    - 목표 달성을 위한 행동
        - 결과 및 추가사항
```

### 채팅방 조회 성능 개선 [[적용 코드]() / [설정 코드]() / [결과](https://velog.io/@ziru/28.Redis-%EC%84%B1%EB%8A%A5-%ED%85%8C%EC%8A%A4%ED%8A%B8)]

- `Redis`를 도입해 채팅방 입장 시 캐싱 처리

  <details>
  <summary>부하테스트 결과, 캐싱 미적용 대비 약 3.5배의 TPS 성능 향상</summary>
  <div>
      <h4>[Ngrinder]</h4>
      <span>Cache 미적용</span>
      <img src="https://github.com/zilyun/Final_Project/assets/40315922/b2da3659-9498-4caa-b082-dbf88e68dbbe">
      <span>Cache 적용</span>
      <img src="https://github.com/zilyun/Final_Project/assets/40315922/bc27e31a-37c9-452d-903a-9cee8b56e53e">
  </div>
  <div>
      <h4>[Scouter]</h4>
      <span>Cache 미적용</span>
      <img src="https://github.com/zilyun/Final_Project/assets/40315922/ebd08602-df42-4936-89be-8767645791e1">
      <span>Cache 적용</span>
      <img src="https://github.com/zilyun/Final_Project/assets/40315922/d1d6c828-c932-4c78-910c-56b78430ab72">
  </div>
  </details>

### 메시지 대규모 트래픽 발송 응답속도 개선 [[적용 코드]() / [설정 코드]()]

- `RabbitMQ`를 도입하여 대규모 메시지를 효율적으로 처리
  <details>
  <summary>RabbitMQ의 메시지 브로커로 메시지를 비동기 처리하여 대규모 트래픽을 처리</summary>
      <h4>[RabbitMQ]</h4>
      <img src="https://github.com/zilyun/Final_Project/assets/40315922/13a2cfdf-acae-4fc3-b1b8-5c2e5eaac56d">
  </details>

### 채팅방 실시간 알림 기능 구현 [[적용 코드]() / [구성 패키지]()]

- 네트워크 자원을 고려해 `Server Sent Event` 스펙으로 클라이언트에게 채팅방 실시간 알림 전송
    - 채팅 메시지 생성, 채팅방(멀티,1대1) 만들기, 채팅방 나가기 기능 사용 시 업데이트 실시간 알림

### 채팅 기록 검색 정확도, 정밀도 개선 [[적용 코드]()]

- `Full Text Index(ngram parser)`를 적용해 키워드가 일치하는 정도를 수치화
    - like + wildcard 검색 대비 정확하고 정밀한 검색 결과 반환

### S3 저장소 파일 업로드 / 다운로드 [[적용 코드]() / [구성 패키지]()]

- 채팅 기능에서 파일 업로드 시 `S3` 저장소 사용

### 문의 리스트 조회 성능 개선 [[적용 코드]() / [결과](https://velog.io/@ziru/26.%EB%B0%B1%EC%97%94%EB%93%9C-%EA%B0%9C%EB%B0%9C%EC%9E%90-%EC%84%B1%EB%8A%A5-%EA%B0%9C%EC%84%A0-%EC%B4%88%EC%84%9D-%EB%8B%A4%EC%A7%80%EA%B8%B0)]

- `Caffeine Cache`를 도입해 문의 리스트를 조회 시 캐싱 처리

  <details>
  <summary>부하테스트 결과, 캐싱 미적용 대비 약 2배의 TPS 성능 향상</summary>
  <div>
      <h4>[Ngrinder]</h4>
      <span>Cache 미적용</span><br>
      <img alt="미적용" src="https://github.com/zilyun/Final_Project/assets/40315922/4c713b8a-6f0e-41a8-9eff-048813461577"><br>
      <span>Cache 적용</span><br>
      <img alt="적용" src="https://github.com/zilyun/Final_Project/assets/40315922/3893c591-8fab-48e5-baed-519efe1ffd3e">
  </div>
  </details>

### CI/CD 환경 구축 [[설정 코드]()]

- `Jenkins`, `Docker`, `Amazon EC2, S3, RDS`를 이용해 테스트-빌드-배포 자동화
    - 빌드 서버는 Amanzon EC2 서버 사용




