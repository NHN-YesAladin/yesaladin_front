package shop.yesaladin.front.member.service.inter;

public interface QueryMemberService {

    boolean nicknameCheck(String nickname);

    boolean loginIdCheck(String loginId);

    boolean emailCheck(String email);

    boolean phoneCheck(String phone);
}
