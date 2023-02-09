package shop.yesaladin.front.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 회원지 배송을 등록하기 위한 요청 Dto 클래스 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberAddressRequestDto {

    private String postAddress;
    private String roadAddress;
    private String detailAddress;
    private Boolean isDefault;

    /**
     * 우편번호, 도로명주소, 상세 주소를 합쳐 반환합니다.
     *
     * @return 주소
     * @author 최예린
     * @since 1.0
     */
    public String getAddress() {
        return postAddress + " " + roadAddress + " " + detailAddress;
    }

    /**
     * 요청 reqeust dto를 생성 dto 클래스로 변환합니다.
     *
     * @return 배송지 생성 요청 dto
     * @author 최예린
     * @since 1.0
     */
    public MemberAddressCreateRequestDto toCreateRequestDto() {
        return new MemberAddressCreateRequestDto(getAddress(), isDefault);
    }
}