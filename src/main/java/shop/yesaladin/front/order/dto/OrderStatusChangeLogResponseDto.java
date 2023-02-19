package shop.yesaladin.front.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 주문 상태 변경 내역 생성 후 반환하는 dto 클래스 입니다.
 *
 * @author 배수한
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusChangeLogResponseDto {

    private String orderNumber;
    private String name;
    private LocalDateTime changeDateTime;
    private OrderStatusCode orderStatusCode;

}
