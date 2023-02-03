package shop.yesaladin.front.member.service.inter;

import java.time.LocalDate;
import shop.yesaladin.front.member.dto.MemberManagerListResponseDto;
import shop.yesaladin.front.member.dto.MemberManagerResponseDto;
import shop.yesaladin.front.member.dto.MemberProfileExistResponseDto;
import shop.yesaladin.front.member.dto.MemberQueryResponseDto;

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

    /**
     * 회원 정보를 조회합니다.
     *
     * @return 회원 정보
     * @author 최예린
     * @since 1.0
     */
    MemberQueryResponseDto getMemberInfo();

    /**
     * 회원의 등급을 조회합니다.
     *
     * @return 회원 등급
     * @author 최예린
     * @since 1.0
     */
    String getMemberGrade();

    /**
     * 관리자가 회원의 loginId 로 해당 회원의 정보를 조회합니다.
     *
     * @param  loginId 회원의 loginId
     * @return 해당 loginId 의 회원 정보
     * @author 김선홍
     * @since 1.0
     */
    MemberManagerResponseDto manageMemberInfoByLoginId(String loginId);

    /**
     * 관리자가 회원의 phone 로 해당 회원의 정보를 조회합니다.
     *
     * @param phone 회원의 phone
     * @return 해당 phone 의 회원 정보
     * @author 김선홍
     * @since 1.0
     */
    MemberManagerResponseDto manageMemberInfoByPhone(String phone);

    /**
     * 관리자가 회원의 nickname 으로 해당 회원의 정보를 조회합니다.
     *
     * @param nickname 회원의 nickname
     * @return 해당 nickname 의 회원 정보
     * @author 김선홍
     * @since 1.0
     */
    MemberManagerResponseDto manageMemberInfoByNickname(String nickname);

    /**
     * 관리자가 회원의 name 으로 해당 회원의 정보를 조회합니다.
     *
     * @param name 회원의 name
     * @return 해당 name을 가지는 회원들의 정보와 총 갯수
     * @author 김선홍
     * @since 1.0
     */
    MemberManagerListResponseDto manageMemberInfoByName(String name, int page, int size);

    /**
     * 관리자가 회원의 signUpDate 으로 해당 회원의 정보를 조회합니다.
     *
     * @param signUpDate 회원의 signUpDate
     * @return 해당 name을 가지는 회원들의 정보와 총 갯수
     * @author 김선홍
     * @since 1.0
     */
    MemberManagerListResponseDto manageMemberInfoBySignUpDate(LocalDate signUpDate, int page, int size);
}
