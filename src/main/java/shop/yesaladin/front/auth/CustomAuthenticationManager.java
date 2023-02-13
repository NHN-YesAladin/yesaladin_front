package shop.yesaladin.front.auth;

import static java.util.stream.Collectors.toList;
import static shop.yesaladin.front.member.jwt.AuthUtil.JWT_CODE;
import static shop.yesaladin.front.member.jwt.AuthUtil.UUID_CODE;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.exception.InvalidHttpHeaderException;
import shop.yesaladin.front.common.utils.CookieUtils;
import shop.yesaladin.front.member.adapter.MemberAdapter;
import shop.yesaladin.front.member.dto.LoginRequestDto;
import shop.yesaladin.front.member.dto.MemberResponseDto;
import shop.yesaladin.front.member.jwt.AuthInfo;

/**
 * AuthenticationManager를 custom한 Manager 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private static final String UUID_HEADER = "UUID_HEADER";
    private static final String X_EXPIRE_HEADER = "X-Expire";

    private final MemberAdapter memberAdapter;
    private final RedisTemplate<String, Object> redisTemplate;
    private final CookieUtils cookieUtils;

    /**
     * Auth 서버에서 발급받은 JWT 토큰을 기반으로 Shop 서버에 유저 정보를 요청 한 뒤, UsernamePasswordAuthenticationToken을 만들어
     * 반환합니다.
     *
     * @param authentication 인증 객체입니다.
     * @return 인증 객체를 반환합니다.
     * @throws AuthenticationException 인증 실패 시 발생 하는 예외 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        LoginRequestDto loginRequestDto = new LoginRequestDto(
                (String) authentication.getPrincipal(),
                (String) authentication.getCredentials()
        );
        ResponseEntity<Void> exchange = memberAdapter.getAuthInfo(loginRequestDto);

        checkValidLoginRequest(exchange);

        String uuid = Objects.requireNonNull(exchange.getHeaders().get(UUID_HEADER).get(0));
        String expiredTime = Objects.requireNonNull(exchange.getHeaders()
                .get(X_EXPIRE_HEADER)
                .get(0));

        String accessToken = extractAuthorizationHeader(exchange);

        if (accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }

        ResponseEntity<ResponseDto<MemberResponseDto>> response = memberAdapter.getMemberInfo(
                loginRequestDto,
                accessToken
        );

        MemberResponseDto memberResponseDto = response.getBody().getData();

        log.info("accessToken={}", accessToken);

        HttpServletRequest servletRequest = Objects.requireNonNull(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()))
                .getRequest();
        HttpServletResponse servletResponse = Objects.requireNonNull(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()))
                .getResponse();

        Cookie authCookie = cookieUtils.createCookie(UUID_CODE.getValue(), uuid, 60 * 30);

        moveIntoMemberCart(loginRequestDto.getLoginId(), servletRequest);
        Cookie cartCookie = cookieUtils.createCookie(
                "CART_NO",
                loginRequestDto.getLoginId(),
                60 * 60 * 24 * 30
        );

        servletResponse.addCookie(authCookie);
        servletResponse.addCookie(cartCookie);

        List<SimpleGrantedAuthority> authorities = getAuthorities(memberResponseDto);
        log.info("authorities={}", authorities);

        AuthInfo authInfo = new AuthInfo(
                memberResponseDto,
                accessToken,
                memberResponseDto.getRoles(),
                expiredTime
        );
        log.info("authInfo={}", authInfo);
        redisTemplate.opsForHash().put(uuid, JWT_CODE.getValue(), authInfo);

        return new UsernamePasswordAuthenticationToken(
                authentication.getPrincipal().toString(),
                null,
                authorities
        );
    }

    /**
     * 로그인 전 사용자가 cart에 담은 상품을 회원의 cart에 추가하기 위한 기능입니다. 설계 흐름 상 이 클래스에서 cartNo cookie의 value가 회원을
     * 식별할 수 있는 값으로 update 되지 않는다면, 장바구니 화면으로 이동 시 회원의 장바구니 목록을 볼 수 없어 임시적으로 인증 flow에서 작동하도록 합니다. 추
     * 후 인증/인가와 관계 없이 migration 예정 입니다.
     *
     * @param loginId        회원의 loginId
     * @param servletRequest HttpServletRequest
     */
    private void moveIntoMemberCart(String loginId, HttpServletRequest servletRequest) {
        String cartNo = cookieUtils.getValueFromCookie(servletRequest.getCookies(), "CART_NO");
        if (Objects.nonNull(cartNo)) {
            Map<Object, Object> cart = redisTemplate.opsForHash().entries(cartNo);
            Map<Object, Object> login = redisTemplate.opsForHash().entries(loginId);

            cart.keySet().forEach(key -> {
                int currentQuantity = Integer.parseInt(cart.get(key).toString());
                login.put(
                        key,
                        (Integer.parseInt(login.getOrDefault(key, 0).toString()) + currentQuantity)
                );
            });

            redisTemplate.opsForHash().putAll(loginId, login);
        }
    }

    /**
     * login 요청 시 올바른 결과 인지 판별 하기 위해 Response Header를 검증 하는 기능 입니다. 예외 발생 시 CustomFailureHandler가
     * 동작합니다.
     *
     * @param exchange Auth 서버에 login 요청 시 반환 되는 결과 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    private void checkValidLoginRequest(ResponseEntity<Void> exchange) {
        log.info("Auth server exchange check={}", exchange);
        if (!exchange.getHeaders().containsKey(UUID_HEADER) || !exchange.getHeaders()
                .containsKey(HttpHeaders.AUTHORIZATION)) {
            throw new BadCredentialsException("자격 증명 실패");
        }
    }

    /**
     * Shop 서버에서 제공 받은 회원 정보를 바탕으로 권한 정보를 추출하는 기능입니다.
     *
     * @param memberResponseDto Shop 서버에서 제공받은 회원 정보 결과 입니다.
     * @return token을 만들기 위해 권한 정보를 담은 List<SimpleGrantedAuthority>를 반환합니다.
     * @author : 송학현
     * @since : 1.0
     */
    private List<SimpleGrantedAuthority> getAuthorities(MemberResponseDto memberResponseDto) {
        MemberResponseDto member = Objects.requireNonNull(memberResponseDto);
        log.info("member={}", member);

        return member.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }

    /**
     * 로그인 시 Auth 서버에서 제공 받은 응답 헤더를 추출하는 기능입니다.
     *
     * @param exchange Auth 서버에서 제공받은 응답 헤더입니다.
     * @return Http Response Header의 Authorization에 들어있는 accessToken을 추출하여 반환합니다.
     * @author : 송학현
     * @since : 1.0
     */
    private String extractAuthorizationHeader(ResponseEntity<Void> exchange) {
        String accessToken = Objects.requireNonNull(exchange.getHeaders()
                .get(HttpHeaders.AUTHORIZATION)
                .get(0));
        if (Objects.isNull(accessToken)) {
            throw new InvalidHttpHeaderException("Authorization Header is empty");
        }
        return accessToken;
    }
}
