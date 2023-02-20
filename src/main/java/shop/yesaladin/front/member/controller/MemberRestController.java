package shop.yesaladin.front.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.member.dto.MemberBlockRequestDto;
import shop.yesaladin.front.member.dto.MemberBlockResponseDto;
import shop.yesaladin.front.member.dto.MemberEmailUpdateRequestDto;
import shop.yesaladin.front.member.dto.MemberNameUpdateRequestDto;
import shop.yesaladin.front.member.dto.MemberNicknameUpdateRequestDto;
import shop.yesaladin.front.member.dto.MemberPhoneUpdateRequestDto;
import shop.yesaladin.front.member.dto.MemberProfileExistResponseDto;
import shop.yesaladin.front.member.dto.MemberUnblockResponseDto;
import shop.yesaladin.front.member.dto.MemberUpdateResponseDto;
import shop.yesaladin.front.member.dto.MemberWithdrawResponseDto;
import shop.yesaladin.front.member.service.inter.CommandMemberService;
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
    private final CommandMemberService commandMemberService;

    /**
     * 회원 가입 시 사용하고자 하는 닉네임이 존재하는지 체크합니다.
     *
     * @param nickname 사용자가 입력하고자 하는 닉네임 입니다.
     * @return 해당하는 nickname의 존재 여부
     * @author : 송학현
     * @since : 1.0
     */
    @GetMapping("/checkNickname/{nickname}")
    public MemberProfileExistResponseDto nicknameDuplicateCheck(@PathVariable String nickname) {
        log.info("nickname={}", nickname);
        return queryMemberService.nicknameCheck(nickname);
    }

    /**
     * 회원 가입 시 사용하고자 하는 loginId가 존재하는지 체크합니다.
     *
     * @param loginId 사용자가 입력하고자 하는 loginId 입니다.
     * @return 해당하는 loginId의 존재 여부
     * @author : 송학현
     * @since : 1.0
     */
    @GetMapping("/checkLoginId/{loginId}")
    public MemberProfileExistResponseDto loginIdDuplicateCheck(@PathVariable String loginId) {
        log.info("nickname={}", loginId);
        return queryMemberService.loginIdCheck(loginId);
    }

    /**
     * 회원 가입 시 사용하고자 하는 이메일이 존재하는지 체크합니다.
     *
     * @param email 사용자가 입력하고자 하는 email 입니다.
     * @return 해당하는 email의 존재 여부
     * @author : 송학현
     * @since : 1.0
     */
    @GetMapping("/checkEmail/{email}")
    public MemberProfileExistResponseDto emailDuplicateCheck(@PathVariable String email) {
        log.info("email={}", email);
        return queryMemberService.emailCheck(email);
    }

    /**
     * 회원 가입 시 사용하고자 하는 휴대폰 번호가 존재하는지 체크합니다.
     *
     * @param phone 사용자가 입력하고자 하는 휴대폰 번호입니다.
     * @return 해당하는 휴대폰 번호의 존재 여부
     * @author : 송학현
     * @since : 1.0
     */
    @GetMapping("/checkPhone/{phone}")
    public MemberProfileExistResponseDto phoneDuplicateCheck(@PathVariable String phone) {
        log.info("phone={}", phone);
        return queryMemberService.phoneCheck(phone);
    }

    /**
     * 마이페이지에서 회원의 등급을 조회합니다.
     *
     * @return 회원의 등급
     */
    @GetMapping("/grades")
    public String getMemberGrade() {
        return queryMemberService.getMemberGrade();
    }

    /**
     * 관리자가 회원을 차단하는 메서드
     *
     * @param requestDto 차단 사유
     * @param loginId    차단할 회원의 이유
     * @return 차단 결과
     * @author 김선홍
     * @since 1.0
     */
    @PostMapping("/block/{loginid}")
    public MemberBlockResponseDto manageMemberBlockByLoginId(
            @RequestBody MemberBlockRequestDto requestDto,
            @PathVariable(name = "loginid") String loginId
    ) throws JsonProcessingException {
        return queryMemberService.manageMemberBlockByLoginId(loginId, requestDto);
    }

    /**
     * 관리자가 회원의 차단을 해지하는 메서드
     *
     * @param loginId 차단 해지한 로그인 아이디
     * @return 차단 해지 결과
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping("/unblock/{loginid}")
    public MemberUnblockResponseDto manageMemberUnblockByLoginId(@PathVariable(name = "loginid") String loginId) {
        return queryMemberService.manageMemberUnBlockByLoginId(loginId);
    }

    /**
     * 관리자가 삭제할 로그인 아이디
     *
     * @param loginId 삭제할 로그인 아이디
     * @return 삭제 결과
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping("/withdraw/{loginid}")
    public MemberWithdrawResponseDto manageMemberWithdrawByLoginId(@PathVariable(name = "loginid") String loginId) {
        return queryMemberService.manageMemberWithdrawByLoginId(loginId);
    }

    /**
     * 회원의 닉네임을 수정합니다.
     *
     * @param request 수정할 회원 정보
     * @return 수정된 결과
     * @author 최예린
     * @author 송학현
     * @since 1.0
     */
    @PostMapping("/edit/members/nickname")
    public MemberUpdateResponseDto editMemberNickname(@RequestBody MemberNicknameUpdateRequestDto request) {
        ResponseDto<MemberUpdateResponseDto> response = commandMemberService.editNickname(
                request);
        return response.getData();
    }

    /**
     * 회원의 이름을 수정합니다.
     *
     * @param request 수정할 회원 정보
     * @return 수정된 결과
     * @author 송학현
     * @since 1.0
     */
    @PostMapping("/edit/members/name")
    public MemberUpdateResponseDto editMemberName(@RequestBody MemberNameUpdateRequestDto request) {
        ResponseDto<MemberUpdateResponseDto> response = commandMemberService.editName(request);
        return response.getData();
    }

    /**
     * 회원의 이름을 수정합니다.
     *
     * @param request 수정할 회원 정보
     * @return 수정된 결과
     * @author 송학현
     * @since 1.0
     */
    @PostMapping("/edit/members/email")
    public MemberUpdateResponseDto editMemberEmail(@RequestBody MemberEmailUpdateRequestDto request) {
        ResponseDto<MemberUpdateResponseDto> response = commandMemberService.editEmail(request);
        return response.getData();
    }

    /**
     * 회원의 전화번호를 수정합니다.
     *
     * @param request 수정할 회원 정보
     * @return 수정된 결과
     * @author 송학현
     * @since 1.0
     */
    @PostMapping("/edit/members/phone")
    public MemberUpdateResponseDto editMemberPhone(@RequestBody MemberPhoneUpdateRequestDto request) {
        ResponseDto<MemberUpdateResponseDto> response = commandMemberService.editPhone(request);
        return response.getData();
    }

    // TODO: 비밀번호 조회 후 passwordEncoder.matches()로 비교하여 비밀번호 확인하는 메소드 추가
}
