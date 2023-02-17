package shop.yesaladin.front.order.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import shop.yesaladin.front.coupon.dto.MemberCouponSummaryDto;
import shop.yesaladin.front.member.dto.MemberAddressResponseDto;

/**
 * 회원 주문서에 필요한 데이터를 반환하는 dto 클래스입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@ToString
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
    /**
     * 회원의 대표 배송지의 유무를 반환합니다.
     *
     * @return 대표 배송지 유무
     * @author 최예린
     * @since 1.0
     */
    public boolean hasDefaultAddress() {
        return memberAddress != null && memberAddress.stream()
                .anyMatch(MemberAddressResponseDto::getIsDefault);
    }

    /**
     * 주문 상품의 isbn 과 수량을 반환합니다.
     *
     * @return 상품의 isbn과 수량
     * @author 최예린
     * @since 1.0
     */
    public List<ProductOrderRequestDto> getProductOrderRequest() {
        return orderProducts.stream().map(product -> new ProductOrderRequestDto(
                product.getIsbn(),
                product.getQuantity()
        )).collect(Collectors.toList());
    }

    /**
     * 주문 상품의 총 가격을 반환합니다.
     *
     * @author 최예린
     * @since 1.0
     */
    public long getTotalProductAmount() {
        return orderProducts.stream()
                .mapToLong(product -> product.getActualPrice() * product.getQuantity()).sum();
    }
}
