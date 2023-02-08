package shop.yesaladin.front.order.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.yesaladin.front.coupon.dto.MemberCouponSummaryDto;
import shop.yesaladin.front.member.dto.MemberAddressResponseDto;
import shop.yesaladin.front.product.dto.ProductOrderResponseDto;

/**
 * 회원 주문서에 필요한 데이터를 반환하는 dto 클래스입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSheetResponseDto {

    private List<ProductOrderResponseDto> orderProducts;
    private String name;
    private String phoneNumber;
    private long point;
    private List<MemberAddressResponseDto> memberAddress;
    private List<MemberCouponSummaryDto> memberCoupons;

    /**
     * 회원의 대표 배송지를 반환합니다.
     *
     * @return 대표 배송지
     * @author 최예린
     * @since 1.0
     */
    public MemberAddressResponseDto getDefaultAddress() {
        return memberAddress.stream()
                .filter(MemberAddressResponseDto::getIsDefault)
                .findFirst()
                .orElse(null);
    }
}
