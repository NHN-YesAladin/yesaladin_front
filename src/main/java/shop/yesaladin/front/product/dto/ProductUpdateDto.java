package shop.yesaladin.front.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.List;

/**
 * 상품 수정을 위한 Dto 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDto {
    // 상품 설명
    @NotBlank
    @Length(min = 1, max = 255)
    private String title;
    @NotBlank
    @Length(min = 1, max = 21844)
    private String contents;
    @NotBlank
    @Length(min = 1, max = 21844)
    private String description;

    // 저자, 출판사
    private List<Long> authors;
    @Positive
    private long publisherId;

    // 정가, 할인율, 포인트 적립율
    @PositiveOrZero
    @Max(2000000)
    private long actualPrice;
    @PositiveOrZero
    @Max(100)
    private int discountRate;
    private Boolean isSeparatelyDiscount;
    @PositiveOrZero
    @Max(100)
    private int givenPointRate;
    private Boolean isGivenPoint;

    // 구독 상품 관련
    @Length(max = 8)
    private String issn;
    private Boolean isSubscriptionAvailable;

    // 수량, 출간일, 노출우선순위
    @PositiveOrZero
    @Max(2000000)
    private long quantity;
    @NotBlank
    private String publishedDate;
    @Min(-100)
    @Max(100)
    private int preferentialShowRanking;

    // 썸네일 파일
    private String thumbnailFileUrl;
    private String thumbnailFileUploadDateTime;

    // e-book 파일
    private String ebookFileUrl;
    private String ebookFileUploadDateTime;

    // codes
    @NotBlank
    private String productTypeCode;
    @NotBlank
    private String productSavingMethodCode;

    // 태그
    private List<Long> tags;

    // 카테고리
    private List<Long> categories;

}
