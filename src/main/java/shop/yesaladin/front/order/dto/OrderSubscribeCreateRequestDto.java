package shop.yesaladin.front.order.dto;

import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

/**
 * 정기구독 생성을 요청하는 dto 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Getter
@Setter
public class OrderSubscribeCreateRequestDto extends OrderMemberCreateRequestDto {

    @Range(min = 1, max = 31)
    private final int expectedDay;
    private final int intervalMonth;

    public OrderSubscribeCreateRequestDto(
            LocalDate expectedShippingDate,
            @Size(min = 1, max = 1, message = "구독 상품은 단건 주문만 가능합니다.") List<ProductOrderRequestDto> orderProducts,
            long productTotalAmount,
            int shippingFee,
            int wrappingFee,
            String recipientName,
            String recipientPhoneNumber,
            Long ordererAddressId,
            List<String> orderCoupons,
            long orderPoint,
            Integer expectedDay,
            Integer intervalMonth
    ) {
        super(
                expectedShippingDate,
                orderProducts,
                productTotalAmount,
                shippingFee,
                wrappingFee,
                recipientName,
                recipientPhoneNumber,
                ordererAddressId,
                orderCoupons,
                orderPoint
        );
        this.expectedDay = expectedDay;
        this.intervalMonth = intervalMonth;
    }
}
