package shop.yesaladin.front.member.service.inter;

import shop.yesaladin.front.member.dto.SignUpRequest;

/**
 * 회원 등록 수정 삭제 요청을 위한 service interface입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
public interface CommandMemberService {

    /**
     * 회원 등록 데이터를 gateway 서버로 넘겨주기 위한 기능입니다.
     * PasswordEncoder를 통해 비밀번호를 encoding 후 Adapter에게 처리를 위임합니다.
     * Gateway 서버 구현 이후 통신을 주고 받을 adapter를 구현 예정입니다.
     * 현재는 return DTO가 없습니다.
     *
     * @param request controller에서 요청 받은 회원가입 사용자 입력 데이터 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    void signUp(SignUpRequest request);
}
