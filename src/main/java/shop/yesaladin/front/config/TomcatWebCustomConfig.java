package shop.yesaladin.front.config;

import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * 톰캣의 설정을 변경하는 config 파일
 *  사용목적 : 토스에서 에러메세지에 "><"과 같은 특수 문자를 메세지에 담아서 보내기 때문에
 *          tomcat 에서 url 파싱중 tomcat 자체 에러를 던져버리기 때문에 특수 문자를 예외처리하는 설정
 *
 * @author 배수한
 * @since 1.0
 */
@Configuration
public class TomcatWebCustomConfig implements
        WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addConnectorCustomizers((TomcatConnectorCustomizer)
                connector -> connector.setAttribute("relaxedQueryChars", "<>[\\]^`{|}"));
    }
}
