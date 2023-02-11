package shop.yesaladin.front.order.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 주문 기본 정보에 관한 dto
 *
 * @author 배수한
 * @since 1.0
 */

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    @NotBlank
    private String ordererName;
    @NotBlank
    private String ordererPhoneNumber;
    @NotNull
    private LocalDateTime orderDateTime;
    @NotBlank
    private String recipientName;
    @NotBlank
    private String recipientPhoneNumber;
    @NotBlank
    private String orderAddress;
    private LocalDate expectedTransportDate;
    @NotNull
    private Boolean isHidden;
    @NotBlank
    private String orderCode;
    @NotBlank
    private String orderNumber;
    @NotBlank
    private String orderName;
    private long usedPoint;
    private int shippingFee;
    private int wrappingFee;
    private long totalAmount;

    //구독 정보
    private int expectedDay;
    private int intervalMonth;
    private LocalDate nextRenewalDate;

}
