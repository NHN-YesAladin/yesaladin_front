package shop.yesaladin.front.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import shop.yesaladin.front.auth.CustomAuthenticationFilter;
import shop.yesaladin.front.auth.CustomAuthenticationManager;

/**
 * Spring Security의 설정 Bean 등록 클래스입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final RestTemplate restTemplate;

    /**
     * 현재는 사용하지 않아 기본 설정을 disable로 설정하였습니다.
     *
     * @param http http의 filter 등록을 위한 객체 입니다.
     * @return Bean으로 등록한 SecurityFilterChain 입니다.
     * @throws Exception
     * @author : 송학현
     * @since : 1.0
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll();
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/auth-login")
                .usernameParameter("loginId")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                .addFilterAt(
                        customAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );
        http.csrf().disable();
        //TODO cors().disable() not working
        http.cors().disable();
        return http.build();
    }

    /**
     * PasswordEncoder를 빈으로 등록하기 위한 메소드 입니다.
     *
     * @return 회원가입 시 password를 encoding 하기 위해 등록한 Bean 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager를 custom한 manager를 Bean으로 등록하기 위한 기능입니다.
     *
     * @return AuthenticationManager를 custom한 manager를 반환합니다.
     * @author : 송학현
     * @since : 1.0
     */
    @Bean
    public CustomAuthenticationManager customAuthenticationManager() {
        return new CustomAuthenticationManager(restTemplate);
    }

    /**
     * UsernamePasswordAuthenticationFilter를 대체하기 위해 custom한 filter 입니다.
     * form login 요청 시 동작하는 filter 입니다.
     *
     * @return UsernamePasswordAuthenticationFilter를 대체하기 위해 custom한 filter 를 반환합니다.
     */
    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
                "/auth-login");
        customAuthenticationFilter.setAuthenticationManager(customAuthenticationManager());

        return customAuthenticationFilter;
    }
}
