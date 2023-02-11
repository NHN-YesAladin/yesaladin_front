package shop.yesaladin.front.payment.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 결제 정보에 대한 간략한 정보만 담을 dto
 *
 * @author 배수한
 * @since 1.0
 */

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCompleteSimpleResponseDto {
    @NotBlank
    private String paymentId;
    @NotBlank
    private String method;
    @NotBlank
    private String currency;
    @NotNull
    private Long totalAmount;
    @NotBlank
    private LocalDateTime approvedDateTime;

    @NotBlank
    private String ordererName;
    @NotBlank
    private String orderNumber;
    @NotBlank
    private String orderName;

    @NotBlank
    private String recipientName;
    @NotBlank
    private String recipientPhoneNumber;
    @NotBlank
    private String orderAddress;

    private String cardCode;
    private String cardOwnerCode;
    private String cardNumber;
    private Integer cardInstallmentPlanMonths;
    private String cardApproveNumber;
    private String cardAcquirerCode;

    private String easyPayProvider;
    private long easyPayAmount;
    private long easyPayDiscountAmount;

}
