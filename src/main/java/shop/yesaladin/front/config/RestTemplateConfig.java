package shop.yesaladin.front.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import shop.yesaladin.front.interceptor.JwtInterceptor;

/**
 * RestTemplate 설정 클래스
 *
 * @author : 송학현
 * @since : 1.0
 */
@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final RedisTemplate<String, Object> redisTemplate;


    /**
     * client와 server간 요청, 응답을 위한 RestTemplate Bean 설정.
     *
     * @param builder RestTemplate의 client, server 커넥션 설정을 위한 사용자 정의 Builder.
     * @return RestTemplate 반환.
     * @author : 송학현
     * @since : 1.0
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .interceptors(new JwtInterceptor(redisTemplate))
                .customizers(restTemplate -> restTemplate.setRequestFactory(clientHttpRequestFactory()))
                .build();
    }

    /**
     * client와 server간 connection 객체를 생성 및 타임아웃 등의 설정을 위한 Bean 설정
     *
     * @return ClientHttpRequestFactory의 구현체 SimpleClientHttpRequestFactory
     * @author : 송학현
     * @since : 1.0
     */
    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(30000);
        factory.setReadTimeout(100000);
        factory.setBufferRequestBody(false);

        return factory;
    }


}
