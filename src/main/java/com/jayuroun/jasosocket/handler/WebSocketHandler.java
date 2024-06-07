package com.jayuroun.jasosocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayuroun.jasosocket.config.MessagingConfig;
import com.jayuroun.jasosocket.model.Message;
import com.jayuroun.jasosocket.model.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * -------------------------------------------------------------------------------------
 * ::::::'OO::::'OOO::::'OO:::'OO:'OO::::'OO:'OOOOOOOO:::'OOOOOOO::'OO::::'OO:'OO....OO:
 * :::::: OO:::'OO OO:::. OO:'OO:: OO::::.OO: OO.....OO:'OO.....OO: OO:::: OO: OOO...OO:
 * :::::: OO::'OO:..OO:::. OOOO::: OO::::.OO: OO::::.OO: OO::::.OO: OO:::: OO: OOOO..OO:
 * :::::: OO:'OO:::..OO:::. OO:::: OO::::.OO: OOOOOOOO:: OO::::.OO: OO:::: OO: OO.OO.OO:
 * OO:::: OO: OOOOOOOOO:::: OO:::: OO::::.OO: OO.. OO::: OO::::.OO: OO:::: OO: OO..OOOO:
 * :OO::::OO: OO.....OO:::: OO:::: OO::::.OO: OO::. OO:: OO::::.OO: OO:::: OO: OO:..OOO:
 * ::OOOOOO:: OO:::..OO:::: OO::::. OOOOOOO:: OO:::. OO:. OOOOOOO::. OOOOOOO:: OO::..OO:
 * :......:::..:::::..:::::..::::::.......:::..:::::..:::.......::::.......:::..::::..::
 * <p>
 * packageName    : com.jayuroun.stompjaso.handler
 * fileName       : WebSocketHandler
 * author         : darren
 * date           : 5/2/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 5/2/24        darren       최초 생성
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;
    private final MessagingConfig messagingConfig;
    // 현재 연결된 세션들
    private Set<WebSocketSession> sessions = new HashSet<>();
    private Map<String ,Set<WebSocketSession>> topics = new HashMap<>();
    private ConcurrentHashMap<String, ScheduledExecutorService> executor = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Integer> missedACK = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        //서버가 켜지면 application.yml에 있는 topic 들을 Map에 저장
        String[] topicsArray = messagingConfig.getTopic().toArray(new String[0]);
        for(String topic : topicsArray) {
            log.debug("init topics : {}", topic);
            topics.put(topic, new HashSet<>());
        }
    }



    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        /* Client가 접속 시 호출되는 메서드 */
        log.debug("{} session connected", session.getId());
        sessions.add(session);

        doACK(session);

    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //응답 시 missedACK 초기화
        missedACKClear(session.getId());

        /* 메시지 수신 시 자동 실행 메서드 */
        String payload = message.getPayload();
        log.debug("payload : {}", payload);

        Message messageDto = mapper.readValue(payload, Message.class);
        Set<WebSocketSession> socketSession = topics.get(messageDto.getTopic());

        if(messageDto.getMessageType().equals(MessageType.ENTER)) {
            /* 처음 입장 시 세션에 추가 */
            socketSession.add(session);
        }else if(messageDto.getMessageType().equals(MessageType.MESSAGE)) {
            /* 일반 메시지 일 경우 */
            sendMessageToTopic(messageDto, socketSession);
        }else if(messageDto.getMessageType().equals(MessageType.SECURITY)) {
            /* 암호화 된 메시지 일 경우 */
            //TODO 준비 중
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        /* Client가 접속 해제 시 호출되는 메서드 */
        // 해당 Client의 Session 끊기
        sessions.remove(session);
        for (Set<WebSocketSession> topic : topics.values()) {
            topic.remove(session);
        }

        // 세션 종료 시 타이머 및 상태 변수 중단 및 제거
        ScheduledExecutorService executorService = executor.get(session.getId());
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
        executor.remove(session.getId());
        missedACK.remove(session.getId());
    }


    public void addTopic(String topic) {
        /* 수동으로 Topic 추가 메서드 */
        topics.put(topic, new HashSet<>());
    }

    public boolean sendMessageTopic(Message message) {
        try {
            //message.getTopic()에 해당하는 세션을 가지고온다. 없으면 새로 만듬 (없을 일 없음)
            Set<WebSocketSession> topicSessions = topics.getOrDefault(message.getTopic(), new HashSet<>());
            sendMessageToTopic(message, topicSessions);
            log.debug("message : {}", message.getData());
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void sendMessageToTopic(Message message, Set<WebSocketSession> socketSession) {
        socketSession.parallelStream().forEach(session -> sendMessage(session, message.getData()));
    }


    private void doACK(WebSocketSession session) {
        // ACK 초기 연결
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executor.put(session.getId(), executorService);
        missedACK.put(session.getId(), 0);

        executorService.scheduleAtFixedRate(() -> {
            try {
                if (session.isOpen()) {
                    Integer missedCount = missedACK.getOrDefault(session.getId(), 0);
                    if (missedCount >= messagingConfig.getAckLimitCount()) {  // 미응답 횟수가 3회 이상이면 세션 종료
                        session.close();
                        executorService.shutdown();
                        executor.remove(session.getId());
                        missedACK.remove(session.getId());
                        System.out.println("Session closed due to lack of client response: " + session.getId());
                    } else {
                        session.sendMessage(new TextMessage(mapper.writeValueAsString(Message.builder()
                                .messageType(MessageType.ACK)
                                .build())));
                        // 미응답 횟수 증가
                        missedACK.put(session.getId(), missedCount + 1);
                    }
                } else {
                    // 세션이 닫혔다면 타이머 종료
                    executorService.shutdown();
                    executor.remove(session.getId());
                    missedACK.remove(session.getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, messagingConfig.getAckTime(), TimeUnit.MILLISECONDS);
    }

    private void missedACKClear(String sessionId) {
        missedACK.put(sessionId, 0);
    }
}
