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
    List<SearchMemberManagerResponseDto> searchByLoginId(String loginId);
    List<SearchMemberManagerResponseDto> searchByNickname(String nickname);
    List<SearchMemberManagerResponseDto> searchByPhone(String phone);
    List<SearchMemberManagerResponseDto> searchByName(String name);
    List<SearchMemberManagerResponseDto> searchBySignUpDate(LocalDate signUpDate);

}
