//package com.jayuroun.stompjaso.service.impl;
//
//import com.jayuroun.stompjaso.config.MessagingConfig;
//import com.jayuroun.stompjaso.service.MessagingService;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * -------------------------------------------------------------------------------------
// * ::::::'OO::::'OOO::::'OO:::'OO:'OO::::'OO:'OOOOOOOO:::'OOOOOOO::'OO::::'OO:'OO....OO:
// * :::::: OO:::'OO OO:::. OO:'OO:: OO::::.OO: OO.....OO:'OO.....OO: OO:::: OO: OOO...OO:
// * :::::: OO::'OO:..OO:::. OOOO::: OO::::.OO: OO::::.OO: OO::::.OO: OO:::: OO: OOOO..OO:
// * :::::: OO:'OO:::..OO:::. OO:::: OO::::.OO: OOOOOOOO:: OO::::.OO: OO:::: OO: OO.OO.OO:
// * OO:::: OO: OOOOOOOOO:::: OO:::: OO::::.OO: OO.. OO::: OO::::.OO: OO:::: OO: OO..OOOO:
// * :OO::::OO: OO.....OO:::: OO:::: OO::::.OO: OO::. OO:: OO::::.OO: OO:::: OO: OO:..OOO:
// * ::OOOOOO:: OO:::..OO:::: OO::::. OOOOOOO:: OO:::. OO:. OOOOOOO::. OOOOOOO:: OO::..OO:
// * :......:::..:::::..:::::..::::::.......:::..:::::..:::.......::::.......:::..::::..::
// * <p>
// * packageName    : com.jayuroun.stompjaso.service
// * fileName       : MessagingService
// * author         : darren
// * date           : 5/2/24
// * description    :
// * ===========================================================
// * DATE              AUTHOR             NOTE
// * -----------------------------------------------------------
// * 5/2/24        darren       최초 생성
// */
//@Service
//public class DefaultMessagingService implements MessagingService {
//    private Set<String> topics;
//    private final SimpMessageSendingOperations messagingTemplate;
//
//    public DefaultMessagingService(SimpMessageSendingOperations messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }
//
//    @PostConstruct
//    private void init(MessagingConfig messagingConfig) {
//        topics = ConcurrentHashMap.newKeySet();
//        String[] topicsArray = messagingConfig.getTopic().toArray(new String[0]);
//        for(String topic : topicsArray) {
//            messagingTemplate.convertAndSend("/topic/" + topic, "Created Topic : " + topic);
//        }
//    }
//
//    @Override
//    public void send(String destination, String message) {
//
//    }
//
//
//    @Override
//    public void createTopic(String topicName) {
//
//    }
//}
