package shop.yesaladin.front.member.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.member.dto.SearchMemberManagerResponseDto;
import shop.yesaladin.front.member.service.inter.SearchMemberService;

/**
 * 회원 등록 수정 삭제 요청을 위한 service interface 구현체 입니다.
 *
 * @author : 김선홍
 * @since : 1.0
 */
@RequiredArgsConstructor
@Service
public class SearchMemberServiceImpl implements SearchMemberService {
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    /**
     * 검색할 회원의 로그인 아이디를 gateway로 보내고 받은 정보를 전송하는 메소드
     *
     * @param loginId 검색할 회원의 로그인 아이디
     * @return 검색된 회원의 정보
     * @author : 김선홍
     * @since : 1.0
     */
    @Override
    public List<SearchMemberManagerResponseDto> searchByLoginId(String loginId) {
        String uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getUrl())
                .path("/v1/members/search/loginid/{loginid}")
                .buildAndExpand(loginId)
                .toUriString();
        ResponseEntity<SearchMemberManagerResponseDto> response = restTemplate.getForEntity(
                uri,
                SearchMemberManagerResponseDto.class
        );
        return List.of(Objects.requireNonNull(response.getBody()));
    }

    /**
     * 검색할 회원의 닉네임을 gateway로 보내고 받은 정보를 전송하는 메소드
     *
     * @param nickname 검색할 회원의 로그인 아이디
     * @return 검색된 회원의 정보
     * @author : 김선홍
     * @since : 1.0
     */
    @Override
    public List<SearchMemberManagerResponseDto> searchByNickname(String nickname) {
        String uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getUrl())
                .path("/v1/members/search/nickname/{nickname}")
                .buildAndExpand(nickname)
                .toUriString();
        ResponseEntity<SearchMemberManagerResponseDto> response = restTemplate.getForEntity(
                uri,
                SearchMemberManagerResponseDto.class
        );
        return List.of(Objects.requireNonNull(response.getBody()));
    }

    /**
     * 검색할 회원의 핸드폰 번호를 gateway로 보내고 받은 정보를 전송하는 메소드
     *
     * @param phone 검색할 회원의 로그인 아이디
     * @return 검색된 회원의 정보
     * @author : 김선홍
     * @since : 1.0
     */
    @Override
    public List<SearchMemberManagerResponseDto> searchByPhone(String phone) {
        String uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getUrl())
                .path("/v1/members/search/phone/{phone}")
                .buildAndExpand(phone)
                .toUriString();
        ResponseEntity<SearchMemberManagerResponseDto> response = restTemplate.getForEntity(
                uri,
                SearchMemberManagerResponseDto.class
        );
        return List.of(Objects.requireNonNull(response.getBody()));
    }

    /**
     * 검색할 회원의 이름을 gateway로 보내고 받은 정보를 전송하는 메소드
     *
     * @param name 검색할 회원의 로그인 아이디
     * @return 검색된 회원의 정보
     * @author : 김선홍
     * @since : 1.0
     */
    @Override
    public List<SearchMemberManagerResponseDto> searchByName(String name) {
        String uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getUrl())
                .path("/v1/members/search/name/{name}")
                .buildAndExpand(name)
                .toUriString();
        ResponseEntity<SearchMemberManagerResponseDto> response = restTemplate.getForEntity(
                uri,
                SearchMemberManagerResponseDto.class
        );
        return List.of(Objects.requireNonNull(response.getBody()));
    }

    /**
     * 검색할 회원의 회원가입날을 gateway로 보내고 받은 정보를 전송하는 메소드
     *
     * @param signUpDate 검색할 회원의 로그인 아이디
     * @return 검색된 회원의 정보
     * @author : 김선홍
     * @since : 1.0
     */
    @Override
    public List<SearchMemberManagerResponseDto> searchBySignUpDate(LocalDate signUpDate) {
        String uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getUrl())
                .path("/v1/members/search/signupdate/{signupdate}")
                .buildAndExpand(signUpDate)
                .toUriString();
        ResponseEntity<SearchMemberManagerResponseDto> response = restTemplate.getForEntity(
                uri,
                SearchMemberManagerResponseDto.class
        );
        return List.of(Objects.requireNonNull(response.getBody()));
    }
}
