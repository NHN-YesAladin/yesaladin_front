package shop.yesaladin.front.order.dto;

import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@Setter
public class OrderMemberCreateRequestDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expectedShippingDate;
    @NotEmpty
    @NotNull
    private List<ProductOrderRequestDto> orderProducts;
    @Min(value = 0)
    private long productTotalAmount;
    @Min(value = 0)
    private int shippingFee;
    @Min(value = 0)
    private int wrappingFee;
    @NotBlank
    @Length(min = 1, max = 20)
    private String recipientName;
    @NotBlank
    @Pattern(regexp = "^01([0|1])([0-9]{8})$")
    private String recipientPhoneNumber;
    @NotNull
    private Long ordererAddressId;
    private List<String> orderCoupons;
    @Min(value = 0)
    private long orderPoint;

    public OrderMemberCreateRequestDto(
            LocalDate expectedShippingDate,
            List<ProductOrderRequestDto> orderProducts,
            long productTotalAmount,
            int shippingFee,
            int wrappingFee,
            String recipientName,
            String recipientPhoneNumber,
            Long ordererAddressId,
            List<String> orderCoupons,
            long orderPoint
    ) {
        this.expectedShippingDate = expectedShippingDate;
        this.orderProducts = orderProducts;
        this.productTotalAmount = productTotalAmount;
        this.shippingFee = shippingFee;
        this.wrappingFee = wrappingFee;
        this.recipientName = recipientName;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.ordererAddressId = ordererAddressId;
        this.orderCoupons = orderCoupons;
        this.orderPoint = orderPoint;
    }
}
