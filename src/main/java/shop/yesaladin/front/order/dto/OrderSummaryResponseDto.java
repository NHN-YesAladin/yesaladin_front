package shop.yesaladin.front.order.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 주문 조회시 응답으로 반환할 dto
 *
 * @author 배수한
 * @since 1.0
 */

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummaryResponseDto {

    private Long orderId;
    private String orderNumber;
    private LocalDateTime orderDateTime;
    private String orderName;
    private Long orderAmount;
    private String orderStatusCode;
    private Long memberId;
    private String memberName;
    private Long orderProductCount;
    private Integer productTotalCount;
    private String orderCode;

}
