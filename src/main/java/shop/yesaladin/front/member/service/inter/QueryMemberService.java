package shop.yesaladin.front.member.service.inter;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDate;
import org.springframework.data.domain.Pageable;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.member.dto.MemberBlockRequestDto;
import shop.yesaladin.front.member.dto.MemberBlockResponseDto;
import shop.yesaladin.front.member.dto.MemberManagerResponseDto;
import shop.yesaladin.front.member.dto.MemberPasswordResponseDto;
import shop.yesaladin.front.member.dto.MemberProfileExistResponseDto;
import shop.yesaladin.front.member.dto.MemberQueryResponseDto;
import shop.yesaladin.front.member.dto.MemberStatisticsResponseDto;
import shop.yesaladin.front.member.dto.MemberUnblockResponseDto;
import shop.yesaladin.front.member.dto.MemberWithdrawResponseDto;

/**
 * 회원 정보 조회용 Service Interface 입니다.
 *
 * @author : 송학현
 * @author : 김선홍
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
    ResponseDto<MemberQueryResponseDto> getMemberInfo();

    /**
     * 회원의 등급을 조회합니다.
     *
     * @return 회원 등급
     * @author 최예린
     * @since 1.0
     */
    String getMemberGrade();

    /**
     * 관리자가 회원의 정보를 조회합니다.
     *
     * @return 해당 loginId 의 회원 정보
     * @author 김선홍
     * @since 1.0
     */
    PaginatedResponseDto<MemberManagerResponseDto> manageMemberInfo(Pageable pageable);

    /**
     * 관리자가 회원의 loginId 로 해당 회원의 정보를 조회합니다.
     *
     * @param  loginId 회원의 loginId
     * @return 해당 loginId 의 회원 정보
     * @author 김선홍
     * @since 1.0
     */
    PaginatedResponseDto<MemberManagerResponseDto> manageMemberInfoByLoginId(String loginId, Pageable pageable);

    /**
     * 관리자가 회원의 phone 로 해당 회원의 정보를 조회합니다.
     *
     * @param phone 회원의 phone
     * @param pageable 페이징 정보
     * @return 해당 phone 의 회원 정보
     * @author 김선홍
     * @since 1.0
     */
    PaginatedResponseDto<MemberManagerResponseDto> manageMemberInfoByPhone(String phone, Pageable pageable);

    /**
     * 관리자가 회원의 nickname 으로 해당 회원의 정보를 조회합니다.
     *
     * @param nickname 회원의 nickname
     * @param pageable 페이징 정보
     * @return 해당 nickname 의 회원 정보
     * @author 김선홍
     * @since 1.0
     */
    PaginatedResponseDto<MemberManagerResponseDto> manageMemberInfoByNickname(String nickname, Pageable pageable);

    /**
     * 관리자가 회원의 name 으로 해당 회원의 정보를 조회합니다.
     *
     * @param name 회원의 name
     * @param pageable 페이징 정보
     * @return 해당 name을 가지는 회원들의 정보와 총 갯수
     * @author 김선홍
     * @since 1.0
     */
    PaginatedResponseDto<MemberManagerResponseDto> manageMemberInfoByName(String name, Pageable pageable);

    /**
     * 관리자가 회원의 signUpDate 으로 해당 회원의 정보를 조회합니다.
     *
     * @param signUpDate 회원의 signUpDate
     * @param pageable 페이징 정보
     * @return 해당 name을 가지는 회원들의 정보와 총 갯수
     * @author 김선홍
     * @since 1.0
     */
    PaginatedResponseDto<MemberManagerResponseDto> manageMemberInfoBySignUpDate(LocalDate signUpDate, Pageable pageable);

    /**
     * 관리자가 회원을 차단하는 메서드
     *
     * @param loginId 차단할 회원의 loginId
     * @param requestDto 차단 사유
     * @return 차단 결과
     * @author 김선홍
     * @since 1.0
     */
    MemberBlockResponseDto manageMemberBlockByLoginId(String loginId, MemberBlockRequestDto requestDto)
            throws JsonProcessingException;

    /**
     * 관리자가 회원의 차단을 해지하는 메서드
     *
     * @param loginId 차단 해지할 회원의 메서드
     * @return 차단 해지 결과
     * @author 김선홍
     * @since 1.0
     */
    MemberUnblockResponseDto manageMemberUnBlockByLoginId(String loginId);

    /**
     * 관리자가 회원을 삭제하는 메서드
     *
     * @param loginId 삭제할 회원의 loginId
     * @return 삭제 결과
     * @author 김선홍
     * @since 1.0
     */
    MemberWithdrawResponseDto manageMemberWithdrawByLoginId(String loginId);

    /**
     * 관리자가 회원 현황 통계 정보를 요청 합니다.
     *
     * @return 회원 현황 통계 정보
     * @author 송학현
     * @since 1.0
     */
    MemberStatisticsResponseDto getMemberStatistics();

    /**
     * 회원이 패스워드 변경 시 기존 패스워드 확인을 위해 요청합니다.
     *
     * @return 회원의 패스워드
     * @author 송학현
     * @since 1.0
     */
    MemberPasswordResponseDto getMemberPassword();
}
