package shop.yesaladin.front.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/**
 * 전체 주문 조회시 리스팅 하기 위해 사용하는 dto
 *
 * @author 배수한
 * @since 1.0
 */
@Getter
@Builder
@AllArgsConstructor
public class TotalOrderResponseDto {

    private Long orderId;
    private String orderNumber;
    private LocalDate orderDate;
    private String orderName;
    private Integer categoryCount;
    private Integer productCount;
    private Long amount;
    private String deliveryStatus;
    private String orderMember;

}
