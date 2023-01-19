package shop.yesaladin.front.coupon.dto;

import java.time.LocalDate;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class CouponCreateRequestDto {

    @NotBlank
    private String name;
    private boolean isUnlimited;
    @Positive
    private Integer quantity;
    private MultipartFile couponImage;
    @Positive
    private Integer duration;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private LocalDate expirationDate;
    @NotBlank
    private String couponTypeCode;
    @PositiveOrZero
    private Integer minOrderAmount;
    @Positive
    private Integer maxDiscountAmount;
    @Positive
    private Integer discountAmount;
    private String couponBoundCode;
    private boolean canBeOverlapped;
}
