package shop.yesaladin.front.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 주문 생성을 요청하는 dto 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Getter
public class OrderNonMemberCreateRequestDto {

    @JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
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
    @NotBlank
    @Length(min = 1, max = 20)
    private String ordererName;
    @NotNull
    @Pattern(regexp = "^01([0|1])([0-9]{8})$")
    private String ordererPhoneNumber;

    @NotBlank
    @Length(min = 2, max = 255)
    private String ordererAddress;

    public OrderNonMemberCreateRequestDto(
            String expectedShippingDate,
            List<ProductOrderRequestDto> orderProducts,
            long productTotalAmount,
            int shippingFee,
            int wrappingFee,
            String recipientName,
            String recipientPhoneNumber,
            String ordererName,
            String ordererPhoneNumber,
            String ordererPostAddress,
            String ordererRoadAddress,
            String ordererDetailAddress
    ) {
        this.expectedShippingDate = LocalDate.parse(expectedShippingDate);
        this.orderProducts = orderProducts;
        this.productTotalAmount = productTotalAmount;
        this.shippingFee = shippingFee;
        this.wrappingFee = wrappingFee;
        this.recipientName = recipientName;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.ordererName = ordererName;
        this.ordererPhoneNumber = ordererPhoneNumber;
        this.ordererAddress = String.join(
                "/",
                new String[]{ordererPostAddress, ordererRoadAddress, ordererDetailAddress}
        );
    }
}
