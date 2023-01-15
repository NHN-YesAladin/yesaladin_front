package shop.yesaladin.front.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web 관련 설정 클래스
 *
 * @author 배수한
 * @since 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    //TODO addCorsMappings() not working -> 임시로 서버에서 @CrossOrigin 어노테이션 사용해 전송 중
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
