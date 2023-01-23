package shop.yesaladin.front.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 프론트 서버의 정보를 담는 Configuration 클래스입니다.
 *
 * @author 김홍대
 * @since 1.0
 */
@Getter
@Configuration
public class FrontServerMetaConfig {

    @Value("${yesaladin.front.url}")
    private String frontServerUrl;
}
