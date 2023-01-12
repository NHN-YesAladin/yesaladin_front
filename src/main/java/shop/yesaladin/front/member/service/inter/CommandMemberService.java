package shop.yesaladin.front.member.service.inter;

import shop.yesaladin.front.member.dto.SignUpRequest;
import shop.yesaladin.front.member.dto.SignUpResponse;

/**
 * 회원 등록 수정 삭제 요청을 위한 service interface입니다.
 *
 * @author : 송학현
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
    SignUpResponse signUp(SignUpRequest request);
}
