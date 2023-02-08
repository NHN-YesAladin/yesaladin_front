package shop.yesaladin.front.order.dto;

import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

/**
 * 주문 생성을 요청하는 dto 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Getter
@ToString
public class OrderMemberCreateRequestDto extends OrderCreateRequestDto {

    @NotNull
    protected Long ordererAddressId;
    protected List<String> orderCoupons;
    @Min(value = 0)
    protected long orderPoint;

    public OrderMemberCreateRequestDto(
            LocalDate expectedShippingDate,
            @NotEmpty @NotNull List<ProductOrderRequestDto> orderProducts,
            @Min(value = 0) long productTotalAmount,
            @Min(value = 0) int shippingFee,
            @Min(value = 0) int wrappingFee,
            @NotBlank String recipientName,
            @NotBlank String recipientPhoneNumber,
            Long ordererAddressId,
            List<String> orderCoupons,
            long orderPoint
    ) {
        super(
                expectedShippingDate,
                orderProducts,
                productTotalAmount,
                shippingFee,
                wrappingFee,
                recipientName,
                recipientPhoneNumber
        );
        this.ordererAddressId = ordererAddressId;
        this.orderCoupons = orderCoupons;
        this.orderPoint = orderPoint;
    }
}
