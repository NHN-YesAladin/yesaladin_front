package shop.yesaladin.front.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shop.yesaladin.front.interceptor.ReissueTokenInterceptor;
import shop.yesaladin.front.interceptor.RequestLoggingInterceptor;
import shop.yesaladin.front.member.adapter.MemberAdapter;

/**
 * Web 관련 설정 클래스
 *
 * @author 김홍대
 * @author 송학현
 * @since 1.0
 */
@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final MemberAdapter memberAdapter;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Custom Interceptor를 추가 하기 위한 기능 입니다.
     *
     * @param registry Interceptor를 설정 하기 위한 registry
     * @author 김홍대
     * @author 송학현
     * @since 1.0
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestLoggingInterceptor())
                .excludePathPatterns("/css/**", "/js/**", "/libs/**", "/**/static/**", "/img/**", "/api/**");

        registry.addInterceptor(new ReissueTokenInterceptor(memberAdapter, redisTemplate))
                .excludePathPatterns("/css/**", "/js/**", "/libs/**", "/**/static/**", "/img/**", "/api/**", "/");
    }
}
