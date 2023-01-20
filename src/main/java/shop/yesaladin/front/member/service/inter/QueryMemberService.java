package shop.yesaladin.front.member.service.inter;

import shop.yesaladin.front.member.dto.MemberProfileExistResponseDto;

/**
 * 회원 정보 조회용 Service Interface 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
public interface QueryMemberService {

    /**
     * Shop 서버에 nickname을 전달하여 기존에 존재하는 nickname 인지 판별하는 기능입니다.
     *
     * @param nickname 입력한 nickname 입니다.
     * @return nickname의 존재 유무 입니다.
     *
     * @author : 송학현
     * @since : 1.0
     */
    MemberProfileExistResponseDto nicknameCheck(String nickname);

    /**
     * Shop 서버에 loginId을 전달하여 기존에 존재하는 loginId 인지 판별하는 기능입니다.
     *
     * @param loginId 입력한 loginId 입니다.
     * @return loginId의 존재 유무 입니다.
     */
    MemberProfileExistResponseDto loginIdCheck(String loginId);

    /**
     * Shop 서버에 email을 전달하여 기존에 존재하는 email 인지 판별하는 기능입니다.
     *
     * @param email 입력한 email 입니다.
     * @return email의 존재 유무 입니다.
     */
    MemberProfileExistResponseDto emailCheck(String email);

    /**
     * Shop 서버에 phone을 전달하여 기존에 존재하는 phone 인지 판별하는 기능입니다.
     *
     * @param phone 입력한 phone 입니다.
     * @return phone의 존재 유무 입니다.
     *
     * @author : 송학현
     * @since : 1.0
     */
    MemberProfileExistResponseDto phoneCheck(String phone);
}
