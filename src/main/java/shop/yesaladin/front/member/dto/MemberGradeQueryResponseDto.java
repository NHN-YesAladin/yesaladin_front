package shop.yesaladin.front.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 회원의 등급을 조회 후 반환하는 dto 클래스 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberGradeQueryResponseDto {

    String gradeEn;
    String gradeKo;
}
