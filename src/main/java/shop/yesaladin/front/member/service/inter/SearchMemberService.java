package shop.yesaladin.front.member.service.inter;

import java.time.LocalDate;
import java.util.List;
import shop.yesaladin.front.member.dto.SearchMemberManagerResponseDto;

/**
 * 검색할 회원의 정보를 gateway로 보내고 검색된 정보를 받는 service interface
 *
 * @author : 김선홍
 * @since : 1.0
 */
public interface SearchMemberService {

    /**
     * 검색할 회원의 로그인 아이디를 gateway로 보내고 받은 정보를 전송하는 메소드
     *
     * @param loginId 검색할 회원의 로그인 아이디
     * @return 검색된 회원의 정보
     * @author : 김선홍
     * @since : 1.0
     */
    List<SearchMemberManagerResponseDto> searchByLoginId(String loginId);

    /**
     * 검색할 회원의 닉네임을 gateway로 보내고 받은 정보를 전송하는 메소드
     *
     * @param nickname 검색할 회원의 로그인 아이디
     * @return 검색된 회원의 정보
     * @author : 김선홍
     * @since : 1.0
     */
    List<SearchMemberManagerResponseDto> searchByNickname(String nickname);

    /**
     * 검색할 회원의 핸드폰 번호를 gateway로 보내고 받은 정보를 전송하는 메소드
     *
     * @param phone 검색할 회원의 로그인 아이디
     * @return 검색된 회원의 정보
     * @author : 김선홍
     * @since : 1.0
     */
    List<SearchMemberManagerResponseDto> searchByPhone(String phone);

    /**
     * 검색할 회원의 이름을 gateway로 보내고 받은 정보를 전송하는 메소드
     *
     * @param name 검색할 회원의 로그인 아이디
     * @return 검색된 회원의 정보
     * @author : 김선홍
     * @since : 1.0
     */
    List<SearchMemberManagerResponseDto> searchByName(String name);

    /**
     * 검색할 회원의 회원가입날을 gateway로 보내고 받은 정보를 전송하는 메소드
     *
     * @param signUpDate 검색할 회원의 로그인 아이디
     * @return 검색된 회원의 정보
     * @author : 김선홍
     * @since : 1.0
     */
    List<SearchMemberManagerResponseDto> searchBySignUpDate(LocalDate signUpDate);

}
