package shop.yesaladin.front.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Gateway URL 설정 클래스
 *
 * @author : 송학현
 * @since : 1.0
 */
@Getter
@Configuration
public class GatewayConfig {

    @Value("${yesaladin.gateway.base}")
    private String url;

    @Value("${yesaladin.gateway.coupon}")
    private String couponUrl;

    @Value("${yesaladin.gateway.shop}")
    private String shopUrl;

    @Value("${yesaladin.gateway.auth}")
    private String authUrl;
}
