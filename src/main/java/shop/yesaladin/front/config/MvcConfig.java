package shop.yesaladin.front.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * hidden type의 input 태그를 읽어 HttpServletRequestWrapper.getMethod() 반환 값을 변경하여
 * form에서 지원하지 않는 PUT, PATCH, DELETE로 변경해주는 필터 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    @Bean
    public HiddenHttpMethodFilter httpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
