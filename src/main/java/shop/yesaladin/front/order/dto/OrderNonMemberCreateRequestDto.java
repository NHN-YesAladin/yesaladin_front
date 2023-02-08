package shop.yesaladin.front.order.dto;

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
public class OrderNonMemberCreateRequestDto extends OrderCreateRequestDto {

    @NotBlank
    @Length(min = 1, max = 20)
    private final String ordererName;
    @NotNull
    @Pattern(regexp = "^01([0|1])([0-9]{8})$")
    private final String ordererPhoneNumber;

    @NotBlank
    @Length(min = 2, max = 255)
    private final String ordererAddress;

    public OrderNonMemberCreateRequestDto(
            LocalDate expectedShippingDate,
            @NotEmpty @NotNull List<ProductOrderRequestDto> orderProducts,
            @Min(value = 0) long productTotalAmount,
            @Min(value = 0) int shippingFee,
            @Min(value = 0) int wrappingFee,
            @NotBlank String recipientName,
            @NotBlank String recipientPhoneNumber,
            String ordererName,
            String ordererPhoneNumber,
            String ordererPostAddress,
            String ordererRoadAddress,
            String ordererDetailAddress
    ) {
        super(
                expectedShippingDate,
                orderProducts,
                productTotalAmount,
                shippingFee,
                wrappingFee,
                recipientName,
                recipientPhoneNumber
        );
        this.ordererName = ordererName;
        this.ordererPhoneNumber = ordererPhoneNumber;
        this.ordererAddress = String.join(
                "/",
                new String[]{ordererPostAddress, ordererRoadAddress, ordererDetailAddress}
        );
    }
}
