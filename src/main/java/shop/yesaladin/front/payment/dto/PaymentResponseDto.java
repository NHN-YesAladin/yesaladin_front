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
 * 결제정보의 대부분을 지니고있는 dto
 *
 * @author 배수한
 * @since 1.0
 */

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {

    //결제 기본 정보
    @NotBlank
    private String paymentId;
    @NotBlank
    private String method;
    @NotBlank
    private String currency;
    @NotNull
    private long paymentTotalAmount;
    @NotNull
    private LocalDateTime approvedDateTime;

    //카드 정보 - 카드 결제의 경우 : nullable
    private String cardCode;
    private String cardOwnerCode;
    private String cardNumber;
    private int cardInstallmentPlanMonths;
    private String cardApproveNumber;
    private String cardAcquirerCode;

    //간편 결제 정보 - 간편 결제일 경우 : nullable
    private String easyPayProvider;
    private long easyPayAmount;
    private long easyPayDiscountAmount;


}
