package shop.yesaladin.front.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 주문 생성시 반환하는 dto 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateResponseDto {

    private Long orderId;
    private String orderNumber;
    private String orderName;
    private Long totalAmount;
    private Integer shippingFee;
    private Integer wrappingFee;
    private String requestId;

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
