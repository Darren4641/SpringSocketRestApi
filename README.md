### 소켓 Test 환경

http://localhost:9462/websocket-test
```bash
docker build -t socket-test .
docker run -d -p 9462:9462 socket-test
```
```
curl --location 'http://localhost:9462/api/message/send' \
--header 'Content-Type: application/json' \
--data '{
    "receiver": null,
    "topic": "topic1",
    "messageType": "MESSAGE",
    "data": "{\"test\": \"test\"}"
}'
//JSON.stringify 형태로 전달 gson 등의 라이브러리로 String형태의 json으로 프론트와 실시간 통신
```

### YML 필수 설정
```bash
//cors 설정
cors:
  allowed-origins: 'http://localhost:9462'
  allowed-methods: GET,POST,PUT,DELETE,OPTIONS
  allowed-headers: '*'
  max-age: 3600

// 테스트 환경
websocket:
  host: "ws://localhost:9462/ws"

messaging:
  socket: true
  encryptionEnabled: true
  ackTime: 60000 # //1분 ACK Time 주기
  ackLimitCount: 3 // ACK 미응답 3번 초과시 인메모리내에서 세션 제거
  topic:
    - "topic1" //서버 시작시 등록할 Topic 
    - "topic2"

//DEBUG 시 log 출력
logging:
  level:
    org.hibernate.SQL: DEBUG
    root: DEBUG
    com.zaxxer.hikari.pool.HikariPool: DEBUG
    org:
      hibernate:
        SQL: DEBUG
        type:
          descriptor:
            sql: trace
      springframework:
        web: DEBUG
        data: DEBUG
      boot:
        autoconfigure:
          data:
            rest: DEBUG
            jpa: DEBUG
            orm: DEBUG
```
```
## Step 1 >> DI 
private final WebSocketHandler webSocketHandler;
## Step 2 >> 실시간 으로 보낼 데이터 Message.data() 안에 넣기
webSocketHandler.sendMessageTopic(Message.builder()
                .messageType(MessageType.MESSAGE)
                .receiver(null)
                .data("${{원하는 데이터}}")
                .topic("${{topicName}}")
                .build());
## Step 3 >> return 
T / F 로 핸들링

```


