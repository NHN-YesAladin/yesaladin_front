package shop.yesaladin.front.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 회원읩 배송지 목록을 조회하는데 사용하는 dto 클래스 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberAddressQueryDto {

    private Long id;
    private String address;
    private boolean isDefault;
    private String loginId;
}
