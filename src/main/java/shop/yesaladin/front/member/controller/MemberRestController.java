package shop.yesaladin.front.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import shop.yesaladin.front.member.service.inter.QueryMemberService;

/**
 * 회원 관련 Rest Controller 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberRestController {

    private final QueryMemberService queryMemberService;

    /**
     * 회원 가입 시 사용하고자 하는 닉네임이 존재하는지 체크합니다.
     *
     * @param nickname 사용자가 입력하고자 하는 닉네임 입니다.
     * @return 해당하는 nickname의 존재 여부
     *
     * @author : 송학현
     * @since : 1.0
     */
    @GetMapping("/checkNickname/{nickname}")
    public boolean nicknameDuplicateCheck(@PathVariable String nickname) {
        log.info("nickname={}", nickname);
        return queryMemberService.nicknameCheck(nickname);
    }

    /**
     * 회원 가입 시 사용하고자 하는 loginId가 존재하는지 체크합니다.
     *
     * @param loginId 사용자가 입력하고자 하는 loginId 입니다.
     * @return 해당하는 loginId의 존재 여부
     *
     * @author : 송학현
     * @since : 1.0
     */
    @GetMapping("/checkLoginId/{loginId}")
    public boolean loginIdDuplicateCheck(@PathVariable String loginId) {
        log.info("nickname={}", loginId);
        return queryMemberService.loginIdCheck(loginId);
    }

    /**
     * 회원 가입 시 사용하고자 하는 이메일이 존재하는지 체크합니다.
     *
     * @param email 사용자가 입력하고자 하는 email 입니다.
     * @return 해당하는 email의 존재 여부
     *
     * @author : 송학현
     * @since : 1.0
     */
    @GetMapping("/checkEmail/{email}")
    public boolean emailDuplicateCheck(@PathVariable String email) {
        log.info("email={}", email);
        return queryMemberService.emailCheck(email);
    }

    /**
     * 회원 가입 시 사용하고자 하는 휴대폰 번호가 존재하는지 체크합니다.
     *
     * @param phone 사용자가 입력하고자 하는 휴대폰 번호입니다.
     * @return 해당하는 휴대폰 번호의 존재 여부
     *
     * @author : 송학현
     * @since : 1.0
     */
    @GetMapping("/checkPhone/{phone}")
    public boolean phoneDuplicateCheck(@PathVariable String phone) {
        log.info("phone={}", phone);
        return queryMemberService.phoneCheck(phone);
    }
}
