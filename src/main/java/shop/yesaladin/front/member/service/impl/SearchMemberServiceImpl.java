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

    @Override
    public List<SearchMemberManagerResponseDto> searchByLoginId(String loginId) {
        String uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getUrl())
                .path("/v2/members/search/loginid/{loginid}")
                .buildAndExpand(loginId)
                .toUriString();
        ResponseEntity<SearchMemberManagerResponseDto> response = restTemplate.getForEntity(
                uri,
                SearchMemberManagerResponseDto.class
        );
        return List.of(Objects.requireNonNull(response.getBody()));
    }

    @Override
    public List<SearchMemberManagerResponseDto> searchByNickname(String nickname) {
        String uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getUrl())
                .path("/v2/members/search/nickname/{nickname}")
                .buildAndExpand(nickname)
                .toUriString();
        ResponseEntity<SearchMemberManagerResponseDto> response = restTemplate.getForEntity(
                uri,
                SearchMemberManagerResponseDto.class
        );
        return List.of(Objects.requireNonNull(response.getBody()));
    }

    @Override
    public List<SearchMemberManagerResponseDto> searchByPhone(String phone) {
        String uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getUrl())
                .path("/v2/members/search/phone/{phone}")
                .buildAndExpand(phone)
                .toUriString();
        ResponseEntity<SearchMemberManagerResponseDto> response = restTemplate.getForEntity(
                uri,
                SearchMemberManagerResponseDto.class
        );
        return List.of(Objects.requireNonNull(response.getBody()));
    }

    @Override
    public List<SearchMemberManagerResponseDto> searchByName(String name) {
        String uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getUrl())
                .path("/v2/members/search/name/{name}")
                .buildAndExpand(name)
                .toUriString();
        ResponseEntity<SearchMemberManagerResponseDto> response = restTemplate.getForEntity(
                uri,
                SearchMemberManagerResponseDto.class
        );
        return List.of(Objects.requireNonNull(response.getBody()));
    }

    @Override
    public List<SearchMemberManagerResponseDto> searchBySignUpDate(LocalDate signUpDate) {
        String uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getUrl())
                .path("/v2/members/search/signupdate/{signupdate}")
                .buildAndExpand(signUpDate)
                .toUriString();
        ResponseEntity<SearchMemberManagerResponseDto> response = restTemplate.getForEntity(
                uri,
                SearchMemberManagerResponseDto.class
        );
        return List.of(Objects.requireNonNull(response.getBody()));
    }
}
