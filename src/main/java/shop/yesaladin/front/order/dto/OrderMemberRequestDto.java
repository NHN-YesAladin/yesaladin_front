package shop.yesaladin.front.order.dto;

import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 주문 생성을 요청하는 dto 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Getter
@ToString
@Setter
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
    private int shippingFee;
    @Min(value = 0)
    private int wrappingFee;
    private List<String> orderCoupons;
    @Min(value = 0)
    private long orderPoint;

    public OrderMemberCreateRequestDto toOrderMemberCreateRequest() {
        return OrderMemberCreateRequestDto.builder()
                .recipientName(recipientName)
                .recipientPhoneNumber(recipientPhoneNumber)
                .ordererAddressId(ordererAddressId)
                .expectedShippingDate(expectedShippingDate)
                .orderProducts(orderProducts)
                .productTotalAmount(productTotalAmount)
                .shippingFee(shippingFee)
                .wrappingFee(wrappingFee)
                .orderCoupons(orderCoupons)
                .orderPoint(orderPoint)
                .build();
    }
//
//    public PaymentViewRequestDto toPaymentViewRequest(String orderNumber, String orderName) {
//        return PaymentViewRequestDto.builder()
//                .ordererName(ordererName)
//                .ordererPhoneNumber(ordererPhoneNumber)
//                .re
//                .build()
//
//    }
}
