package shop.yesaladin.front.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 회원읩 배송지 목록을 조회하는데 사용하는 dto 클래스 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberAddressResponseDto {

    private final String ADDRESS_DELIMITER = "/";

    private Long id;
    private String address;
    private Boolean isDefault;

    /**
     * 주소의 우편번호를 반환합니다.
     *
     * @return 우편번호
     * @author 최예린
     * @since 1.0
     */
    public String getPostAddress() {
        return address.split(ADDRESS_DELIMITER)[0];
    }

    /**
     * 주소의 도로명주소를 반환합니다.
     *
     * @return 도로명 주소
     * @author 최예린
     * @since 1.0
     */
    public String getRoadAddress() {
        return address.split(ADDRESS_DELIMITER)[1];
    }

    /**
     * 주소의 상세주소를 반환합니다.
     *
     * @return 상세 주소
     * @author 최예린
     * @since 1.0
     */
    public String getDetailAddress() {
        return address.split(ADDRESS_DELIMITER)[2];
    }
}
