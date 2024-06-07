package com.jayuroun.jasosocket.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

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
 * packageName    : com.jayuroun.stompjaso.config
 * fileName       : MessagingConfig
 * author         : darren
 * date           : 5/2/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 5/2/24        darren       최초 생성
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "messaging")
public class MessagingConfig {
    private boolean encryptionEnabled;
    private long ackTime;
    private Integer ackLimitCount;
    private List<String> topic;
    private List<String> brokerRoute;


}
