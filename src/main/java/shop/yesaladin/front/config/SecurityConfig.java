package shop.yesaladin.front.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shop.yesaladin.front.auth.CustomAuthenticationManager;
import shop.yesaladin.front.auth.CustomFailureHandler;
import shop.yesaladin.front.auth.CustomLoginProcessingFilter;
import shop.yesaladin.front.auth.CustomLogoutHandler;
import shop.yesaladin.front.auth.RedirectToUrlAuthenticationSuccessHandler;
import shop.yesaladin.front.common.utils.CookieUtils;
import shop.yesaladin.front.member.adapter.MemberAdapter;

/**
 * Spring Security의 설정 Bean 등록 클래스입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberAdapter memberAdapter;
    private final RedisTemplate<String, Object> redisTemplate;
    private final CookieUtils cookieUtils;

    /**
     * Spring Security의 SecurityFilterChain을 설정하고 Bean으로 등록합니다. develop 환경에서 팀원들의 개발상 편의를 위해 권한 별 접근
     * 제어를 하지 않는 설정 입니다. (추 후 이 Bean 설정은 제거할 예정입니다.)
     *
     * @param http http의 filter 등록을 위한 객체 입니다.
     * @return Bean으로 등록한 SecurityFilterChain 입니다.
     * @throws Exception
     * @author : 송학현
     * @since : 1.0
     */
    @Profile({"dev", "test"})
    @Bean
    SecurityFilterChain securityFilterChainDev(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/mypage/**").authenticated()
                .anyRequest().permitAll();
        http.formLogin()
                .loginPage("/members/login")
                .loginProcessingUrl("/auth-login");
        http.logout()
                .logoutUrl("/logout")
                .addLogoutHandler(customLogoutHandler())
                .logoutSuccessUrl("/");

        http.addFilterAt(
                customLoginProcessingFilter(),
                UsernamePasswordAuthenticationFilter.class
        );

        http.headers().defaultsDisabled().frameOptions().sameOrigin();
        http.csrf().disable();
        http.cors().disable();
        return http.build();
    }

    /**
     * Spring Security의 SecurityFilterChain을 설정하고 Bean으로 등록합니다.
     *
     * @param http http의 filter 등록을 위한 객체 입니다.
     * @return Bean으로 등록한 SecurityFilterChain 입니다.
     * @throws Exception
     * @author : 송학현
     * @since : 1.0
     */
    @Profile({"default", "prod"})
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/monitor/**").permitAll()
                .antMatchers("/mypage/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/manager/**").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().permitAll();
        http.formLogin()
                .loginPage("/members/login")
                .loginProcessingUrl("/auth-login");
        http.logout()
                .logoutUrl("/logout")
                .addLogoutHandler(customLogoutHandler())
                .logoutSuccessUrl("/");

        http.addFilterAt(
                customLoginProcessingFilter(),
                UsernamePasswordAuthenticationFilter.class
        );

        http.headers().defaultsDisabled().frameOptions().sameOrigin();
        http.csrf().disable();
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
        return new CustomAuthenticationManager(memberAdapter, redisTemplate, cookieUtils);
    }

    /**
     * UsernamePasswordAuthenticationFilter를 대체하기 위해 custom한 filter 입니다.
     * form login 요청 시 동작하는 filter 입니다.
     *
     * @return UsernamePasswordAuthenticationFilter를 대체하기 위해 custom한 filter 를 반환합니다.
     * @author : 송학현
     * @since : 1.0
     */
    @Bean
    public CustomLoginProcessingFilter customLoginProcessingFilter() {
        CustomLoginProcessingFilter customLoginProcessingFilter = new CustomLoginProcessingFilter(
                "/auth-login");
        customLoginProcessingFilter.setAuthenticationManager(customAuthenticationManager());
        customLoginProcessingFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        customLoginProcessingFilter.setAuthenticationSuccessHandler(new RedirectToUrlAuthenticationSuccessHandler());

        return customLoginProcessingFilter;
    }

    /**
     * 인증 실패 시 작동하는 FailureHandler를 Bean으로 등록합니다.
     *
     * @return 인증 실패 시 작동하는 AuthenticationFailureHandler 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomFailureHandler();
    }

    /**
     * Logout 시 작동하는 custom handler를 Bean으로 등록합니다.
     *
     * @return Custom 한 Logout Handler 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    @Bean
    public CustomLogoutHandler customLogoutHandler() {
        return new CustomLogoutHandler(redisTemplate, memberAdapter, cookieUtils);
    }
}
