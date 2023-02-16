package shop.yesaladin.front.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원지 배송을 등록하기 위한 요청 Dto 클래스 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberAddressRequestDto {

    private String postAddress;
    private String roadAddress;
    private String detailAddress;
    private Boolean isDefault = false;

    /**
     * 요청 reqeust dto를 생성 dto 클래스로 변환합니다.
     *
     * @return 배송지 생성 요청 dto
     * @author 최예린
     * @since 1.0
     */
    public MemberAddressCreateRequestDto toCreateRequestDto() {
        String address = String.join("/", new String[]{postAddress, roadAddress, detailAddress});
        return new MemberAddressCreateRequestDto(address, isDefault);
    }
}