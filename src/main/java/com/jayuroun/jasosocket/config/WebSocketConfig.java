package com.jayuroun.jasosocket.config;

import com.jayuroun.jasosocket.properties.CorsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

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
 * packageName    : kr.zelen.socket.config
 * fileName       : WebSocketBrokerConfig
 * author         : darren
 * date           : 4/30/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 4/30/24        darren       최초 생성
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSocket
@ConditionalOnProperty(name = "messaging.socket", havingValue = "true")
public class WebSocketConfig implements WebSocketConfigurer {
    private final CorsProperties corsProperties;
    private final WebSocketHandler webSocketHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws").setAllowedOrigins(corsProperties.getAllowedOrigins().split(","));
        registry.addHandler(webSocketHandler, "/ws").setAllowedOrigins(corsProperties.getAllowedOrigins().split(",")).withSockJS();
    }
}
