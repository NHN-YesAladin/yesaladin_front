package shop.yesaladin.front.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import shop.yesaladin.front.payment.dto.PaymentViewRequestDto;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

/**
 * 주문 생성을 요청하는 dto 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Getter
@ToString
@Setter
@NoArgsConstructor
public class OrderMemberRequestDto {

    @NotBlank
    @Length(min = 1, max = 20)
    private String ordererName;
    @NotBlank
    @Pattern(regexp = "^01([0|1])([0-9]{8})$")
    private String ordererPhoneNumber;

    @NotBlank
    @Length(min = 1, max = 20)
    private String recipientName;
    @NotBlank
    @Pattern(regexp = "^01([0|1])([0-9]{8})$")
    private String recipientPhoneNumber;
    @NotNull
    private Long ordererAddressId;
    private String ordererPostAddress;
    private String ordererRoadAddress;
    private String ordererDetailAddress;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expectedShippingDate;
    @NotEmpty
    @NotNull
    private List<ProductOrderRequestDto> orderProducts;
    private long productActualTotalAmount;
    @Min(value = 0)
    private long productTotalAmount;
    @Min(value = 0)
    private long shippingFee;
    @Min(value = 0)
    private long wrappingFee;
    private List<String> orderCoupons;
    @Min(value = 0)
    private long usePoint;
    private List<Long> savePoint;

    public OrderMemberCreateRequestDto toOrderMemberCreateRequest() {
        return OrderMemberCreateRequestDto.builder()
                .recipientName(recipientName)
                .recipientPhoneNumber(recipientPhoneNumber)
                .ordererAddressId(ordererAddressId)
                .expectedShippingDate(expectedShippingDate)
                .orderProducts(orderProducts)
                .productTotalAmount(productTotalAmount)
                .shippingFee((int) shippingFee)
                .wrappingFee((int) wrappingFee)
                .orderCoupons(orderCoupons)
                .usePoint(usePoint)
                .savePoint(savePoint.stream().mapToLong(point -> point).sum())
                .build();
    }

    public PaymentViewRequestDto toPaymentViewRequest(String orderNumber, String orderName) {
        return PaymentViewRequestDto.builder()
                .ordererName(ordererName)
                .ordererPhoneNumber(ordererPhoneNumber)
                .recipientName(recipientName)
                .recipientPhoneNumber(recipientPhoneNumber)
                .recipientAddress(getAddress())
                .recipientExpectedDate(
                        expectedShippingDate == null ? "" : expectedShippingDate.toString())
                .orderName(orderName)
                .orderNumber(orderNumber)
                .productAmount(productActualTotalAmount)
                .discountAmount(productActualTotalAmount - productTotalAmount)
                .shippingFee(shippingFee)
                .wrappingFee(wrappingFee)
                .totalAmount(productTotalAmount + shippingFee + wrappingFee)
                .build();

    }

    private String getAddress() {
        return String.join(
                ",",
                List.of(ordererPostAddress, ordererRoadAddress, ordererDetailAddress)
        );
    }
}
