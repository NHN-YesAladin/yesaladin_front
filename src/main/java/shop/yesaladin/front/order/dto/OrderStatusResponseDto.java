package shop.yesaladin.front.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 배수한
 * @since 1.0
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusResponseDto {

    private Long orderId;
    private String orderNumber;
    private LocalDateTime orderDateTime;
    private String orderName;
    private Long totalAmount;
    private String loginId;
    private String receiverName;
    private String orderCode;

}
