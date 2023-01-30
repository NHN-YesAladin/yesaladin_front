package shop.yesaladin.front.member.dto;

import lombok.Data;

/**
 * 회원지 배송을 등록하기 위한 요청 Dto 클래스 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Data
public class MemberAddressCreateRequestDto {

    private String address;

    private Boolean isDefault;
}