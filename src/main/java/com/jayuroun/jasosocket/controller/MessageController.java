package com.jayuroun.jasosocket.controller;

import com.jayuroun.jasosocket.handler.WebSocketHandler;
import com.jayuroun.jasosocket.model.Message;
import com.jayuroun.jasosocket.model.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * packageName    : com.jayuroun.jasosocket.controller
 * fileName       : MessageController
 * author         : darren
 * date           : 5/9/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 5/9/24        darren       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/message")
public class MessageController {
    private final WebSocketHandler webSocketHandler;

    @PostMapping("/send")
    public Object sendMessage(@RequestBody Message message) {

        boolean isSuccess = webSocketHandler.sendMessageTopic(message);

        return isSuccess ? "success" : "fail";
    }

}
