package shop.yesaladin.front.member.service.inter;

import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.member.dto.MemberEmailUpdateRequestDto;
import shop.yesaladin.front.member.dto.MemberNameUpdateRequestDto;
import shop.yesaladin.front.member.dto.MemberNicknameUpdateRequestDto;
import shop.yesaladin.front.member.dto.MemberPasswordUpdateRequestDto;
import shop.yesaladin.front.member.dto.MemberPhoneUpdateRequestDto;
import shop.yesaladin.front.member.dto.MemberUpdateResponseDto;
import shop.yesaladin.front.member.dto.SignUpRequestDto;
import shop.yesaladin.front.member.dto.SignUpResponseDto;
import shop.yesaladin.front.oauth.dto.Oauth2SignUpRequestDto;

/**
 * 회원 등록 수정 삭제 요청을 위한 service interface입니다.
 *
 * @author 송학현
 * @author 최예린
 * @since 1.0
 */
public interface CommandMemberService {

    /**
     * 회원 등록 데이터를 gateway 서버로 넘겨주기 위한 기능입니다.
     *
     * @param request controller에서 요청 받은 회원가입 사용자 입력 데이터 입니다.
     * @return 회원 등록 API 호출 결과 입니다.
     * @author 송학현
     * @since 1.0
     */
    SignUpResponseDto signUp(SignUpRequestDto request);

    /**
     * OAuth2 로그인 시 회원 등록 데이터를 gateway 서버로 넘겨주기 위한 기능입니다.
     *
     * @param request controller에서 요청 받은 회원가입 사용자 입력 데이터 입니다.
     * @return 회원 등록 API 호출 결과 입니다.
     * @author 송학현
     * @since 1.0
     */
    SignUpResponseDto signUp(Oauth2SignUpRequestDto request);

    /**
     * 회원 탈퇴를 위한 기능입니다.
     *
     * @param loginId 탈퇴 대상 계정의 loginId 입니다.
     * @author 송학현
     * @since 1.0
     */
    void withdraw(String loginId);

    /**
     * 회원의 닉네임을 수정합니다.
     *
     * @param request 회원 닉네임 수정 정보
     * @return 수정된 회원 정보
     * @author 최예린
     * @author 송학현
     * @since 1.0
     */
    ResponseDto<MemberUpdateResponseDto> editNickname(MemberNicknameUpdateRequestDto request);


    /**
     * 회원의 이름을 수정합니다.
     *
     * @param request 회원 이름 수정 정보
     * @return 수정된 회원 정보
     * @author 송학현
     * @since 1.0
     */
    ResponseDto<MemberUpdateResponseDto> editName(MemberNameUpdateRequestDto request);

    /**
     * 회원의 이메일을 수정합니다.
     *
     * @param request 회원 이메일 수정 정보
     * @return 수정된 회원 정보
     * @author 송학현
     * @since 1.0
     */
    ResponseDto<MemberUpdateResponseDto> editEmail(MemberEmailUpdateRequestDto request);

    /**
     * 회원의 전화번호를 수정합니다.
     *
     * @param request 회원 전화번호 수정 정보
     * @return 수정된 회원 정보
     * @author 송학현
     * @since 1.0
     */
    ResponseDto<MemberUpdateResponseDto> editPhone(MemberPhoneUpdateRequestDto request);

    /**
     * 회원의 패스워드를 수정합니다.
     *
     * @param request 회원 패스워드 수정 정보
     * @return 수정된 회원 정보
     * @author 송학현
     * @since 1.0
     */
    ResponseDto<MemberUpdateResponseDto> editPassword(MemberPasswordUpdateRequestDto request);
}
