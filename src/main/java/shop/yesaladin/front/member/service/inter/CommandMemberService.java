package shop.yesaladin.front.member.service.inter;

import shop.yesaladin.front.member.dto.MemberUpdateRequestDto;
import shop.yesaladin.front.member.dto.SignUpRequestDto;
import shop.yesaladin.front.member.dto.SignUpResponseDto;

/**
 * 회원 등록 수정 삭제 요청을 위한 service interface입니다.
 *
 * @author : 송학현
 * @author 최예린
 * @since : 1.0
 */
public interface CommandMemberService {

    /**
     * 회원 등록 데이터를 gateway 서버로 넘겨주기 위한 기능입니다.
     *
     * @param request controller에서 요청 받은 회원가입 사용자 입력 데이터 입니다.
     * @return 회원 등록 API 호출 결과 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    SignUpResponseDto signUp(SignUpRequestDto request);

    /**
     * 회원 탈퇴를 위한 기능입니다.
     *
     * @param loginId 탈퇴 대상 계정의 loginId 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    void withdraw(String loginId);

    /**
     * 회원 정보를 수정합니다.
     *
     * @param request 회원 수정 정보
     * @author 최예린
     * @since 1.0
     */
    void edit(MemberUpdateRequestDto request);
}
